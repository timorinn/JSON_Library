import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestLibrary {
	private static String pathToTestFiles = "src/test/resources/";

	public static String getStringFromFileFromResources(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(pathToTestFiles + fileName)));
		} catch (Exception e) {
			return null;
		}
	}

	public static Method getMethod(Class<?> c, String methodName, Class<?>... params) throws NoSuchMethodException {
		return c.getDeclaredMethod(methodName, params);
	}
}
