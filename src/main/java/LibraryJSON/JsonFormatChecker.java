package LibraryJSON;

import java.util.HashSet;

public class JsonFormatChecker {
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
	private int currentIndex;
	private int bodyLength;

	// todo
//	private String processMessage;

	public JsonFormatChecker(String body) {
		this.body = body.trim();
		this.bodyLength = this.body.length();
		this.state = STATE_START_BLOCK;
		this.currentIndex = 0;
		this.keys = new HashSet<>();
	}


	private char getCurrentChar() {
		return body.charAt(currentIndex);
	}


	private void skipSpaces() {
		//todo
		while (currentIndex < bodyLength - 1 && Character.isWhitespace(getCurrentChar())) {
			currentIndex++;
		}
	}


	private void skipValueString() {
		while (currentIndex < bodyLength - 1) {
			if (getCurrentChar() == '\"' && currentIndex > 0 && body.charAt(currentIndex - 1) != '\\') {
				break;
			}
			currentIndex++;
		}
	}


	private int skipKeyName() {
		int cnt = 0;

		while (currentIndex < bodyLength - 1 && getCurrentChar() != '\"'
				&& (Character.isAlphabetic(getCurrentChar()) || Character.isDigit(getCurrentChar()))) {
			currentIndex++;
			cnt++;
		}
		return cnt;
	}


	private void checkStartBlock() {
		state = getCurrentChar() == '{' ? STATE_CHECK_KEY : STATE_ERROR;
	}


	//todo можно сделать лучше, выглядит некрасиво
	private void checkKey() {
		String keyName;
		int keyLength;
		int keyStart;

		skipSpaces();

		if (getCurrentChar() == '}' && currentIndex == bodyLength - 1) {
			state = STATE_SUCCESS;
		} else if (getCurrentChar() == '\"') {
			keyStart = currentIndex++;
			keyLength = skipKeyName();
			if (getCurrentChar() == '\"' && keyLength > 0) {
				keyName = body.substring(keyStart, currentIndex);
				if (!keys.contains(keyName)) {
					keys.add(keyName);
					state = STATE_COLON;
				} else {
					state = STATE_ERROR;
				}
			}
		} else {
			state = STATE_ERROR;
		}
	}


	private void checkColon() {
		skipSpaces();
		state = getCurrentChar() == ':' ? STATE_VALUE : STATE_ERROR;
	}


	private void checkValue() {
		skipSpaces();

		switch (getCurrentChar()) {
			case '\"' -> checkValueString();
			case '{' -> checkValueBlock();
			case '[' -> checkValueArray();
			case 't', 'f', 'n' -> checkValueSpecial();
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> checkValueNumber();
			default -> state = STATE_ERROR;
		}
	}


	private void checkValueString() {
		currentIndex++;
		skipValueString();
		state = getCurrentChar() == '\"' ? STATE_NEXT : STATE_ERROR;
	}


	private void checkValueNumber() {
		boolean afterPoint = false;
		boolean afterExp = false;

		if (getCurrentChar() == '-') {
			if (currentIndex < bodyLength - 1) {
				currentIndex++;
			} else {
				state = STATE_ERROR;
				return;
			}
		}

		if (getCurrentChar() == '0') {
			if (currentIndex < bodyLength - 1) {
				currentIndex++;
			} else {
				state = STATE_ERROR;
				return;
			}
		} else if (Character.isDigit(getCurrentChar())) {
			while (currentIndex < bodyLength - 1 && Character.isDigit(getCurrentChar())) {
				currentIndex++;
			}
		} else {
			state = STATE_ERROR;
			return;
		}

		if (getCurrentChar() == '.') {
			if (currentIndex < bodyLength - 1) {
				currentIndex++;
				while (currentIndex < bodyLength - 1 && Character.isDigit(getCurrentChar())) {
					currentIndex++;
					afterPoint = true;
				}
				if (!afterPoint) {
					state = STATE_ERROR;
					return;
				}
			} else {
				state = STATE_ERROR;
				return;
			}
		}

		if (getCurrentChar() == '-' || getCurrentChar() == '+') {
			if (currentIndex < bodyLength - 1) {
				currentIndex++;
				if (currentIndex < bodyLength - 1 && Character.toLowerCase(getCurrentChar()) == 'e') {
					currentIndex++;
					if (getCurrentChar() == '0') {
						state = STATE_ERROR;
						return;
					}
					while (currentIndex < bodyLength - 1 && Character.isDigit(getCurrentChar())) {
						afterExp = true;
						currentIndex++;
					}
					if (!afterExp) {
						state = STATE_ERROR;
						return;
					}
				} else {
					state = STATE_ERROR;
					return;
				}
			} else {
				state = STATE_ERROR;
				return;
			}
		}
		currentIndex--;
		state = STATE_NEXT;
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


	private void checkValueSpecial() {
		String valueSpecial;

		switch (getCurrentChar()) {
			case 't' -> valueSpecial = "true";
			case 'f' -> valueSpecial = "false";
			case 'n' -> valueSpecial = "null";
			default -> { state = STATE_ERROR; return;}
		}

		if (strInStr(body, currentIndex, valueSpecial) == 0) {
			currentIndex += valueSpecial.length() - 1;
			state = STATE_NEXT;
		} else {
			state = STATE_ERROR;
		}
	}


	private void checkValueBlock() {
		int closeBracketIndex = JsonCommon.getNextBlockBracket(body, currentIndex);

		if (closeBracketIndex != -1
				&& new JsonFormatChecker(body.substring(currentIndex, closeBracketIndex + 1)).checkFormat() != -1) {
			currentIndex = closeBracketIndex;
			state = STATE_NEXT;
		} else {
			state = STATE_ERROR;
		}
	}


	// TODO: 15.01.2022 дописать :-----------(
	private void checkValueArray() {
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


	public int checkFormat() {
		for (; currentIndex < bodyLength; currentIndex++) {
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
					checkValue();
					break;
				case STATE_NEXT:
					checkNext();
					break;
				case STATE_SUCCESS:
					state = STATE_ERROR;
					break;
				case STATE_ERROR:
					return -1;
			}
		}
		return state.equals(STATE_SUCCESS) ? 0 : -1;
	}
}
