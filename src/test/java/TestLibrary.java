import LibraryJSON.JsonFormatChecker;
import org.junit.Assert;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestLibrary {
	private static String pathToTestFiles = "src/test/resources/";


	public static void testJsonFile(String fileName) {
		String jsonText = getStringFromFileFromResources(fileName);
		int expectedResult = (fileName.startsWith("correct") ? 0 : -1);

		if (jsonText != null) {
			Assert.assertEquals(expectedResult, new JsonFormatChecker(jsonText).checkFormat());
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}


	public static String getStringFromFileFromResources(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(pathToTestFiles + fileName)));
		} catch (Exception e) {
			return null;
		}
	}

}
