package jsonlib;

import jsonlib.exceptions.JsonFormatException;

import java.util.Set;

public interface JsonObject {
	public void addNewValue(String s, Object o) throws JsonFormatException;
	public Set getKeys();
}
