import LibraryJSON.JsonObject;
import LibraryJSON.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;


public class JsonParserTest {


//	@Test
//	public void nullJsonTest() {
//		Assert.assertThrows(NullPointerException.class, ()-> {
//			JsonParser jsonParser = new JsonParser(null);
//		});
//	}


//	@Test
//	public void minimalJsonTest() {
//		JsonParser jsonParser = null;
//
//		try {
//			jsonParser = new JsonParser(getStringFromFileFromResources("emptyJson"));
//		} catch (Exception e) {
//			Assert.fail("Error with creating new jsonParser with minimal json-file.\n");
//		}
//		Assert.assertTrue(jsonParser != null);
//	}


	//todo нарущен принцип единственной ответственности
//	@Test
//	public void simpleJsonTest() {
//		JsonObject jsonObject = null;
//
//		try {
//			jsonObject = new JsonParser(getStringFromFileFromResources("oneStringJson")).parseBody();
//		} catch (Exception e) {
//			Assert.fail("Error with creating new jsonParser with simple json-file.\n");
//		}
//		Assert.assertEquals("value1", jsonObject.getValue("key1"));
//	}
}
