package LibraryJSON;

public class JsonFormatChecker {
	final private static String STATE_START_BLOCK = "STATE_START_BLOCK";
	final private static String STATE_KEY_FIRST_QUOTER = "STATE_KEY_FIRST_QUOTER";
	final private static String STATE_KEY_NAME_AND_SECOND_QUOTER = "STATE_KEY_NAME_AND_SECOND_QUOTER";
	final private static String STATE_COLON = "STATE_COLON";
	final private static String STATE_VALUE = "STATE_VALUE";
	final private static String STATE_VALUE_STRING = "STATE_VALUE_STRING";
	final private static String STATE_VALUE_ARRAY = "STATE_VALUE_ARRAY";
	final private static String STATE_VALUE_BLOCK = "STATE_VALUE_BLOCK";
	final private static String STATE_VALUE_BLOCK_ARRAY = "STATE_VALUE_BLOCK_ARRAY";
	final private static String STATE_VALUE_END_COMMA = "STATE_VALUE_END_COMMA";
	final private static String STATE_CHECK_END = "STATE_CHECK_END";
	final private static String STATE_SUCCESS = "STATE_SUCCESS";
	final private static String STATE_ERROR = "STATE_ERROR";
	final private static String STATE_NEXT = "STATE_NEXT";

	private String state;
	private String body;
	private int index;
	private int bodySize;

	// todo
//	private String processMessage;

	public JsonFormatChecker(String body) {
		this.body = body.trim();
		this.bodySize = this.body.length();
		this.state = STATE_START_BLOCK;
		this.index = 0;
	}

	private char getCurrentChar() {
		return body.charAt(index);
	}

	private void skipSpaces() {
		//todo
		while (index < bodySize - 1 && Character.isWhitespace(getCurrentChar())) {
			index++;
		}
	}

	// todo мне кажец тут что то как то не оч
	private void skipSymbols() {
		//todo обрабатывать \"
		while (index < bodySize - 1 && getCurrentChar() != '\"') {
			index++;
		}
	}

	private void checkStartBlock() {
		state = getCurrentChar() == '{' ? STATE_KEY_FIRST_QUOTER : STATE_ERROR;
	}

	private void checkKeyFirstQuoter() {
		skipSpaces();

		if (getCurrentChar() == '\"') {
			state = STATE_KEY_NAME_AND_SECOND_QUOTER;
		} else if (getCurrentChar() == '}' && index == bodySize - 1) {
			state = STATE_SUCCESS;
		} else {
			state = STATE_ERROR;
		}
	}

	private void checkKeyNameAndSecondQuoter() {
		skipSymbols();
		state = getCurrentChar() == '"' ? STATE_COLON : STATE_ERROR;
	}

	private void checkColon() {
		skipSpaces();
		state = getCurrentChar() == ':' ? STATE_VALUE : STATE_ERROR;
	}

	private void checkValueString() {
		skipSymbols();
		state = getCurrentChar() == '\"' ? STATE_NEXT : STATE_ERROR;
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

	private void checkValue() {
		if (getCurrentChar() == '\"') {
			state = STATE_VALUE_STRING;
		} else if (getCurrentChar() == 't') {
			// todo process TRUE
		} else if (getCurrentChar() == 'f') {
			// todo process FALSE
		} else if (getCurrentChar() == 'n') {
			// todo process NULL/NONE
		} else if (getCurrentChar() == '[') {
			state = STATE_VALUE_ARRAY;
		} else if (getCurrentChar() == '{') {
			state = STATE_VALUE_BLOCK;
		}
	}

	public int checkFormat() {
		for (;index < bodySize; index++) {
			switch (state) {
				case STATE_SUCCESS:
					//todo
					System.out.println("STATE_SUCCESS");
					state = STATE_ERROR;
					break;
				case STATE_ERROR:
					System.out.println("STATE_ERROR");
					//todo
					return -1;
				case STATE_START_BLOCK:
					System.out.println("STATE_START_BLOCK");
					checkStartBlock();
					break;
				case STATE_KEY_FIRST_QUOTER:
					System.out.println("STATE_KEY_FIRST_QUOTER");
					checkKeyFirstQuoter();
					break;
				case STATE_KEY_NAME_AND_SECOND_QUOTER:
					System.out.println("STATE_KEY_NAME_AND_SECOND_QUOTER");
					checkKeyNameAndSecondQuoter();
					break;
				case STATE_COLON:
					System.out.println("STATE_COLON");
					checkColon();
					break;
				case STATE_VALUE:
					System.out.println("STATE_VALUE");
					checkValue();
					break;
				case STATE_VALUE_STRING:
					System.out.println("STATE_VALUE_STRING");
					checkValueString();
					break;
				case STATE_NEXT:
					System.out.println("STATE_NEXT");
					checkNext();
					break;
/*
			} else if (status == STATUS_KEY_SECOND_QUOTER) {
				if (posChar == '\"') {
					status = STATUS_COLON;
				} else if (!Character.isDigit(posChar) && !Character.isAlphabetic(posChar)) {
					throw new JsonFormatException("2");
				}
			} else if (status == STATUS_COLON) {
				if (posChar == ':') {
					status = STATUS_VALUE;
				} else {
					throw new JsonFormatException("2");
				}
			} else if (status == STATUS_VALUE) {
				// todo тут точно будет рекурсия, потому что нигде больше ее быть не может
			}
			*/
				case STATE_CHECK_END:
					System.out.println("STATE_CHECK_END");
					if (body.charAt(index) == '}') {
						state = STATE_SUCCESS;
					}
					break;
			}
			// todo завершить цепочку статусов
		}
		return state.equals(STATE_SUCCESS) ? 0 : -1;
	}
}
