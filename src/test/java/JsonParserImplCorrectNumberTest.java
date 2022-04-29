import jsonlib.exceptions.JsonFormatException;
import jsonlib.JsonObjectImp;
import jsonlib.JsonParserImpl;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserImplCorrectNumberTest {
    @Test
    public void correctNumber_1() {
        String fileName = "correct/number/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("123", jsonObjectImp.getValue("k"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("-0.123", jsonObjectImp.getValue("k"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("123+e18", jsonObjectImp.getValue("k"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("0", jsonObjectImp.getValue("k"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("-0.0", jsonObjectImp.getValue("k"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
