package jsonlib;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonObjectPrinter {
	public static String printWithoutSpaces(JsonObject jsonObject) {
		return printWithSpaces(jsonObject, 1, false, false);
	}

	public static String printWithSpaces(JsonObject jsonObject) {
		return printWithSpaces(jsonObject, 0, true, false);
	}

	private static String printWithSpaces(JsonObject jsonObject, int countTabs, boolean isSpaces, boolean needOffset) {
		StringBuilder stringResult = new StringBuilder();
		String key;
		Object value;

		appendSpaces(stringResult, "\t", needOffset ? countTabs : 0, isSpaces);
		stringResult.append('{');
		appendSpaces(stringResult, "\n", 1, isSpaces);

		Iterator<String> mapIter = jsonObject.map.keySet().iterator();

		while (mapIter.hasNext()) {
			key = mapIter.next();
			appendSpaces(stringResult, "\t", countTabs + 1, isSpaces);
			stringResult.append('"').append(key).append("\":");
			appendSpaces(stringResult, " ", 1, isSpaces);

			value = jsonObject.map.get(key);
			if (JsonObject.class.equals(value.getClass())) {
				stringResult.append(printWithSpaces((JsonObject) value, countTabs + 1, isSpaces, false));
			} else if (String.class.equals(value.getClass())) {
				stringResult.append((String) value);
			} else if (ArrayList.class.equals(value.getClass())) {
				processArray(stringResult, (ArrayList<Object>) value, countTabs, isSpaces);
			}
			if (mapIter.hasNext()) { stringResult.append(','); }
			appendSpaces(stringResult, "\n", 1, isSpaces);
		}
		appendSpaces(stringResult, "\t", countTabs, isSpaces);
		stringResult.append("}");
		return stringResult.toString();
	}

	private static void processArray(StringBuilder stringResult, ArrayList<Object> ar, int countTabs, boolean isSpaces) {
		stringResult.append('[');
		appendSpaces(stringResult, "\n", 1, isSpaces);
		Iterator<Object> arIter = ar.iterator();
		Object value;

		while (arIter.hasNext()) {
			value = arIter.next();

			if (JsonObject.class.equals(value.getClass())) {
				stringResult.append(printWithSpaces((JsonObject) value, countTabs + 2, isSpaces, true));
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
