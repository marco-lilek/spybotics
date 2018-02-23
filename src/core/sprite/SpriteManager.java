package core.sprite;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

public class SpriteManager {
  private class FilterBGColor extends RGBImageFilter {
    private final int bgColorRGB;
    
    FilterBGColor(Color bgColor) {
      bgColorRGB = bgColor.getRGB() & 0x00ffffff;
    }
    
    @Override
    public int filterRGB(int x, int y, int rgb) {
      int transparentRGB = (rgb & 0x00ffffff);
      if (transparentRGB == bgColorRGB) {
        return 0x0;
      } else {
        return rgb;
      }
    }
  }
  
  private static final SpriteManager manager = new SpriteManager();
  private Image spriteSheet;
  
  private static final int SPRITEW = 32;
  
  SpriteManager() {
    try {
      spriteSheet = filterOutbgColor(ImageIO.read(new File("resources/spritesheet.gif")), new Color(255, 0, 255));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private Image filterOutbgColor(Image im, Color bgColor) {
    ImageFilter filter = new FilterBGColor(bgColor);
    
    ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
    return Toolkit.getDefaultToolkit().createImage(ip);
  }
  
  public void drawSprite(Graphics2D g, int idx, int x, int y) {
    g.drawImage(spriteSheet, x + 5, y, x+SPRITEW, y+SPRITEW, 0, 0, SPRITEW, SPRITEW, null);
  }
  
  public static SpriteManager getManager() {
    return manager;
  }
}
