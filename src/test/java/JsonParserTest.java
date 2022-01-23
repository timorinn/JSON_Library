import LibraryJSON.JsonFormatException;
import LibraryJSON.JsonObject;
import LibraryJSON.JsonParser;
import org.junit.Assert;
import org.junit.Test;


public class JsonParserTest {

//	@Test
//	public void correctEmpty() {
//		Assert.assertEquals(0, new JsonParser("{}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("{}	 	").checkFormat());
//		Assert.assertEquals(0, new JsonParser("{		}	 	").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{}	 	").checkFormat());
//		Assert.assertEquals(0, new JsonParser("{	 	}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{	 	}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{	 	}	 	").checkFormat());
//	}
//
//
//	@Test
//	public void incorrectEmpty() {
//		Assert.assertEquals(-1, JsonParser("").checkFormat());
//		Assert.assertEquals(-1, JsonParser("  	{  ").checkFormat());
//		Assert.assertEquals(-1, JsonParser("  	{").checkFormat());
//		Assert.assertEquals(-1, JsonParser("}").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("{_}").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("   } ").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("{     }}").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("{{    }").checkFormat());
//	}
//

	@Test
	public void correctString_1() {
		String fileName = "correct/string/1";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("\"value\"", jsonObject.getValue("key"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctString_2() {
		String fileName = "correct/string/2";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("\"  v a _ lue \"", jsonObject.getValue("key"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctString_3() {
		String fileName = "correct/string/3";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("\"  v a _ l\n   u  e \"", jsonObject.getValue("key"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}


	@Test
	public void correctNumber_1() {
		String fileName = "correct/number/1";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("123", jsonObject.getValue("k"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctNumber_2() {
		String fileName = "correct/number/2";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("-0.123", jsonObject.getValue("k"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctNumber_3() {
		String fileName = "correct/number/3";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("123+e18", jsonObject.getValue("k"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctNumber_4() {
		String fileName = "correct/number/4";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("0", jsonObject.getValue("k"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctNumber_5() {
		String fileName = "correct/number/5";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("-0.0", jsonObject.getValue("k"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}


	@Test
	public void correctSpecial_1() {
		String fileName = "correct/special/1";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctSpecial_2() {
		String fileName = "correct/special/2";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("false", jsonObject.getValue("2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctSpecial_3() {
		String fileName = "correct/special/3";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("null", jsonObject.getValue("2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}


// TODO: 23.01.2022
//	@Test
//	public void correctArray_1() {
//		String fileName = "correct/array/1";
//		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
//		JsonObject jsonObject;
//
//		if (jsonText != null) {
//			try {
//				jsonObject = JsonParser.getJsonObject(jsonText);
//				//Assert.assertEquals("null", jsonObject.getValue("2"));
//			} catch (JsonFormatException e) {
//				Assert.fail(e.getMessage());
//			}
//		} else {
//			Assert.fail("File '" + fileName + "' does not exists.");
//		}
//	}

	@Test
	public void correctArray_2() {
		String fileName = "correct/array/2";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {

				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/[0]"));
				Assert.assertEquals("false", jsonObject.getValue("key/[1]"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctArray_3() {
		String fileName = "correct/array/3";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/[0]"));
				Assert.assertEquals("false", jsonObject.getValue("key/[1]"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctArray_4() {
		String fileName = "correct/array/4";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/[0]"));
				Assert.assertEquals("false", jsonObject.getValue("key/[1]"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

//	@Test
//	public void incorrectNumber() {
//		// TODO: 16.01.2022
//	}


	@Test
	public void correctBlock_1() {
		String fileName = "correct/block/1";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {

				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/key"));
				Assert.assertEquals("\"jojo\"", jsonObject.getValue("key/key2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctBlock_2() {
		String fileName = "correct/block/2";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {

				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/key"));
				Assert.assertEquals("\"jojo\"", jsonObject.getValue("key/key2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctBlock_3() {
		String fileName = "correct/block/3";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {

				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/key"));
				Assert.assertEquals("\"jo}{jo\"", jsonObject.getValue("key/key2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctBlock_4() {
		String fileName = "correct/block/4";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {

				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/key"));
				Assert.assertEquals("\"}o\\\"}  j }\"", jsonObject.getValue("key/key2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

	@Test
	public void correctBlock_5() {
		String fileName = "correct/block/5";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("true", jsonObject.getValue("key/key"));
				Assert.assertEquals("\"}o\\\"}j}\"", jsonObject.getValue("key/key2"));
				Assert.assertEquals("\"}s\\\"tr\"", jsonObject.getValue("k/k2/s"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}
}