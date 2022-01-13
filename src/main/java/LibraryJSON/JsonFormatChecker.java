package LibraryJSON;

import java.util.HashSet;

public class JsonFormatChecker {
	final private static String STATE_START_BLOCK = "STATE_START_BLOCK";
	final private static String STATE_KEY_FIRST_QUOTER = "STATE_KEY_FIRST_QUOTER";
	final private static String STATE_KEY_NAME_AND_SECOND_QUOTER = "STATE_KEY_NAME_AND_SECOND_QUOTER";
	final private static String STATE_COLON = "STATE_COLON";
	final private static String STATE_VALUE = "STATE_VALUE";
	final private static String STATE_CHECK_END = "STATE_CHECK_END";
	final private static String STATE_SUCCESS = "STATE_SUCCESS";
	final private static String STATE_ERROR = "STATE_ERROR";
	final private static String STATE_NEXT = "STATE_NEXT";

	final private static String ARRAY_STRINGS = "ARRAY_STRING";
	final private static String ARRAY_NUMBERS = "ARRAY_NUMBERS";
	final private static String ARRAY_BOOLEANS = "ARRAY_BOOLEANS";
	final private static String ARRAY_BLOCKS = "ARRAY_BLOCKS";

	private HashSet<String> keys;
	private String state;
	private String body;
	private int keyStart;
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


	private int skipValueString() {
		//todo обрабатывать \" (?)
		int cnt = 0;

		while (currentIndex < bodyLength - 1 && getCurrentChar() != '\"') {
			currentIndex++;
			cnt++;
		}
		return cnt;
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
		state = getCurrentChar() == '{' ? STATE_KEY_FIRST_QUOTER : STATE_ERROR;
	}


	private void checkKeyFirstQuoter() {
		skipSpaces();

		if (getCurrentChar() == '\"') {
			keyStart = currentIndex + 1;
			state = STATE_KEY_NAME_AND_SECOND_QUOTER;
		} else if (getCurrentChar() == '}' && currentIndex == bodyLength - 1) {
			state = STATE_SUCCESS;
		} else {
			state = STATE_ERROR;
		}
	}

	private void checkKeyNameAndSecondQuoter() {
		int keyLength;
		String keyName;

		state = STATE_ERROR;
		keyLength = skipKeyName();

		if (getCurrentChar() == '\"' && keyLength > 0) {
			keyName = body.substring(keyStart, currentIndex);
			if (!keys.contains(keyName)) {
				keys.add(keyName);
				state = STATE_COLON;
			}
		}
	}


	private void checkColon() {
		skipSpaces();
		state = getCurrentChar() == ':' ? STATE_VALUE : STATE_ERROR;
	}


	private void checkValue() {
		skipSpaces();

		if (getCurrentChar() == '\"') {
			currentIndex++;
			checkValueString();
		} else if (getCurrentChar() == 't' && checkValueSpecial("true") == 0) {
			//todo надо сделать поизящнее
			currentIndex += 3;
			state = STATE_NEXT;
		} else if (getCurrentChar() == 'f' && checkValueSpecial("false") == 0) {
			//todo надо сделать поизящнее
			currentIndex += 4;
			state = STATE_NEXT;
		} else if (getCurrentChar() == 'n' && checkValueSpecial("null") == 0) {
			//todo надо сделать поизящнее
			currentIndex += 3;
			state = STATE_NEXT;
		} else if (getCurrentChar() == '{') {
			checkValueBlock();
		} else if (getCurrentChar() == '[') {
			checkValueArray();
		} else {
			state = STATE_ERROR;
		}
	}


	private void checkValueString() {
		skipValueString();

		state = getCurrentChar() == '\"' ? STATE_NEXT : STATE_ERROR;
	}


	/**
	 * Check value: true, false, null
	 */
	private int checkValueSpecial(String value) {
		int valueLength = value.length();

		// todo проверить условие
		if (currentIndex + valueLength < bodyLength ) {
			for (int i = 0; i < valueLength; i++) {
				if (value.charAt(i) != body.charAt(currentIndex + i)) {
					return -1;
				}
			}
			return 0;
		} else {
			return -1;
		}
	}


	private void checkValueBlock() {
		int closeBracketIndex = JsonCommon.getNextBracker(body, currentIndex, '{');

		if (closeBracketIndex != -1
				&& new JsonFormatChecker(body.substring(currentIndex, closeBracketIndex + 1)).checkFormat() != -1) {
			currentIndex = closeBracketIndex;
			state = STATE_NEXT;
		} else {
			state = STATE_ERROR;
		}
	}


	private void checkValueArray() {

	}


	private void checkNext() {
		skipSpaces();

		//todo чувствую тут чот не так
		if (getCurrentChar() == ',') {
			state = STATE_KEY_FIRST_QUOTER;
		} else if (getCurrentChar() == '}') {
			state = STATE_SUCCESS;
		} else {
			state = STATE_ERROR;
		}
	}


	public int checkFormat() {
		for (; currentIndex < bodyLength; currentIndex++) {
			switch (state) {
				case STATE_SUCCESS:
					//todo
					state = STATE_ERROR;
					break;
				case STATE_ERROR:
					//todo
					return -1;
				case STATE_START_BLOCK:
					checkStartBlock();
					break;
				case STATE_KEY_FIRST_QUOTER:
					checkKeyFirstQuoter();
					break;
				case STATE_KEY_NAME_AND_SECOND_QUOTER:
					checkKeyNameAndSecondQuoter();
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
				case STATE_CHECK_END:
					if (body.charAt(currentIndex) == '}') {
						state = STATE_SUCCESS;
					}
					break;
			}
			// todo завершить цепочку статусов
		}
		return state.equals(STATE_SUCCESS) ? 0 : -1;
	}
}
