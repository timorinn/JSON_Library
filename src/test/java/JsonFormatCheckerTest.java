import LibraryJSON.JsonFormatChecker;
import org.junit.Assert;
import org.junit.Test;


public class JsonFormatCheckerTest {

	@Test
	public void emptyCorrectJsonTest() {
		Assert.assertEquals(new JsonFormatChecker("{}").checkFormat(), 0);
		Assert.assertEquals(new JsonFormatChecker("	 	{}").checkFormat(), 0);
		Assert.assertEquals(new JsonFormatChecker("{}	 	").checkFormat(), 0);
		Assert.assertEquals(new JsonFormatChecker("	 	{}	 	").checkFormat(), 0);

		Assert.assertEquals(new JsonFormatChecker("{	 	}").checkFormat(), 0);
		Assert.assertEquals(new JsonFormatChecker("	 	{	 	}").checkFormat(), 0);
		Assert.assertEquals(new JsonFormatChecker("{		}	 	").checkFormat(), 0);
		Assert.assertEquals(new JsonFormatChecker("	 	{	 	}	 	").checkFormat(), 0);
	}

	@Test
	public void stringCorrectJsonTest() {
		Assert.assertEquals(new JsonFormatChecker("{\"key\":\"value\"}").checkFormat(), 0);
		Assert.assertEquals(new JsonFormatChecker("{\"  k e y  \":\"  v1 2_ al u e \" }").checkFormat(), 0);

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
