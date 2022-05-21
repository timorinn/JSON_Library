package jsonlib;

import jsonlib.exceptions.JsonFormatException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JsonParserImpl {

	private static class JsonParserPreferences {
		private final LinkedHashMap<String, Object> map;
		private JsonParserState state;
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
			this.state = JsonParserState.STATE_START_BLOCK;
			this.currentIndex = startIndex;
			this.map = new LinkedHashMap<>();
			this.errorMessage = null;
		}
	}

	/**
	 * Singleton
	 */
	private JsonParserImpl() { }


	public static JsonObjectImp parse(String body) throws JsonFormatException {
		JsonParserPreferences pr = new JsonParserPreferences(body);

		for (; pr.currentIndex < pr.endIndex && !JsonParserState.STATE_ERROR.equals(pr.state); pr.currentIndex++) {
			switch (pr.state) {
				case STATE_START_BLOCK -> checkStartBlock(pr);
				case STATE_CHECK_KEY -> processKey(pr);
				case STATE_COLON -> checkColon(pr);
				case STATE_VALUE -> processValue(pr);
				case STATE_NEXT -> checkNext(pr);
				case STATE_SUCCESS -> checkSuccess(pr);
			}
		}

		if (!JsonParserState.STATE_SUCCESS.equals(pr.state)) {
			// TODO: 23.01.2022 обновить сообщение об ошибке
			throw new JsonFormatException("Pr.errorMessage: " + pr.errorMessage + "  " + pr.state);
		}
		return new JsonObjectImp(pr.map);
	}


	private static char getCurrentChar(JsonParserPreferences pr) {
		return pr.body.charAt(pr.currentIndex);
	}


	private static void skipSpaces(JsonParserPreferences pr) {
		while (pr.currentIndex < pr.endIndex - 1 && Character.isWhitespace(getCurrentChar(pr))) {
			pr.currentIndex++;
		}
	}


	private static int getNextBlockBracketIndex(JsonParserPreferences pr) {
		char openBracket = getCurrentChar(pr);
		char closeBracket;
		boolean isText = false;
		int cntBrackets = 0;

		switch (openBracket) {
			case '{' -> closeBracket = '}';
			case '[' -> closeBracket = ']';
			case '(' -> closeBracket = ')';
			default -> {return -1;}
		}

		for (int i = pr.currentIndex; i < pr.endIndex; i++) {
			if (pr.body.charAt(i) =='\"') {
				if (!isText) {
					isText = true;
				} else if (pr.body.charAt(i - 1) != '\\') {
					isText = false;
				}
			}
			cntBrackets += (pr.body.charAt(i) == openBracket && !isText) ? 1 : 0;
			cntBrackets -= (pr.body.charAt(i) == closeBracket && !isText) ? 1 : 0;

			if (cntBrackets == 0) {
				return i;
			}
		}
		return -1;
	}


	// TODO: 21.05.2022 int -> void
	private static void findEndOfStringIndex(JsonParserPreferences pr) {
		while (pr.currentIndex < pr.endIndex - 1) {
			if (getCurrentChar(pr) == '\"' && pr.currentIndex > 0 && pr.body.charAt(pr.currentIndex - 1) != '\\') {
				break;
			}
			pr.currentIndex++;
		}
	}


	private static int skipKeyName(JsonParserPreferences pr) {
		int cnt = 0;

		while (pr.currentIndex < pr.endIndex - 1 && getCurrentChar(pr) != '\"'
				&& (Character.isAlphabetic(getCurrentChar(pr)) || Character.isDigit(getCurrentChar(pr)))) {
			pr.currentIndex++;
			cnt++;
		}
		return cnt;
	}


	private static void checkStartBlock(JsonParserPreferences pr) {
		skipSpaces(pr);
		pr.state = getCurrentChar(pr) == '{' ? JsonParserState.STATE_CHECK_KEY : JsonParserState.STATE_ERROR;
	}


	private static void processKey(JsonParserPreferences pr) {
		int keyLength;
		int keyStart;

		skipSpaces(pr);
		if (getCurrentChar(pr) == '}') {
			pr.state = JsonParserState.STATE_SUCCESS;
		} else if (getCurrentChar(pr) == '\"') {
			pr.currentIndex++;
			keyStart = pr.currentIndex;
			keyLength = skipKeyName(pr);
			if (getCurrentChar(pr) == '\"' && keyLength > 0) {
				pr.currentKey = pr.body.substring(keyStart, pr.currentIndex);
				pr.state = JsonParserState.STATE_COLON;
			} else {
				pr.state = JsonParserState.STATE_ERROR;
			}
		} else {
			pr.state = JsonParserState.STATE_ERROR;
		}
	}


	private static void checkColon(JsonParserPreferences pr) {
		skipSpaces(pr);
		pr.state = getCurrentChar(pr) == ':' ? JsonParserState.STATE_VALUE : JsonParserState.STATE_ERROR;
		// TODO: 21.01.2022
	}


	private static void processValue(JsonParserPreferences pr) {
		Object value;

		skipSpaces(pr);
		value = getValue(pr);
		
		if (value != null) {
			pr.currentValue = value;
			pr.state = JsonParserState.STATE_NEXT;
		} else {
			pr.state = JsonParserState.STATE_ERROR;
		}
	}


	private static Object getValue(JsonParserPreferences pr) {
		Object value = null;

		switch (getCurrentChar(pr)) {
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
		findEndOfStringIndex(pr);
		return getCurrentChar(pr) == '\"' ? pr.body.substring(startValue, pr.currentIndex + 1): null;
	}


	private static String getValueNumber(JsonParserPreferences pr) {
		boolean afterPoint = false;
		boolean afterExp = false;
		int startNumber = pr.currentIndex;

		if (getCurrentChar(pr) == '-') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
			} else {
				return null;
			}
		}

		if (getCurrentChar(pr) == '0') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
			} else {
				return null;
			}
		} else if (Character.isDigit(getCurrentChar(pr))) {
			while (pr.currentIndex < pr.endIndex - 1 && Character.isDigit(getCurrentChar(pr))) {
				pr.currentIndex++;
			}
		} else {
			return null;
		}

		if (getCurrentChar(pr) == '.') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
				while (pr.currentIndex < pr.endIndex - 1 && Character.isDigit(getCurrentChar(pr))) {
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

		if (getCurrentChar(pr) == '-' || getCurrentChar(pr) == '+') {
			if (pr.currentIndex < pr.endIndex - 1) {
				pr.currentIndex++;
				if (pr.currentIndex < pr.endIndex - 1 && Character.toLowerCase(getCurrentChar(pr)) == 'e') {
					pr.currentIndex++;
					if (getCurrentChar(pr) == '0') {
						return null;
					}
					while (pr.currentIndex < pr.endIndex - 1 && Character.isDigit(getCurrentChar(pr))) {
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

		switch (getCurrentChar(pr)) {
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


	private static JsonObjectImp getValueBlock(JsonParserPreferences pr) {
		int closeBracketIndex = getNextBlockBracketIndex(pr);
		JsonObjectImp jsonObjectImp;

		if (closeBracketIndex != -1) {
			try {
				 jsonObjectImp = JsonParserImpl.parse(pr.body.substring(pr.currentIndex, closeBracketIndex + 1));
			} catch (JsonFormatException je) {
				return null;
			}
			pr.currentIndex = closeBracketIndex;
			return jsonObjectImp;
		} else {
			return null;
		}
	}


	private static ArrayList<Object> getValueArray(JsonParserPreferences pr) {
		ArrayList<Object> arrayList = new ArrayList<>();
		JsonArrayType typeArray = JsonArrayType.ARRAY_NULLS;
		JsonArrayType typeValue;
		int startArrayIndex = pr.currentIndex;
		int endArrayIndex = getNextBlockBracketIndex(pr);
		Object value;

		if (endArrayIndex == -1) {
			pr.errorMessage = "Can't find matching closing array bracket.";
			return null;
		} else {
			pr.currentIndex++;
			while (pr.currentIndex < endArrayIndex) {
				skipSpaces(pr);

				typeValue = defineArrayElementType(pr);
				if (typeArray.equals(JsonArrayType.ARRAY_NULLS) && !typeValue.equals(JsonArrayType.ARRAY_NULLS)) {
					typeArray = typeValue;
				} else if (!typeArray.equals(JsonArrayType.ARRAY_NULLS) && !typeArray.equals(typeValue)
						&& !typeValue.equals(JsonArrayType.ARRAY_NULLS)) {
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
				skipSpaces(pr);
				if (getCurrentChar(pr) == ',') {
					pr.currentIndex++;
				}
			}
			return arrayList;
		}
	}


	private static JsonArrayType defineArrayElementType(JsonParserPreferences pr) {
		JsonArrayType arrType = JsonArrayType.ARRAY_NULLS;

		switch (getCurrentChar(pr)) {
			case '\"' -> arrType = JsonArrayType.ARRAY_STRINGS;
			case 't', 'f' -> arrType = JsonArrayType.ARRAY_BOOLEANS;
			case 'n' -> arrType = JsonArrayType.ARRAY_NULLS;
			case '{' -> arrType = JsonArrayType.ARRAY_BLOCKS;
			case '[' -> arrType = JsonArrayType.ARRAY_ARRAYS;
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> arrType = JsonArrayType.ARRAY_NUMBERS;
		}
		return arrType;
	}


	private static void checkNext(JsonParserPreferences pr) {
		skipSpaces(pr);

		if (getCurrentChar(pr) == ',' || getCurrentChar(pr) == '}') {
			if (!pr.map.containsKey(pr.currentKey)) {
				pr.state = (getCurrentChar(pr) == ',' ? JsonParserState.STATE_CHECK_KEY : JsonParserState.STATE_SUCCESS);
				pr.map.put(pr.currentKey, pr.currentValue);
			} else {
				pr.errorMessage = "Key '" + pr.currentKey + "' already exists in map.";
				pr.state = JsonParserState.STATE_ERROR;
			}
		} else {
			// TODO: 21.01.2022
			pr.errorMessage = "Incorrect symbol at the end of request body.";
			pr.state = JsonParserState.STATE_ERROR;
		}
	}


	private static void checkSuccess(JsonParserPreferences pr) {
		while (pr.currentIndex < pr.endIndex && Character.isWhitespace(getCurrentChar(pr))) {
			pr.currentIndex++;
		}
		if (pr.currentIndex == pr.endIndex) {
			pr.state = JsonParserState.STATE_SUCCESS;
		} else {
			pr.errorMessage = "Incorrect json end. Unexpected symbol '" + getCurrentChar(pr)
					+ "' on position " + pr.currentIndex + ".";
			pr.state = JsonParserState.STATE_ERROR;
		}
	}
}
