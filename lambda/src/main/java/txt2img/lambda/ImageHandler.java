package txt2img.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

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
public class ImageHandler {
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
    * Extract the text from request.
    * </p>
    * 
    * @param input
    * @return
    */
   private String getText(Map<String, String> input) {
      return input.get("text");
   }
   
   /**
    * <p>
    * Extract the font from request.
    * </p>
    * 
    * @param input
    * @return
    */
   private String getFont(Map<String, String> input) {
      String font = input.get("font");
      return (font != null && font.length() > 0 ? font : "Arial");
   }
   
   /**
    * <p>
    * Extract the font size from request, throwing an exception if invalid number.
    * </p>
    * 
    * @param input
    * @return
    */
   private int getFontSize(Map<String, String> input) {
      String font = input.get("size");
      int size = 20;
      if(font != null && font.length() > 0) {
         size = Integer.parseInt(font);
      }
      return size;
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
   public byte[] toImage(Map<String, String> input, Context context) {
      byte[] result = new byte[0];
      String text = getText(input);
      if(input == null || input.size() == 0 
         || text == null || text.length() > getTextLimit())
      {
         return result;
      }
      
      TextToGraphics2D converter = new TextToGraphics2D();
      converter.setFont(getFont(input));
      try {
         converter.setFontSize(getFontSize(input));
         result = converter.toImage(text);
      } catch (Exception e) {}
      
      return result;
   }
}
