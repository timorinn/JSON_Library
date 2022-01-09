import LibraryJSON.JsonFormatChecker;
import LibraryJSON.JsonFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JsonFormatCheckerTest {

	final private static String STATUS_START_BLOCK = "STATUS_START_BLOCK";
	final private static String STATUS_KEY_FIRST_QUOTER = "STATUS_KEY_FIRST_QUOTER";
	final private static String STATUS_KEY_SECOND_QUOTER = "STATUS_KEY_SECOND_QUOTER";
	final private static String STATUS_COLON = "STATUS_COLON";
	final private static String STATUS_VALUE = "STATUS_VALUE";
	final private static String STATUS_VALUE_SIMPLE_ARRAY = "STATUS_VALUE_SIMPLE_ARRAY";
	final private static String STATUS_VALUE_BLOCK_ARRAY = "STATUS_VALUE_BLOCK_ARRAY";
	final private static String STATUS_VALUE_END_COMMA = "STATUS_VALUE_END_COMMA";

	@Test
	public void checkCorrectJsonTest() {
		String json = "{}";
		try {
			Assert.assertEquals(new JsonFormatChecker(json).checkFormat(), 0);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
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
