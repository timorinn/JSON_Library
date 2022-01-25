import LibraryJSON.JsonFormatException;
import LibraryJSON.JsonObject;
import LibraryJSON.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserCorrectStringTest {
    @Test
    public void correctString_1() {
        String fileName = "correct/string/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("\"value\"", jsonObject.getValue("key"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctString_2() {
        String fileName = "correct/string/2";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("\"  v a _ lue \"", jsonObject.getValue("key"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctString_3() {
        String fileName = "correct/string/3";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("\"  v a _ l\n   u  e \"", jsonObject.getValue("key"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
