import LibraryJSON.JsonFormatException;
import LibraryJSON.JsonObject;
import LibraryJSON.JsonObjectPrinter;
import LibraryJSON.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserCorrectBlockTest {
    @Test
    public void correctBlock_1() {
        String fileName = "correct/block/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key/key"));
                Assert.assertEquals("\"jojo\"", jsonObject.getValue("key/key2"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctBlock_2() {
        String fileName = "correct/block/2";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key/key"));
                Assert.assertEquals("\"jojo\"", jsonObject.getValue("key/key2"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctBlock_3() {
        String fileName = "correct/block/3";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key/key"));
                Assert.assertEquals("\"jo}{jo\"", jsonObject.getValue("key/key2"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctBlock_4() {
        String fileName = "correct/block/4";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {

                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key/key"));
                Assert.assertEquals("\"}o\\\"}  j }\"", jsonObject.getValue("key/key2"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctBlock_5() {
        String fileName = "correct/block/5";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);

                //TODO: 26.01.2022
                Assert.assertEquals("true", jsonObject.getValue("key/key"));
                Assert.assertEquals("\"}o\\\"}j}\"", jsonObject.getValue("key/key2"));
                Assert.assertEquals("\"}s\\\"tr\"", jsonObject.getValue("k/k2/s"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
