package LibraryJSON;

import java.util.*;

public class JsonObject {
	public Map<String, Object> map;


	public JsonObject() {
		map = new HashMap<>();
	}

	public JsonObject(Map<String, Object> map) {
		this.map = map;
	}

	public void addNewValue(String key, Object value) throws JsonFormatException {
		if (map.containsKey(key)) {
			throw new JsonFormatException("Key '" + key + "' already exists.");
		}
		map.put(key, value);
	}

	public Set getAllKeys() {
		return map.keySet();
	}

	private int getArrayElementIndex(String part) {
		int partLength = part.length();
		int arrayElementIndex = 0;

		if (partLength >= 3 && part.charAt(0) == '[' && part.charAt(partLength - 1) == ']') {
			for (int i = 1; i < partLength - 1; i++) {
				if (!Character.isDigit(part.charAt(i))) {
					return -1;
				}
				arrayElementIndex = arrayElementIndex * 10 + part.charAt(i) - 48;
			}
			return arrayElementIndex;
		}
		return -1;
	}

	public String getValue(String keyPath) throws JsonFormatException {
		String[] fullPath = keyPath.split("/");
		int arrayElementIndex;
		Object value = this;

		for (String partOfPath : fullPath) {
			if (ArrayList.class.equals(value.getClass())) {
				arrayElementIndex = getArrayElementIndex(partOfPath);
				if (arrayElementIndex != -1) {
					value = ((List<?>) value).get(arrayElementIndex);
				} else {
					throw new JsonFormatException("Incorrect index of array element");
				}
			} else if (JsonObject.class.equals(value.getClass())) {
				value = ((JsonObject) value).map.get(partOfPath);
				if (value == null) {
					throw new JsonFormatException("Incorrect path of value.");
				}
			} else {
				throw new JsonFormatException("Incorrect path of value.");
			}
		}
		return String.class.equals(value.getClass()) ? (String) value : null;
	}
}
