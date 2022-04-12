import jsonlib.JsonFormatException;
import jsonlib.JsonObject;
import jsonlib.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserCorrectNumberTest {
    @Test
    public void correctNumber_1() {
        String fileName = "correct/number/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("123", jsonObject.getValue("k"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctNumber_2() {
        String fileName = "correct/number/2";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("-0.123", jsonObject.getValue("k"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctNumber_3() {
        String fileName = "correct/number/3";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("123+e18", jsonObject.getValue("k"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctNumber_4() {
        String fileName = "correct/number/4";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("0", jsonObject.getValue("k"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctNumber_5() {
        String fileName = "correct/number/5";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("-0.0", jsonObject.getValue("k"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
