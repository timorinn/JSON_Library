import jsonlib.exceptions.JsonFormatException;
import jsonlib.JsonObjectImp;
import jsonlib.JsonParserImpl;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserImplCorrectBlockTest {
    @Test
    public void correctBlock_1() {
        String fileName = "correct/block/1";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key/key"));
                Assert.assertEquals("\"jojo\"", jsonObjectImp.getValue("key/key2"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key/key"));
                Assert.assertEquals("\"jojo\"", jsonObjectImp.getValue("key/key2"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key/key"));
                Assert.assertEquals("\"jo}{jo\"", jsonObjectImp.getValue("key/key2"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {

                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key/key"));
                Assert.assertEquals("\"}o\\\"}  j }\"", jsonObjectImp.getValue("key/key2"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);

                //TODO: 26.01.2022
                Assert.assertEquals("true", jsonObjectImp.getValue("key/key"));
                Assert.assertEquals("\"}o\\\"}j}\"", jsonObjectImp.getValue("key/key2"));
                Assert.assertEquals("\"}s\\\"tr\"", jsonObjectImp.getValue("k/k2/s"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
