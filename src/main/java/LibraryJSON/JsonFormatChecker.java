package LibraryJSON;

import java.util.Stack;

public class JsonFormatChecker {
	final private static String CHECK_START_BLOCK = "CHECK_START_BLOCK";
	final private static String CHECK_KEY_FIRST_QUOTER = "CHECK_KEY_FIRST_QUOTER";
	final private static String CHECK_KEY_SYMBOLS = "CHECK_KEY_SYMBOLS";
	final private static String CHECK_KEY_SECOND_QUOTER = "CHECK_KEY_SECOND_QUOTER";
	final private static String CHECK_COLON = "CHECK_COLON";
	final private static String CHECK_VALUE = "CHECK_VALUE";
	final private static String CHECK_VALUE_SIMPLE_ARRAY = "CHECK_VALUE_SIMPLE_ARRAY";
	final private static String CHECK_VALUE_BLOCK_ARRAY = "CHECK_VALUE_BLOCK_ARRAY";
	final private static String CHECK_VALUE_END_COMMA = "CHECK_VALUE_END_COMMA";

	public static int checkFormatBody(String body) {
		// Stack<Character> stack = new Stack<>();
		int bodySize = body.length();
		String status = CHECK_START_BLOCK;
		char posChar;

		for (int i = 0; i < bodySize - 1; i++) {
			posChar = body.charAt(i);

			if (status == CHECK_START_BLOCK) {

			} else if (status == CHECK_KEY_FIRST_QUOTER) {

			} else if (status == CHECK_KEY_SYMBOLS) {

			} else if (status == CHECK_KEY_SECOND_QUOTER) {

			} else if (status == CHECK_COLON) {

			} else if (status == CHECK_VALUE) {
				// todo тут точно будет рекурсия, потому что нигде больше ее быть не может
			}
			// todo завершить цепочку статусов
		}
		return  0;
	}
}
