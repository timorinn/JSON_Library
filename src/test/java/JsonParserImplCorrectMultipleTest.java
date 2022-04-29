import jsonlib.exceptions.JsonFormatException;
import jsonlib.JsonObjectImp;
import jsonlib.JsonParserImpl;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserImplCorrectMultipleTest {
	private final String path = "correct/multiple/";

	@Test
	public void correctMultiple_1() {
		String fileName = path + "1";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObjectImp jsonObjectImp;

		if (jsonText != null) {
			try {
				jsonObjectImp = JsonParserImpl.parse(jsonText);
				Assert.assertEquals("\"value\"", jsonObjectImp.getValue("key"));
				Assert.assertEquals("\"value\"", jsonObjectImp.getValue("key2"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}
}
