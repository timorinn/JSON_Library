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


	@Test
	public void correctString() {
		TestLibrary.testJsonFile("correctString/1");
		TestLibrary.testJsonFile("correctString/2");
		TestLibrary.testJsonFile("correctString/3");
	}

	@Test
	public void incorrectString() {
		TestLibrary.testJsonFile("incorrectString/1");
		TestLibrary.testJsonFile("incorrectString/2");
	}

	@Test
	public void correctMultiple() {
		TestLibrary.testJsonFile("correctMultiple/1");
		TestLibrary.testJsonFile("correctMultiple/2");
		TestLibrary.testJsonFile("correctMultiple/3");
	}

	@Test
	public void incorrectMultiple() {
		TestLibrary.testJsonFile("incorrectMultiple/1");
		TestLibrary.testJsonFile("incorrectMultiple/2");
	}

	@Test
	public void correctSpecial() {
		TestLibrary.testJsonFile("correctSpecial/1");
		TestLibrary.testJsonFile("correctSpecial/2");
		TestLibrary.testJsonFile("correctSpecial/3");
	}

	@Test
	public void incorrectSpecial() {
		TestLibrary.testJsonFile("incorrectSpecial/1");
		TestLibrary.testJsonFile("incorrectSpecial/2");
		TestLibrary.testJsonFile("incorrectSpecial/3");
	}

	@Test
	public void correctNumber() {
		TestLibrary.testJsonFile("correctNumber/1");
		TestLibrary.testJsonFile("correctNumber/2");
		TestLibrary.testJsonFile("correctNumber/3");
		TestLibrary.testJsonFile("correctNumber/4");
		TestLibrary.testJsonFile("correctNumber/5");
	}

	@Test
	public void incorrectNumber() {
		// TODO: 16.01.2022
	}

	@Test
	public void correctBlock() {
		TestLibrary.testJsonFile("correctBlock/1");
		TestLibrary.testJsonFile("correctBlock/2");
		TestLibrary.testJsonFile("correctBlock/3");
		TestLibrary.testJsonFile("correctBlock/4");
		TestLibrary.testJsonFile("correctBlock/5");
	}

	@Test
	public void incorrectBlock() {
		TestLibrary.testJsonFile("incorrectBlock/1");
		TestLibrary.testJsonFile("incorrectBlock/1");
		TestLibrary.testJsonFile("incorrectBlock/1");
	}

//	@Test
//	public void correctArray() {
//		TestLibrary.testJsonFile("correctArray/1");
//	}

//	@Test
//	public void incorrectArray() {
//
//	}



//
//	@Test
//	public void checkStartBlockCorrectTest() {
//		String methodName = "checkStartBlock";
//		Method method;
//		JsonFormatChecker jsonFormatChecker = new JsonFormatChecker("{");
//
//		try {
//			method = TestLibrary.getMethod(JsonFormatChecker.class, methodName, String.class, int.class, String.class);
//			method.setAccessible(true);
//			method.invoke(jsonFormatChecker, "}");
//
//			Assert.assertEquals(STATUS_KEY_FIRST_QUOTER, jsonFormatChecker.);
//		} catch (Exception e) {
//			Assert.fail("Something problems: " + e.toString());
//		}
//	}
//
//	@Test
//	public void checkStartBlockIncorrectTest() {
//		String methodName = "checkStartBlock";
//		Method method;
//		JsonFormatChecker jsonFormatChecker = new JsonFormatChecker("}");
//
//		try {
//
//			method = TestLibrary.getMethod(JsonFormatChecker.class, methodName, String.class, int.class, String.class);
//			method.setAccessible(true);
//			method.invoke(jsonFormatChecker, "}");
//		} catch (InvocationTargetException invocationTargetException) {
//			//тестирование успешно
//		} catch (Exception e) {
//			// todo
//			Assert.fail("Something problems: " + e.toString());
//		}
//	}
}
