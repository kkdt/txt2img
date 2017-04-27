package txt2img;

/**
 * <p>
 * Base specification for converting a text to an image.
 * </p>
 * 
 * @author thinh
 *
 */
public interface TextToImage {
   /**
    * <p>
    * Convert the specified <code>text</code> into an image byte array.
    * </p>
    * 
    * @param text
    * @return
    */
   byte[] toImage(String text);
}
