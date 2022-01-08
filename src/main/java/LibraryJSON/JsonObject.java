package LibraryJSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject {
	private Map<Object, Object> map;

	public JsonObject() {
		map = new HashMap<>();
	}

	public JsonObject(Map map) {
		this.map = map;
	}

	public void addNewValue(Object key, Object value) {
		map.put(key, value);
	}

	public String getValue(String path) {
		// todo добавить разделение по уровням структур
		return (String) map.get(path);
	}

	public Set getAllKeys() {
		return map.keySet();
	}
}
