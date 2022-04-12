import jsonlib.JsonFormatException;
import jsonlib.JsonObject;
import jsonlib.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserCorrectSpecialTest {
    @Test
    public void correctSpecial_1() {
        String fileName = "correct/special/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("true", jsonObject.getValue("key"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctSpecial_2() {
        String fileName = "correct/special/2";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("false", jsonObject.getValue("2"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }

    @Test
    public void correctSpecial_3() {
        String fileName = "correct/special/3";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObject jsonObject;

        if (jsonText != null) {
            try {
                jsonObject = JsonParser.getJsonObject(jsonText);
                Assert.assertEquals("null", jsonObject.getValue("2"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
