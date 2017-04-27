package txt2img.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

import txt2img.TextToImage;
import txt2img.awt.TextToGraphics2D;

/**
 * <p>
 * Lambda function for converting an input text to an image byte array. The input
 * text size limit is a configurable environment property,
 * </p>
 * 
 * @author thinh
 *
 */
public class ImageHandler implements TextToImage
{
   /**
    * <p>
    * The environment variable 'textLimit' holds the restriction on the input
    * length, default 50.
    * </p>
    * 
    * @return
    */
   private int getTextLimit() {
      Integer limit = 50;
      try {
         limit = Integer.parseInt(System.getenv("textLimit"));
      } catch (Exception e) {}
      return limit;
   }
   
   /**
    * <p>
    * Lambda handler method/name.
    * </p>
    * 
    * @param input
    * @param context
    * @return
    */
   public byte[] textToImage(Map<String, String> input, Context context) {
      String key = "text";
      if(input == null || input.size() == 0 || !input.containsKey(key) || input.get(key).length() > getTextLimit()) {
         return new byte[0];
      }
      return toImage(input.get(key));
   }

   @Override
   public byte[] toImage(String input) {
      byte[] data = new byte[0];
      if(input != null && input.length() > 0) {
         TextToGraphics2D converter = new TextToGraphics2D();
         converter.setFont("Arial");
         data = converter.toImage(input);
      }
      return data;
   }

}
