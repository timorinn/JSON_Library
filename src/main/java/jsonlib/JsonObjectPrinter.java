package jsonlib;

import jsonlib.exceptions.JsonFormatException;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonObjectPrinter {
	public static String printWithoutSpaces(JsonObjectImp jsonObjectImp) {
		return printWithSpaces(jsonObjectImp, 1, false, false);
	}

	public static String printWithSpaces(JsonObjectImp jsonObjectImp) {
		return printWithSpaces(jsonObjectImp, 0, true, false);
	}

	private static String printWithSpaces(JsonObjectImp jsonObjectImp, int countTabs, boolean isSpaces, boolean needOffset) {
		StringBuilder stringResult = new StringBuilder();
		String key;
		Object value;

		appendSpaces(stringResult, "\t", needOffset ? countTabs : 0, isSpaces);
		stringResult.append('{');
		appendSpaces(stringResult, "\n", 1, isSpaces);

		Iterator<String> mapIter = jsonObjectImp.getKeys().iterator();

		while (mapIter.hasNext()) {
			key = mapIter.next();
			appendSpaces(stringResult, "\t", countTabs + 1, isSpaces);
			stringResult.append('"').append(key).append("\":");
			appendSpaces(stringResult, " ", 1, isSpaces);

			try {
				value = jsonObjectImp.getValue(key);
			} catch (JsonFormatException e) {
				return null;
			}
			if (JsonObjectImp.class.equals(value.getClass())) {
				stringResult.append(printWithSpaces((JsonObjectImp) value, countTabs + 1, isSpaces, false));
			} else if (String.class.equals(value.getClass())) {
				stringResult.append((String) value);
			} else if (ArrayList.class.equals(value.getClass())) {
				try {
					processArray(stringResult, (ArrayList<Object>) value, countTabs, isSpaces);
				} catch (JsonFormatException e) {
					return null;
				}
			}
			if (mapIter.hasNext()) { stringResult.append(','); }
			appendSpaces(stringResult, "\n", 1, isSpaces);
		}
		appendSpaces(stringResult, "\t", countTabs, isSpaces);
		stringResult.append("}");
		return stringResult.toString();
	}

	private static void processArray(StringBuilder stringResult,
									 ArrayList<Object> ar, int countTabs, boolean isSpaces) throws JsonFormatException {
		stringResult.append('[');
		appendSpaces(stringResult, "\n", 1, isSpaces);
		Iterator<Object> arIter = ar.iterator();
		Object value;

		while (arIter.hasNext()) {
			value = arIter.next();

			if (JsonObjectImp.class.equals(value.getClass())) {
				stringResult.append(printWithSpaces((JsonObjectImp) value, countTabs + 2, isSpaces, true));
			} else if (String.class.equals(value.getClass())) {
				stringResult.append((String) value);
			} else if (ArrayList.class.equals(value.getClass())) {
				processArray(stringResult, (ArrayList<Object>) value, countTabs + 1, isSpaces);
			}
		}
		appendSpaces(stringResult, "\n", 1, isSpaces);
		appendSpaces(stringResult, "\t", countTabs + 1, isSpaces);
		stringResult.append("]");
	}

	private static void appendSpaces(StringBuilder stringResult, String s, int repeat, boolean isSpaces) {
		if (isSpaces) {
			if (repeat == 1) {
				stringResult.append(s);
			} else {
				stringResult.append(s.repeat(repeat));
			}
		}
	}
}
