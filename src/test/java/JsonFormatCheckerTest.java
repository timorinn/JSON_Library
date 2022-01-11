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
		Assert.assertEquals(0
				, new JsonFormatChecker(TestLibrary.getStringFromFileFromResources("correctSingleStringJson"))
						.checkFormat());
		Assert.assertEquals(0
				, new JsonFormatChecker(TestLibrary.getStringFromFileFromResources("correctSingleStringJson2"))
						.checkFormat());
		Assert.assertEquals(0
				, new JsonFormatChecker(TestLibrary.getStringFromFileFromResources("correctSingleStringJson3"))
						.checkFormat());
	}

	@Test
	public void incorrectSingleStringTest() {
		Assert.assertEquals(-1
				, new JsonFormatChecker(TestLibrary.getStringFromFileFromResources("incorrectSingleStringJson"))
						.checkFormat());

	}

	@Test
	public void correctMultipleStingsJsonTest() {
		Assert.assertEquals(0
				, new JsonFormatChecker(
						TestLibrary.getStringFromFileFromResources("correctMultipleStringsJson"))
						.checkFormat());
		Assert.assertEquals(0
				, new JsonFormatChecker(
						TestLibrary.getStringFromFileFromResources("correctMultipleStringsJson2"))
						.checkFormat());
	}

	@Test
	public void incorrectMultipleStingsJsonTest() {
		Assert.assertEquals(-1
				, new JsonFormatChecker(
						TestLibrary.getStringFromFileFromResources("incorrectMultipleStringsJson"))
						.checkFormat());
		Assert.assertEquals(-1
				, new JsonFormatChecker(
						TestLibrary.getStringFromFileFromResources("incorrectMultipleStringsJson2"))
						.checkFormat());
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
