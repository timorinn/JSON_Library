package LibraryJSON;

import java.util.HashMap;
import java.util.HashSet;

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

	// todo

	static class JsonParserPreferences {
		private HashMap<String, Object> map;
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
			// TODO: 21.01.2022
			this.body = body;
			this.endIndex = endIndex;
			this.state = STATE_START_BLOCK;
			this.currentIndex = startIndex;
			this.map = new HashMap<>();
			this.errorMessage = null;
		}

		public char getCurrentChar() {
			return body.charAt(currentIndex);
		}

		private void skipSpaces() {
			//todo
			while (currentIndex < endIndex - 1 && Character.isWhitespace(getCurrentChar())) {
				currentIndex++;
			}
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


	//todo можно сделать лучше, выглядит некрасиво
	private static void getKey(JsonParserPreferences pr) {
		int keyLength;
		int keyStart = pr.currentIndex;

		pr.skipSpaces();

		if (pr.getCurrentChar() == '}') {
			pr.state = STATE_SUCCESS;
		} else if (pr.getCurrentChar() == '\"') {
			pr.currentIndex++;
			keyLength = skipKeyName(pr);
			if (pr.getCurrentChar() == '\"' && keyLength > 0) {
				pr.currentKey = pr.body.substring(keyStart, pr.currentIndex + 1);
				// TODO: 22.01.2022  
//				if (!keys.contains(keyName)) {
//					keys.add(keyName);
					pr.state = STATE_COLON;
//				} else {
//					state = STATE_ERROR;
//				}
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


	private static void getValue(JsonParserPreferences pr) {
		Object value = null;

		pr.skipSpaces();

		switch (pr.getCurrentChar()) {
			case '\"' -> value = getValueString(pr);
			case '{' -> value = getValueBlock(pr);
			case '[' -> getValueArray(pr);
			case 't', 'f', 'n' -> value = checkValueSpecial(pr);
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> value = getValueNumber(pr);
		}

		if (value != null) {
			pr.currentValue = value;
			pr.state = STATE_NEXT;
		} else {
			pr.state = STATE_ERROR;
		}
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
		JsonObject jsonObject;
		int closeBracketIndex = JsonCommon.getNextBlockBracket(pr.body, pr.currentIndex);

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


	// TODO: 15.01.2022 дописать :-----------(
	private static void getValueArray(JsonParserPreferences pr) {
		int startArrayIndex = pr.currentIndex;
		int endArrayIndex;

		endArrayIndex = JsonCommon.getNextBlockBracket(pr.body, startArrayIndex);
		if (endArrayIndex == -1) {
			// TODO: 21.01.2022
			pr.state = STATE_ERROR;
		}
		// TODO: 17.01.2022
	}


//	private void defineArrayElementType() {
//		String arrType = ARRAY_NULLS;
//
//		switch (getCurrentChar()) {
//			case '\"' -> arrType = ARRAY_STRINGS;
//			case 't', 'f' -> arrType = ARRAY_BOOLEANS;
//			case 'n' -> arrType = ARRAY_NULLS;
//			case '{' -> arrType = ARRAY_BLOCKS;
//			case '[' -> arrType = ARRAY_ARRAYS;
//			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> arrType = ARRAY_NUMBERS;
//			default -> state = STATE_ERROR;
//		}
//	}


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
				case STATE_CHECK_KEY -> getKey(pr);
				case STATE_COLON -> checkColon(pr);
				case STATE_VALUE -> getValue(pr);
				case STATE_NEXT -> checkNext(pr);
				case STATE_SUCCESS -> checkSuccess(pr);
			}
		}
		if (!STATE_SUCCESS.equals(pr.state)) {
			// TODO: 21.01.2022
			throw new JsonFormatException("Pr.errorMessage: " + pr.errorMessage);
		}
		return new JsonObject();
	}
}
