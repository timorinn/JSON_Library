import jsonlib.exceptions.JsonFormatException;
import jsonlib.JsonObjectImp;
import jsonlib.JsonParserImpl;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserImplCorrectArrayTest {

// TODO: 23.01.2022
	@Test
	public void correctArray_1() {
		String fileName = "correct/array/1";
		String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
		JsonObjectImp jsonObjectImp;

		if (jsonText != null) {
			try {
				jsonObjectImp = JsonParserImpl.parse(jsonText);
				Assert.assertNotNull(jsonObjectImp.getValue("key"));
			} catch (JsonFormatException e) {
				Assert.fail(e.getMessage());
			}
		} else {
			Assert.fail("File '" + fileName + "' does not exists.");
		}
	}

    @Test
    public void correctArray_2() {
        String fileName = "correct/array/2";
        String jsonText = TestLibrary.getStringFromFileFromResources(fileName);
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key/[0]"));
                Assert.assertEquals("false", jsonObjectImp.getValue("key/[1]"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key/[0]"));
                Assert.assertEquals("false", jsonObjectImp.getValue("key/[1]"));
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
        JsonObjectImp jsonObjectImp;

        if (jsonText != null) {
            try {
                jsonObjectImp = JsonParserImpl.parse(jsonText);
                Assert.assertEquals("true", jsonObjectImp.getValue("key/[0]"));
                Assert.assertEquals("false", jsonObjectImp.getValue("key/[1]"));
            } catch (JsonFormatException e) {
                Assert.fail(e.getMessage());
            }
        } else {
            Assert.fail("File '" + fileName + "' does not exists.");
        }
    }
}
