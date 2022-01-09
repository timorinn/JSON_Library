import java.lang.reflect.Method;

public class TestLibrary {

	public static Method getMethod(Class<?> c, String methodName, Class<?>... params) throws NoSuchMethodException {
		return c.getDeclaredMethod(methodName, params);
	}
}
