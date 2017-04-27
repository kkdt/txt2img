package txt2img.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedString;

import javax.imageio.ImageIO;

import txt2img.TextToImage;

/**
 * <p>
 * AWT implementation for converting a text to an image.
 * </p>
 * 
 * @author thinh
 *
 */
public class TextToGraphics2D implements TextToImage {
   protected static final String defaultFont = "Serif";
   
   private String font = defaultFont;
   private int fontSize = 20;
   
   public TextToGraphics2D() {}
   
   public TextToGraphics2D(String font, int fontSize) {
      this.font = font;
      this.fontSize = fontSize;
   }
   
   /**
    * <p>
    * The font name (i.e. Arial), default to "Serif" if not set.
    * </p>
    * 
    * @return
    */
   public String getFont() {
      return font;
   }

   /**
    * <p>
    * The font name (i.e. Arial), default to "Serif" if not set.
    * </p>
    * 
    * @param font
    */
   public void setFont(String font) {
      this.font = font;
   }

   /**
    * <p>
    * The font size, default to 20 if not set.
    * </p>
    * 
    * @return
    */
   public int getFontSize() {
      return fontSize;
   }

   /**
    * <p>
    * The font size, default to 20 if not set.
    * </p>
    * 
    * @param fontSize
    */
   public void setFontSize(int fontSize) {
      this.fontSize = fontSize;
   }
   
   /**
    * <p>
    * Helper method to create the text wrapper.
    * </p>
    * 
    * @param text the text string.
    * @param font the font name (i.e. Arial).
    * @param size the font size.
    * @return
    */
   protected AttributedString createText(String text, Font font, int size) {
      AttributedString str = new AttributedString(text);
      str.addAttribute(TextAttribute.FONT, font);
      str.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, text.length());
      str.addAttribute(TextAttribute.BACKGROUND, Color.LIGHT_GRAY, 0, text.length());
      return str;
   }

   /**
    * <p>
    * Helper method to create the {@code BufferedImage} using font and font size.
    * </p>
    * 
    * @param text input text.
    * @param fontName font name (i.e. Arial).
    * @param size font size.
    * @return
    */
   protected BufferedImage createImage(String text, String fontName, int size) {
      // need to find the actual image width and height first
      BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = img.createGraphics();
      Font font = new Font(fontName, Font.PLAIN, size);
      g2d.setFont(font);
      FontMetrics fm = g2d.getFontMetrics();
      int imageWidth = fm.stringWidth(text);
      int imageHeight = fm.getHeight();
      g2d.dispose();
      
      // render with known width and height
      img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
      g2d = img.createGraphics();
      g2d.setFont(font);
      AttributedString str = createText(text, font, size);
      g2d.drawString(str.getIterator(), 0, fm.getAscent());
      g2d.dispose();
      
      return img;
   }

   @Override
   public byte[] toImage(String text) {
      BufferedImage img = createImage(text, font, fontSize);
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      try {
         ImageIO.write(img, "png", os);
      } catch (IOException e) {
         try {
            os.close();
         } catch (IOException ioe) {}
         os = null;
      }
      
      byte[] data = new byte[0];
      if(os != null) {
         data = os.toByteArray();
      }
      return data;
   }
   
}
