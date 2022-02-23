package LibraryJSON;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonObjectPrinter {
	public static String printWithoutSpaces(JsonObject jsonObject) {
		return printWithSpaces(jsonObject, 1, false);
	}

	public static String printWithSpaces(JsonObject jsonObject) {
		return printWithSpaces(jsonObject, 1, true);
	}

	private static String printWithSpaces(JsonObject jsonObject, int tabs, boolean isSpaces) {
		StringBuilder stringResult = new StringBuilder();
		String key;
		Object value;

		stringResult.append('{');
		appendSpaces(stringResult, "\n", 1, isSpaces);

		Iterator<String> mapIter = jsonObject.map.keySet().iterator();

		while (mapIter.hasNext()) {
			key = mapIter.next();

			appendSpaces(stringResult, "\t", tabs, isSpaces);
			stringResult.append('"').append(key).append("\":");
			appendSpaces(stringResult, " ", 1, isSpaces);

			value = jsonObject.map.get(key);

			if (JsonObject.class.equals(value.getClass())) {
				stringResult.append(printWithSpaces((JsonObject) value, tabs + 1, isSpaces));
			} else if (String.class.equals(value.getClass())) {
				stringResult.append((String) value);
			} else if (ArrayList.class.equals(value.getClass())) {
				stringResult.append('[');
				appendSpaces(stringResult, "\n", 1, isSpaces);

				ArrayList<String> ar = (ArrayList) value;
				Iterator<String> arIter = ar.iterator();

				while (arIter.hasNext()) {
					appendSpaces(stringResult, "\t", tabs + 1, isSpaces);
					stringResult.append(arIter.next());
					if (arIter.hasNext()) { stringResult.append(','); }
					appendSpaces(stringResult, "\n", 1, isSpaces);
				}
				appendSpaces(stringResult, "\t", tabs, isSpaces);
				stringResult.append("]");
			}
			if (mapIter.hasNext()) { stringResult.append(','); }
			appendSpaces(stringResult, "\n", 1, isSpaces);
		}
		appendSpaces(stringResult, "\t", tabs - 1, isSpaces);
		stringResult.append("}");
		return stringResult.toString();
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
