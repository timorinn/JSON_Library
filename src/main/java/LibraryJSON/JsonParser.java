package LibraryJSON;

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


	private HashSet<String> keys;
	private String state;
	private String body;
	private String currentKey;
	private int currentIndex;
	private int startIndex;
	private int endIndex;

	// todo
//	private String processMessage;

	public JsonParser(String body) throws JsonFormatException {
		this(body, 0, body.length());
	}


	public JsonParser(String body, int startIndex, int endIndex) {
		this.body = body;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.state = STATE_START_BLOCK;
		this.currentIndex = startIndex;
		this.keys = new HashSet<>();
	}


	private char getCurrentChar() {
		return body.charAt(currentIndex);
	}


	private void skipSpaces() {
		//todo
		while (currentIndex < endIndex - 1 && Character.isWhitespace(getCurrentChar())) {
			currentIndex++;
		}
	}


	private void skipValueString() {
		while (currentIndex < endIndex - 1) {
			if (getCurrentChar() == '\"' && currentIndex > 0 && body.charAt(currentIndex - 1) != '\\') {
				break;
			}
			currentIndex++;
		}
	}


	private int skipKeyName() {
		int cnt = 0;

		while (currentIndex < endIndex - 1 && getCurrentChar() != '\"'
				&& (Character.isAlphabetic(getCurrentChar()) || Character.isDigit(getCurrentChar()))) {
			currentIndex++;
			cnt++;
		}
		return cnt;
	}


	private void checkStartBlock() {
		skipSpaces();

		state = getCurrentChar() == '{' ? STATE_CHECK_KEY : STATE_ERROR;
	}


	//todo можно сделать лучше, выглядит некрасиво
	private void checkKey() {
//	private Object checkKey() {
		String keyName;
		int keyLength;
		int keyStart = currentIndex;

		skipSpaces();

		if (getCurrentChar() == '}') {
			state = STATE_SUCCESS;
		} else if (getCurrentChar() == '\"') {
			currentIndex++;
			keyLength = skipKeyName();
			if (getCurrentChar() == '\"' && keyLength > 0) {
				keyName = body.substring(keyStart, currentIndex + 1);
//				if (!keys.contains(keyName)) {
//					keys.add(keyName);
					state = STATE_COLON;
//				} else {
//					state = STATE_ERROR;
//				}
			}
		} else {
			state = STATE_ERROR;
		}
	}
//
//	private String readKey() {
//		String keyName;
//		int keyLength;
//		int keyStart = currentIndex;
//
//		skipSpaces();
//
//		if (getCurrentChar() == '}') {
////			state = STATE_SUCCESS;
//			return null;
//		} else if (getCurrentChar() == '\"') {
//			currentIndex++;
//			keyLength = skipKeyName();
//			if (getCurrentChar() == '\"' && keyLength > 0) {
//				keyName = body.substring(keyStart, currentIndex);
//				if (!keys.contains(keyName)) {
//					keys.add(keyName);
////					state = STATE_COLON;
//					return body.substring(keyStart, currentIndex + 1);
//				} else {
////					state = STATE_ERROR;
//					return null;
//				}
//			}
//		} else {
////			state = STATE_ERROR;
//			return null;
//		}
//	}
//
	private void checkColon() {
		skipSpaces();
		state = getCurrentChar() == ':' ? STATE_VALUE : STATE_ERROR;
	}


	private void getValue() {
		Object value = null;

		skipSpaces();

		switch (getCurrentChar()) {
			case '\"' -> value = getValueString();
			case '{' -> value = getValueBlock();
			case '[' -> getValueArray();
			case 't', 'f', 'n' -> value = checkValueSpecial();
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> value = getValueNumber();
			default -> state = STATE_ERROR;
		}

		if (value != null) {
//			System.out.println(((String) value));
			state = STATE_NEXT;
		}
	}


	private Object getValueString() {
		int startValue = currentIndex;

		currentIndex++;
		skipValueString();
		return getCurrentChar() == '\"' ? body.substring(startValue, currentIndex + 1): null;
	}


	private String getValueNumber() {
		boolean afterPoint = false;
		boolean afterExp = false;
		int startNumber = currentIndex;

		if (getCurrentChar() == '-') {
			if (currentIndex < endIndex - 1) {
				currentIndex++;
			} else {
				return null;
			}
		}

		if (getCurrentChar() == '0') {
			if (currentIndex < endIndex - 1) {
				currentIndex++;
			} else {
				return null;
			}
		} else if (Character.isDigit(getCurrentChar())) {
			while (currentIndex < endIndex - 1 && Character.isDigit(getCurrentChar())) {
				currentIndex++;
			}
		} else {
			return null;
		}

		if (getCurrentChar() == '.') {
			if (currentIndex < endIndex - 1) {
				currentIndex++;
				while (currentIndex < endIndex - 1 && Character.isDigit(getCurrentChar())) {
					currentIndex++;
					afterPoint = true;
				}
				if (!afterPoint) {
					return null;
				}
			} else {
				return null;
			}
		}

		if (getCurrentChar() == '-' || getCurrentChar() == '+') {
			if (currentIndex < endIndex - 1) {
				currentIndex++;
				if (currentIndex < endIndex - 1 && Character.toLowerCase(getCurrentChar()) == 'e') {
					currentIndex++;
					if (getCurrentChar() == '0') {
						return null;
					}
					while (currentIndex < endIndex - 1 && Character.isDigit(getCurrentChar())) {
						afterExp = true;
						currentIndex++;
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
		currentIndex--;
		return body.substring(startNumber, currentIndex + 1);
	}


	private int strInStr(String mainStr, int startIndex, String expectedStr) {
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


	private String checkValueSpecial() {
		String valueSpecial;

		switch (getCurrentChar()) {
			case 't' -> valueSpecial = "true";
			case 'f' -> valueSpecial = "false";
			case 'n' -> valueSpecial = "null";
			default -> {
				return null;
			}
		}

		if (strInStr(body, currentIndex, valueSpecial) == 0) {
			currentIndex += valueSpecial.length() - 1;
			return valueSpecial;
		} else {
			return null;
		}
	}


	private JsonParser getValueBlock() {
		JsonParser jsonParser;
		int closeBracketIndex = JsonCommon.getNextBlockBracket(body, currentIndex);

		if (closeBracketIndex != -1) {
			try {
				jsonParser = new JsonParser(body.substring(currentIndex, closeBracketIndex + 1));
			} catch (JsonFormatException je) {
				return null;
			}
			currentIndex = closeBracketIndex;;
			return jsonParser;
		} else {
			return null;
		}
	}


	// TODO: 15.01.2022 дописать :-----------(
	private void getValueArray() {
		int startArrayIndex = currentIndex;
		int endArrayIndex;

		endArrayIndex = JsonCommon.getNextBlockBracket(body, startArrayIndex);
		if (endArrayIndex == -1) {
			state = STATE_ERROR;
			return;
		}

		// TODO: 17.01.2022
	}


	private void defineArrayElementType() {
		String arrType = ARRAY_NULLS;

		switch (getCurrentChar()) {
			case '\"' -> arrType = ARRAY_STRINGS;
			case 't', 'f' -> arrType = ARRAY_BOOLEANS;
			case 'n' -> arrType = ARRAY_NULLS;
			case '{' -> arrType = ARRAY_BLOCKS;
			case '[' -> arrType = ARRAY_ARRAYS;
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> arrType = ARRAY_NUMBERS;
			default -> state = STATE_ERROR;
		}
	}


	private void checkNext() {
		skipSpaces();

		if (getCurrentChar() == ',') {
			state = STATE_CHECK_KEY;
		} else if (getCurrentChar() == '}') {
			state = STATE_SUCCESS;
		} else {
			state = STATE_ERROR;
		}
	}


	private void checkSuccess() {
		while (currentIndex < endIndex && Character.isWhitespace(getCurrentChar())) {
			currentIndex++;
		}
		state = currentIndex == endIndex ? STATE_SUCCESS : STATE_ERROR;
	}


	public int checkFormat() {
		for (; currentIndex < endIndex; currentIndex++) {
			switch (state) {
				case STATE_START_BLOCK:
					checkStartBlock();
					break;
				case STATE_CHECK_KEY:
					checkKey();
					break;
				case STATE_COLON:
					checkColon();
					break;
				case STATE_VALUE:
					getValue();
					break;
				case STATE_NEXT:
					checkNext();
					break;
				case STATE_SUCCESS:
					checkSuccess();
					break;
				case STATE_ERROR:
					return -1;
			}
		}
		return state.equals(STATE_SUCCESS) ? 0 : -1;
	}
}
