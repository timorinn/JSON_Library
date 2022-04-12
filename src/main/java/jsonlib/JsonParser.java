package jsonlib;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JsonParser {
	final private static String STATE_START_BLOCK = "STATE_START_BLOCK";
	final private static String STATE_CHECK_KEY = "STATE_CHECK_KEY";
	final private static String STATE_COLON = "STATE_COLON";
	final private static String STATE_VALUE = "STATE_VALUE";
	final private static String STATE_SUCCESS = "STATE_SUCCESS";
	final private static String STATE_ERROR = "STATE_ERROR";
	final private static String STATE_NEXT = "STATE_NEXT";

	final private static String ARRAY_STRINGS = "ARRAY_STRING";
	final private static String ARRAY_NUMBERS = "ARRAY_NUMBERS";
	final private static String ARRAY_BOOLEANS = "ARRAY_BOOLEANS";
	final private static String ARRAY_BLOCKS = "ARRAY_BLOCKS";
	final private static String ARRAY_ARRAYS = "ARRAY_ARRAYS";
	final private static String ARRAY_NULLS = "ARRAY_NULLS";

	
	static class JsonParserPreferences {
		private final LinkedHashMap<String, Object> map;
		private String state;
		private final String body;
		private String currentKey;
		private Object currentValue;
		private int currentIndex;
		private final int endIndex;
		private String errorMessage;

		public JsonParserPreferences(String body) {
			this(body, 0, body.length());
		}

		public JsonParserPreferences(String body, int startIndex, int endIndex) {
			this.body = body;
			this.endIndex = endIndex;
			this.state = STATE_START_BLOCK;
			this.currentIndex = startIndex;
			this.map = new LinkedHashMap<>();
			this.errorMessage = null;
		}


		public char getCurrentChar() {
			return body.charAt(currentIndex);
		}

		
		private void skipSpaces() {
			while (currentIndex < endIndex - 1 && Character.isWhitespace(getCurrentChar())) {
				currentIndex++;
			}
		}

		
		public int getNextBlockBracket() {
			char openBracket = getCurrentChar();
			char closeBracket;
			boolean isText = false;
			int cntBrackets = 0;

			switch (openBracket) {
				case '{' -> closeBracket = '}';
				case '[' -> closeBracket = ']';
				case '(' -> closeBracket = ')';
				default -> {return -1;}
			}

			for (int i = currentIndex; i < this.endIndex; i++) {
				if (this.body.charAt(i) =='\"') {
					if (!isText) {
						isText = true;
					} else if (this.body.charAt(i - 1) != '\\') {
						isText = false;
					}
				}
				cntBrackets += (this.body.charAt(i) == openBracket && !isText) ? 1 : 0;
				cntBrackets -= (this.body.charAt(i) == closeBracket && !isText) ? 1 : 0;

				if (cntBrackets == 0) {
					return i;
				}
			}
			return -1;
		}
	}

	private static void skipValueString(JsonParserPreferences pr) {
		while (pr.currentIndex < pr.endIndex - 1) {
			if (pr.getCurrentChar() == '\"' && pr.currentIndex > 0 && pr.body.charAt(pr.currentIndex - 1) != '\\') {
				break;
			}
			pr.currentIndex++;
		}
	}


	private static int skipKeyName(JsonParserPreferences pr) {
		int cnt = 0;

		while (pr.currentIndex < pr.endIndex - 1 && pr.getCurrentChar() != '\"'
				&& (Character.isAlphabetic(pr.getCurrentChar()) || Character.isDigit(pr.getCurrentChar()))) {
			pr.currentIndex++;
			cnt++;
		}
		return cnt;
	}


	private static void checkStartBlock(JsonParserPreferences pr) {
		pr.skipSpaces();

		pr.state = pr.getCurrentChar() == '{' ? STATE_CHECK_KEY : STATE_ERROR;
	}


	private static void processKey(JsonParserPreferences pr) {
		int keyLength;
		int keyStart;

		pr.skipSpaces();

		if (pr.getCurrentChar() == '}') {
			pr.state = STATE_SUCCESS;
		} else if (pr.getCurrentChar() == '\"') {
			pr.currentIndex++;
			keyStart = pr.currentIndex;
			keyLength = skipKeyName(pr);
			if (pr.getCurrentChar() == '\"' && keyLength > 0) {
				pr.currentKey = pr.body.substring(keyStart, pr.currentIndex);
				pr.state = STATE_COLON;
			} else {
				pr.state = STATE_ERROR;
			}
		} else {
			pr.state = STATE_ERROR;
		}
	}


	private static void checkColon(JsonParserPreferences pr) {
		pr.skipSpaces();
		pr.state = pr.getCurrentChar() == ':' ? STATE_VALUE : STATE_ERROR;
		// TODO: 21.01.2022
	}


	private static void processValue(JsonParserPreferences pr) {
		Object value;

		pr.skipSpaces();
		value = getValue(pr);
		
		if (value != null) {
			pr.currentValue = value;
			pr.state = STATE_NEXT;
		} else {
			pr.state = STATE_ERROR;
		}
	}


	private static Object getValue(JsonParserPreferences pr) {
		Object value = null;

		switch (pr.getCurrentChar()) {
			case '\"' -> value = getValueString(pr);
			case '{' -> value = getValueBlock(pr);
			case '[' -> value = getValueArray(pr);
			case 't', 'f', 'n' -> value = checkValueSpecial(pr);
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> value = getValueNumber(pr);
		}
		return value;
	}


	private static Object getValueString(JsonParserPreferences pr) {
		int startValue = pr.currentIndex;

		pr.currentIndex++;
		skipValueString(pr);
		return pr.getCurrentChar() == '\"' ? pr.body.substring(startValue, pr.currentIndex + 1): null;
	}


	private static String getValueNumber(JsonParserPreferences pr) {
		boolean afterPoint = false;
		boolean afterExp = false;
		int startNumber = pr.currentIndex;

		if (pr.getCurrentChar() == '-') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
			} else {
				return null;
			}
		}

		if (pr.getCurrentChar() == '0') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
			} else {
				return null;
			}
		} else if (Character.isDigit(pr.getCurrentChar())) {
			while (pr.currentIndex < pr.endIndex - 1 && Character.isDigit(pr.getCurrentChar())) {
				pr.currentIndex++;
			}
		} else {
			return null;
		}

		if (pr.getCurrentChar() == '.') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
				while (pr.currentIndex < pr.endIndex - 1 && Character.isDigit(pr.getCurrentChar())) {
					pr.currentIndex++;
					afterPoint = true;
				}
				if (!afterPoint) {
					return null;
				}
			} else {
				return null;
			}
		}

		if (pr.getCurrentChar() == '-' || pr.getCurrentChar() == '+') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
				if (pr.currentIndex < pr.endIndex - 1 && Character.toLowerCase(pr.getCurrentChar()) == 'e') {
					pr.currentIndex++;
					if (pr.getCurrentChar() == '0') {
						return null;
					}
					while (pr.currentIndex < pr.endIndex - 1 && Character.isDigit(pr.getCurrentChar())) {
						afterExp = true;
						pr.currentIndex++;
					}
					if (!afterExp) {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		pr.currentIndex--;
		return pr.body.substring(startNumber, pr.currentIndex + 1);
	}


	private static int strInStr(String mainStr, int startIndex, String expectedStr) {
		int mainStrLength = mainStr.length();
		int expectedStrLength = expectedStr.length();
		int i;

		for (i = 0; i + startIndex < mainStrLength && i < expectedStrLength; i++) {
			if (mainStr.charAt(startIndex + i) != expectedStr.charAt(i)) {
				return -1;
			}
		}
		return i == expectedStrLength ? 0 : -1;
	}


	private static String checkValueSpecial(JsonParserPreferences pr) {
		String valueSpecial;

		switch (pr.getCurrentChar()) {
			case 't' -> valueSpecial = "true";
			case 'f' -> valueSpecial = "false";
			case 'n' -> valueSpecial = "null";
			default -> {
				return null;
			}
		}

		if (strInStr(pr.body, pr.currentIndex, valueSpecial) == 0) {
			pr.currentIndex += valueSpecial.length() - 1;
			return valueSpecial;
		} else {
			return null;
		}
	}


	private static JsonObject getValueBlock(JsonParserPreferences pr) {
		int closeBracketIndex = pr.getNextBlockBracket();
		JsonObject jsonObject;

		if (closeBracketIndex != -1) {
			try {
				 jsonObject = JsonParser.getJsonObject(pr.body.substring(pr.currentIndex, closeBracketIndex + 1));
			} catch (JsonFormatException je) {
				return null;
			}
			pr.currentIndex = closeBracketIndex;
			return jsonObject;
		} else {
			return null;
		}
	}


	private static ArrayList<Object> getValueArray(JsonParserPreferences pr) {
		ArrayList<Object> arrayList = new ArrayList<>();
		String typeArray = ARRAY_NULLS;
		String typeValue;
		int startArrayIndex = pr.currentIndex;
		int endArrayIndex = pr.getNextBlockBracket();
		Object value;

		if (endArrayIndex == -1) {
			pr.errorMessage = "Can't find matching closing array bracket.";
			return null;
		} else {
			pr.currentIndex++;
			while (pr.currentIndex < endArrayIndex) {
				pr.skipSpaces();

				typeValue = defineArrayElementType(pr);
				if (typeArray.equals(ARRAY_NULLS) && !typeValue.equals(ARRAY_NULLS)) {
					typeArray = typeValue;
				} else if (!typeArray.equals(ARRAY_NULLS) && !typeArray.equals(typeValue)
						&& !typeValue.equals(ARRAY_NULLS)) {
					pr.errorMessage = "Array elements has different types: "
							+ pr.body.substring(startArrayIndex, endArrayIndex + 1);
					return null;
				}

				value = getValue(pr);
				if (value == null) {
					return arrayList;
				}

				arrayList.add(value);
				pr.currentIndex++;
				pr.skipSpaces();
				if (pr.getCurrentChar() == ',') {
					pr.currentIndex++;
				}
			}
			return arrayList;
		}
	}


	private static String defineArrayElementType(JsonParserPreferences pr) {
		String arrType = ARRAY_NULLS;

		switch (pr.getCurrentChar()) {
			case '\"' -> arrType = ARRAY_STRINGS;
			case 't', 'f' -> arrType = ARRAY_BOOLEANS;
			case 'n' -> arrType = ARRAY_NULLS;
			case '{' -> arrType = ARRAY_BLOCKS;
			case '[' -> arrType = ARRAY_ARRAYS;
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> arrType = ARRAY_NUMBERS;
		}
		return arrType;
	}


	private static void checkNext(JsonParserPreferences pr) {
		pr.skipSpaces();

		if (pr.getCurrentChar() == ',' || pr.getCurrentChar() == '}') {
			if (!pr.map.containsKey(pr.currentKey)) {
				pr.state = (pr.getCurrentChar() == ',' ? STATE_CHECK_KEY : STATE_SUCCESS);
				pr.map.put(pr.currentKey, pr.currentValue);
			} else {
				pr.errorMessage = "Key '" + pr.currentKey + "' already exists in map.";
				pr.state = STATE_ERROR;
			}
		} else {
			// TODO: 21.01.2022
			pr.errorMessage = "Incorrect symbol at the end of request body.";
			pr.state = STATE_ERROR;
		}
	}


	private static void checkSuccess(JsonParserPreferences pr) {
		while (pr.currentIndex < pr.endIndex && Character.isWhitespace(pr.getCurrentChar())) {
			pr.currentIndex++;
		}
		if (pr.currentIndex == pr.endIndex) {
			pr.state = STATE_SUCCESS;
		} else {
			pr.errorMessage = "Incorrect json end. Unexpected symbol '" + pr.getCurrentChar()
					+ "' on position " + pr.currentIndex + ".";
			pr.state = STATE_ERROR;
		}
	}


	public static JsonObject getJsonObject(String body) throws JsonFormatException {
		JsonParserPreferences pr = new JsonParserPreferences(body);

		for (; pr.currentIndex < pr.endIndex && !STATE_ERROR.equals(pr.state); pr.currentIndex++) {
			switch (pr.state) {
				case STATE_START_BLOCK -> checkStartBlock(pr);
				case STATE_CHECK_KEY -> processKey(pr);
				case STATE_COLON -> checkColon(pr);
				case STATE_VALUE -> processValue(pr);
				case STATE_NEXT -> checkNext(pr);
				case STATE_SUCCESS -> checkSuccess(pr);
			}
		}

		if (!STATE_SUCCESS.equals(pr.state)) {
			// TODO: 23.01.2022
			throw new JsonFormatException("Pr.errorMessage: " + pr.errorMessage + "  " + pr.state);
		}
		return new JsonObject(pr.map);
	}
}
