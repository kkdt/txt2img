package txt2img;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import txt2img.awt.TextToGraphics2D;

/**
 * <p>
 * Simple UI to allow text input and then display the converted image.
 * </p>
 * 
 * @author thinh
 *
 */
public class Text2ImageFrame extends JFrame 
   implements ActionListener
{
   private static final long serialVersionUID = 195825163958609193L;
   
   private JTextField input;
   private JTextField fontInput;
   private JTextField sizeInput;
   private JLabel message;
   private JButton generateBtn;
   
   public Text2ImageFrame() {
      super("Text2Image");
      
      setSize(620, 190);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      getContentPane().add(buildComponents());
      generateBtn.addActionListener(this);
      
      pack();
      setVisible(true);
   }
   
   private JPanel buildComponents() {
      input = new JTextField(20);
      fontInput = new JTextField(5);
      sizeInput = new JTextField(5);
      message = new JLabel("Enter text and hit 'Generate' button");
      generateBtn = new JButton("Generate");
      
      JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      panel.add(new JLabel("Text:"));
      panel.add(input);
      panel.add(new JLabel("Font:"));
      panel.add(fontInput);
      panel.add(new JLabel("Size:"));
      panel.add(sizeInput);
      panel.add(generateBtn);
      
      JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      messagePanel.add(message);
      
      JPanel contents = new JPanel(new BorderLayout(5,5));
      contents.add(panel, BorderLayout.NORTH);
      contents.add(messagePanel, BorderLayout.SOUTH);
      return contents;
   }
   
   private void info(String msg) {
      EventQueue.invokeLater(() -> {
         message.setText(msg);
      });
   }
   
   private void displayImage(final BufferedImage img) {
      JFrame frame = new JFrame(input.getText());
      frame.setLocationRelativeTo(this);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setContentPane(new JPanel() {
         private static final long serialVersionUID = 3018556188601113851L;
         @Override
         protected void paintComponent(Graphics g) {
             super.paintComponent(g);
             g.drawImage(img, 10, 10, this);
         }
      });
      frame.setMinimumSize(new Dimension(400, 100));
      frame.pack();
      frame.setVisible(true);
   }
   
   @Override
   public void actionPerformed(ActionEvent event) {
      String text = input.getText().trim();
      
      if(text.length() > 0) {
         TextToGraphics2D converter = new TextToGraphics2D();
         try{
            converter.setFont(fontInput.getText().trim().length() > 0 ? fontInput.getText().trim() : "Arial");
            converter.setFontSize(Integer.parseInt(sizeInput.getText().trim()));
         } catch (Exception e) {
            info("Invalid inputs: " + e.getMessage());
            return;
         }
         
         Date start = new Date();
         byte[] data = converter.toImage(text);
         Date end = new Date();
         
         if(data != null && data.length > 0) {
            InputStream in = new ByteArrayInputStream(data);
            BufferedImage img = null;
            try {
               img = ImageIO.read(in);
               displayImage(img);
               info("Total time: " + (end.getTime() - start.getTime()) + "ms. Length: " + data.length + " bytes");
            } catch (IOException e) {
               info(e.getMessage());
            }
         } else {
            info("No data returned");
         }
      } else {
         info("Please input text");
      }
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
             new Text2ImageFrame();
         }
     });
   }

}
