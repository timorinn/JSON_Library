import jsonlib.exceptions.JsonFormatException;
import jsonlib.JsonObjectImp;
import jsonlib.JsonParserImpl;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserImplCorrectSpecialTest {
    @Test
    public void correctSpecial_1() {
        String fileName = "correct/special/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("false", jsonObjectImp.getValue("2"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("null", jsonObjectImp.getValue("2"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
