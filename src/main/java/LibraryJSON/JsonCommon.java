package LibraryJSON;

public class JsonCommon {

	public static int getNextBracker(String s, int startIndex, char openBracket) {
		char closeBracket;
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
			cntBrackets += (s.charAt(i) == openBracket) ? 1 : 0;
			cntBrackets -= (s.charAt(i) == closeBracket) ? 1 : 0;

			if (cntBrackets == 0) {
				return i;
			}
		}
		return -1;
	}
}
