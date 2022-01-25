import LibraryJSON.JsonFormatException;
import LibraryJSON.JsonObject;
import LibraryJSON.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserCorrectArrayTest {

// TODO: 23.01.2022
//	@Test
//	public void correctArray_1() {
//		String fileName = "correct/array/1";
//		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
//		JsonObject jsonObject;
//
//		if (jsonText != null) {
//			try {
//				jsonObject = JsonParser.getJsonObject(jsonText);
//				//Assert.assertEquals("null", jsonObject.getValue("2"));
//			} catch (JsonFormatException e) {
//				Assert.fail(e.getMessage());
//			}
//		} else {
//			Assert.fail("File '" + fileName + "' does not exists.");
//		}
//	}

    @Test
    public void correctArray_2() {
        String fileName = "correct/array/2";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {

                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key/[0]"));
                Assert.assertEquals("false", jsonObject.getValue("key/[1]"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctArray_3() {
        String fileName = "correct/array/3";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key/[0]"));
                Assert.assertEquals("false", jsonObject.getValue("key/[1]"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctArray_4() {
        String fileName = "correct/array/4";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key/[0]"));
                Assert.assertEquals("false", jsonObject.getValue("key/[1]"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
