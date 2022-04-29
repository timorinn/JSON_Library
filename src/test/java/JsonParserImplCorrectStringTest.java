import jsonlib.exceptions.JsonFormatException;
import jsonlib.JsonObjectImp;
import jsonlib.JsonParserImpl;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserImplCorrectStringTest {
    @Test
    public void correctString_1() {
        String fileName = "correct/string/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("\"value\"", jsonObjectImp.getValue("key"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("\"  v a _ lue \"", jsonObjectImp.getValue("key"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("\"  v a _ l\n   u  e \"", jsonObjectImp.getValue("key"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
