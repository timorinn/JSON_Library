import LibraryJSON.JsonFormatException;
import LibraryJSON.JsonObject;
import LibraryJSON.JsonParser;
import org.junit.Assert;
import org.junit.Test;


public class JsonParserTest {

//	@Test
//	public void correctEmpty() {
//		Assert.assertEquals(0, new JsonParser("{}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("{}	 	").checkFormat());
//		Assert.assertEquals(0, new JsonParser("{		}	 	").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{}	 	").checkFormat());
//		Assert.assertEquals(0, new JsonParser("{	 	}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{	 	}").checkFormat());
//		Assert.assertEquals(0, new JsonParser("	 	{	 	}	 	").checkFormat());
//	}
//
//
//	@Test
//	public void incorrectEmpty() {
//		Assert.assertEquals(-1, JsonParser("").checkFormat());
//		Assert.assertEquals(-1, JsonParser("  	{  ").checkFormat());
//		Assert.assertEquals(-1, JsonParser("  	{").checkFormat());
//		Assert.assertEquals(-1, JsonParser("}").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("{_}").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("   } ").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("{     }}").checkFormat());
//		Assert.assertEquals(-1, new JsonParser("{{    }").checkFormat());
//	}
//

}