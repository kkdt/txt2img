package txt2img.unit;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import txt2img.TextToImage;
import txt2img.awt.TextToGraphics2D;

public class TextToGraphics2DTest {
   private static TextToImage driver;
   
   @BeforeClass
   public static void setup() {
      driver = new TextToGraphics2D();
   }
   
   @Test
   public void testToImage() {
      String text = "h3llo, @world!";
      byte[] img = driver.toImage(text);
      assertTrue(img != null && img.length > 0);
   }
}
