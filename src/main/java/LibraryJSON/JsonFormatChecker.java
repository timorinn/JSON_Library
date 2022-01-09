package LibraryJSON;

public class JsonFormatChecker {
	final private static String STATUS_START_BLOCK = "STATUS_START_BLOCK";
	final private static String STATUS_KEY_FIRST_QUOTER = "STATUS_KEY_FIRST_QUOTER";
	final private static String STATUS_KEY_SECOND_QUOTER = "STATUS_KEY_SECOND_QUOTER";
	final private static String STATUS_COLON = "STATUS_COLON";
	final private static String STATUS_VALUE = "STATUS_VALUE";
	final private static String STATUS_VALUE_SIMPLE_ARRAY = "STATUS_VALUE_SIMPLE_ARRAY";
	final private static String STATUS_VALUE_BLOCK_ARRAY = "STATUS_VALUE_BLOCK_ARRAY";
	final private static String STATUS_VALUE_END_COMMA = "STATUS_VALUE_END_COMMA";
	final private static String STATUS_CHECK_END = "STATUS_CHECK_END";

	private String status;
	private String body;
	private int index;
	private int bodySize;
	private char indexChar;


	public JsonFormatChecker(String body) {
		this.body = body.trim();
		this.bodySize = body.length();
		this.status = STATUS_START_BLOCK;
		this.index = 0;
	}

	private void skipSpaces() throws JsonFormatException {
		while (index < bodySize && Character.isSpaceChar(body.charAt(index))) {
			index++;
		}
		if (index < bodySize) {
			indexChar = body.charAt(index);
		} else {
			// TODO: 10.01.2022 придумать обработку
			throw new JsonFormatException("not good");
		}
	}

	private void checkStartBlock() throws JsonFormatException {
		if (body.charAt(index) == '{') {
			status = STATUS_KEY_FIRST_QUOTER;
		} else {
			//todo
			throw new JsonFormatException("111");
		}
	}

	public int checkFormat() throws JsonFormatException {
		indexChar = body.charAt(this.index);

		for (;index < bodySize; index++) {

			if (status.equals(STATUS_START_BLOCK)) {
				checkStartBlock();
			}  else if (status.equals(STATUS_KEY_FIRST_QUOTER)) {
				skipSpaces();

				if (indexChar == '\"') {
					status = STATUS_KEY_SECOND_QUOTER;
				} else if (indexChar == '}' && index == bodySize - 1) {

				} else {
					throw new JsonFormatException("2");
				}
			}
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
			// todo завершить цепочку статусов
		}
		return  0;
	}
}
