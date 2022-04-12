import jsonlib.JsonFormatException;
import jsonlib.JsonObject;
import jsonlib.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserCorrectMultipleTest {
	private final String path = "correct/multiple/";

	@Test
	public void correctMultiple_1() {
		String fileName = path + "1";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObject jsonObject;

		if (jsonText != null) {
			try {
				jsonObject = JsonParser.getJsonObject(jsonText);
				Assert.assertEquals("\"value\"", jsonObject.getValue("key"));
				Assert.assertEquals("\"value\"", jsonObject.getValue("key2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}
}
