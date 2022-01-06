package LibraryJSON;

import java.util.HashMap;

public class JSONParser {
	private String body;
	private HashMap paramArray = new HashMap();
	private int ptr;

	public JSONParser(String body) {
		//todo
		// чото вообще не помог ассерт
		assert body != null : "body should not be null";

		ptr = 1;

		setBody(body);
	}

	public String getBody() {
		return body;
	}

	private void setBody(String body) {
		this.body = body;
	}

	private void checkBodyLength() throws JSONFormatException {
		if (body.length() < 2) {
			throw new JSONFormatException("Length of body (" + body.length() + ") is too small.");
		}
	}

	private void checkFirstAndLastChar() throws JSONFormatException {
		char firstChar = body.charAt(0);
		char lastChar = body.charAt(body.length() - 1);

		if (firstChar != '{') {
			throw new JSONFormatException("Incorrect first symbol '" + firstChar + "' expected '{'.");
		}
		if (lastChar != '}') {
			throw new JSONFormatException("Incorrect last symbol '" + lastChar + "' expected '}'.");
		}
	}

	// todo возможно стоит не int, а void с использованием исключений
	private int getNextCloseBracket(int startPos, char openBracket) {
		char closeBracket = (openBracket == '{' ? '}' : ']');
		int cntBrackets = 1;

		for (int i = startPos + 1; i < body.length() - 1; i++) {
			if (body.charAt(i) == openBracket) {
				cntBrackets++;
			} else if (body.charAt(i) == closeBracket) {
				cntBrackets--;
			}
			if (cntBrackets == 0) {
				return i;
			}
		}
		return -1;
	}

	private int getParameterEndIndex(int startParameterIndex) {
		int endParameterIndex;
		char c;

		//экономия ресурсов
		for (int i = startParameterIndex + 1; i < body.length() - 1; i++) {
			c = body.charAt(i);

			if (c == '\"') {
				return i;
			} else if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
				return -1;
			}
		}

		return -1;
	}

	private int getValueEndIndex(int startValueIndex) {
		char startValueChar = body.charAt(startValueIndex);

		System.out.println("startValueChar: " +  startValueChar);

		if (startValueChar != '{' && startValueChar != '[') {
			if (body.indexOf(',') == -1) {
				return body.indexOf('}');
			} else {
				return body.indexOf(',');
			}
		} else {
			//todo
			return getNextCloseBracket(startValueIndex, startValueChar);
		}
	}

	private void checkColon(int endParameterIndex) throws JSONFormatException {
		if (body.charAt(endParameterIndex + 1) != ':') {
			throw new JSONFormatException("Missing colon in position " + (endParameterIndex + 1) + ".");
		}
	}

	private void getParameterAndValue(int startParameterIndex) throws JSONFormatException {
		int endParameterIndex = getParameterEndIndex(startParameterIndex);
		String parameter = body.substring(startParameterIndex, endParameterIndex + 1);;
		int startValueIndex = endParameterIndex + 2;
		int endValueIndex;
		Object value;

		System.out.println("parameter=" + parameter);

		checkColon(endParameterIndex);

		System.out.println("startValueChar: " +  startValueChar);

		if (startValueChar != '{' && startValueChar != '[') {
			if (body.indexOf(',') == -1) {
				endValueIndex = body.indexOf('}');
				value = body.substring(startValueIndex, endValueIndex);
			} else {
				endValueIndex = body.indexOf(',');
				value = body.substring(startValueIndex, endValueIndex);
			}
		} else {
			//todo
			endValueIndex = getNextCloseBracket(startValueIndex, startValueChar);
			value = body.substring(startValueIndex, endValueIndex + 1);
		}

		System.out.println("value=" + value.toString());

		System.out.println("endValuePos + 1 : " + body.charAt(endValueIndex));

		this.ptr = body.charAt(endValueIndex + 1) == ',' ? endValueIndex + 2 : endValueIndex + 1;
	}


	public JSONObject parseBody() throws Exception {
		int lastCharPos = body.length() - 2;

		System.out.println("start: " +
				body.charAt(0) + " | end: " + body.charAt(lastCharPos));

		checkBodyLength();
		checkFirstAndLastChar();

		getParameterAndValue(ptr);
		getParameterAndValue(ptr);
		getParameterAndValue(ptr);

		return null;
	}
}
