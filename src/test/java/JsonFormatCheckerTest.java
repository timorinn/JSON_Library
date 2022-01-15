import LibraryJSON.JsonFormatChecker;
import org.junit.Assert;
import org.junit.Test;


public class JsonFormatCheckerTest {

	@Test
	public void correctEmptyJsonTest() {
		Assert.assertEquals(0, new JsonFormatChecker("{}").checkFormat());
		Assert.assertEquals(0, new JsonFormatChecker("	 	{}").checkFormat());
		Assert.assertEquals(0, new JsonFormatChecker("{}	 	").checkFormat());
		Assert.assertEquals(0, new JsonFormatChecker("	 	{}	 	").checkFormat());
		Assert.assertEquals(0, new JsonFormatChecker("{	 	}").checkFormat());
		Assert.assertEquals(0, new JsonFormatChecker("	 	{	 	}").checkFormat());
		Assert.assertEquals(0, new JsonFormatChecker("{		}	 	").checkFormat());
		Assert.assertEquals(0, new JsonFormatChecker("	 	{	 	}	 	").checkFormat());
	}

	@Test
	public void incorrectEmptyJsonTest() {
		Assert.assertEquals(-1, new JsonFormatChecker("").checkFormat());
		Assert.assertEquals(-1, new JsonFormatChecker("  	{  ").checkFormat());
		Assert.assertEquals(-1, new JsonFormatChecker("{_}").checkFormat());
		Assert.assertEquals(-1, new JsonFormatChecker("   } ").checkFormat());
		Assert.assertEquals(-1, new JsonFormatChecker("{     }}").checkFormat());
		Assert.assertEquals(-1, new JsonFormatChecker("{{    }").checkFormat());
	}

	@Test
	public void correctSingleStringJsonTest() {
		TestLibrary.testJsonFile("correctSingleStringJson");
		TestLibrary.testJsonFile("correctSingleStringJson2");
		TestLibrary.testJsonFile("correctSingleStringJson3");
	}

	@Test
	public void incorrectSingleStringJsonTest() {
		TestLibrary.testJsonFile("incorrectSingleStringJson");
		TestLibrary.testJsonFile("incorrectSingleStringJson2");
	}


	@Test
	public void correctMultipleStingsJsonTest() {
		TestLibrary.testJsonFile("correctMultipleStringsJson");
		TestLibrary.testJsonFile("correctMultipleStringsJson2");
		TestLibrary.testJsonFile("correctMultipleStringsJson3");
	}

	@Test
	public void incorrectMultipleStingsJsonTest() {
		TestLibrary.testJsonFile("incorrectMultipleStringsJson");
		TestLibrary.testJsonFile("incorrectMultipleStringsJson2");
	}

	@Test
	public void correctSpecialJsonTest() {
		TestLibrary.testJsonFile("correctSpecialJson");
		TestLibrary.testJsonFile("correctSpecialJson2");
		TestLibrary.testJsonFile("correctSpecialJson3");
	}

	@Test
	public void incorrectSpecialJsonTest() {
		TestLibrary.testJsonFile("incorrectSpecialJson");
		TestLibrary.testJsonFile("incorrectSpecialJson2");
		TestLibrary.testJsonFile("incorrectSpecialJson3");
	}

	@Test
	public void correctBlockJsonTest() {
		TestLibrary.testJsonFile("correctBlockJson");
		TestLibrary.testJsonFile("correctBlockJson2");
	}

	@Test
	public void incorrectBlockJsonTest() {
		TestLibrary.testJsonFile("incorrectBlockJson");
		TestLibrary.testJsonFile("incorrectBlockJson");
		TestLibrary.testJsonFile("incorrectBlockJson");
	}


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
