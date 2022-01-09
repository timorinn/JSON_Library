package LibraryJSON;

import java.util.HashMap;

public class JsonParser {
	private String body;
	private HashMap<Object, Object> paramArray = new HashMap();
	private int ptr = 1;
	private int bodySize;

	public JsonParser(String body) {
		//todo
		if (body == null) {
			throw new NullPointerException("body is null");
		}
		ptr = 1;
		setBody(body);
		bodySize = body.length();
	}

	public String getBody() {
		return body;
	}

	private void setBody(String body) {
		this.body = body;
	}

	private void checkBodyLength() throws JsonFormatException {
		if (body.length() < 2) {
			throw new JsonFormatException("Length of body (" + body.length() + ") is too small.");
		}
	}

	private void checkFirstAndLastChar() throws JsonFormatException {
		char firstChar = body.charAt(0);
		char lastChar = body.charAt(body.length() - 1);

		if (firstChar != '{') {
			throw new JsonFormatException("Incorrect first symbol '" + firstChar + "' expected '{'.");
		}
		if (lastChar != '}') {
			throw new JsonFormatException("Incorrect last symbol '" + lastChar + "' expected '}'.");
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

	private int getParameterEndIndex(int startParameterIndex) throws JsonFormatException {
		int endParameterIndex;
		char c;

		//экономия ресурсов
		for (int i = startParameterIndex + 1; i < body.length() - 1; i++) {
			c = body.charAt(i);

			if (c == '\"') {
				return i;
			} else if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
				throw new JsonFormatException("missing second quoter.");
			}
		}
		throw new JsonFormatException("missing second quoter.");
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
			return getNextCloseBracket(startValueIndex, startValueChar) + 1;
		}
	}

	private void checkFirstParameterQuoter(int startParameterIndex) throws JsonFormatException {
		if (body.charAt(startParameterIndex) != '\"') {
			throw new JsonFormatException("first symbol must be quoter.");
		}
	}

	private void checkFirstQuoter() {

	}

	private void checkColon(int endParameterIndex) throws JsonFormatException {
		if (body.charAt(endParameterIndex + 1) != ':') {
			throw new JsonFormatException("Missing colon in position " + (endParameterIndex + 1) + ".");
		}
	}

	private void getParameterAndValue(int startParameterIndex) throws JsonFormatException {
		int endParameterIndex;
		String parameter;
		int startValueIndex;
		int endValueIndex;
		Object value;

		if (startParameterIndex == bodySize - 1) {
			ptr = -1;
			return;
		}

		checkFirstParameterQuoter(startParameterIndex);
		endParameterIndex = getParameterEndIndex(startParameterIndex);
		parameter = body.substring(startParameterIndex, endParameterIndex + 1);

		System.out.println("parameter=" + parameter);

		checkColon(endParameterIndex);
		startValueIndex = endParameterIndex + 2;
		endValueIndex = getValueEndIndex(startValueIndex);

		value = body.substring(startValueIndex, endValueIndex);

		System.out.println("value=" + value.toString());
		System.out.println("NEW CHAR=" + body.charAt(endValueIndex));

		paramArray.put(parameter, value);

		this.ptr = body.charAt(endValueIndex) == ',' ? endValueIndex + 1 : -1;
	}

	public JsonObject parseBody() throws Exception {
		new JsonFormatChecker(body).checkFormat();

//		checkBodyLength();
//		checkFirstAndLastChar();
//
//		System.out.println("POINT 1");
//
//		while (ptr != -1) {
//			getParameterAndValue(ptr);
//		}
//		return new JSONObject(paramArray);
		return null;
	}
}
