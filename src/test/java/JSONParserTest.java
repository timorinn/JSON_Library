import LibraryJSON.JSONFormatException;
import LibraryJSON.JSONParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.ECField;
import java.util.Arrays;


public class JSONParserTest {
	private String pathToTestFiles = "src/test/resources/";

	public String getStringFromFileFromResources(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(pathToTestFiles + fileName)));
		} catch (Exception e) {
			System.out.println("File " + pathToTestFiles + fileName + " not found.");
			return null;
		}
	}

	@Test
	public void emptyJsonTest() {
		System.out.println("emptyJsonTest " + getStringFromFileFromResources("emptyJson"));
	}


}
