package LibraryJSON;

public class JsonCommon {

	public static int getNextBlockBracket(String s, int startIndex) {
		char openBracket = s.charAt(startIndex);
		char closeBracket;
		boolean isText = false;
		int cntBrackets = 0;

		switch (openBracket) {
			case '{':
				closeBracket = '}';
				break;
			case '[':
				closeBracket = ']';
				break;
			case '(':
				closeBracket = ')';
				break;
			default:
				return -1;
		}

		for (int i = startIndex; i < s.length(); i++) {
//			System.out.println(i + ") CURRENT CHAR: " + s.charAt(i) + " |  isText: " + isText);
			if (s.charAt(i) =='\"') {
				if (!isText) {
					isText = true;
				} else if (s.charAt(i - 1) != '\\') {
					isText = false;
				}
			}
			cntBrackets += (s.charAt(i) == openBracket && !isText) ? 1 : 0;
			cntBrackets -= (s.charAt(i) == closeBracket && !isText) ? 1 : 0;

			if (cntBrackets == 0) {
				return i;
			}
		}
		return -1;
	}
}
