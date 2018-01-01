package core.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

public class SpriteManager {
  private class FilterMarker extends RGBImageFilter {
    private final int markerRGB;
    
    FilterMarker(Color marker) {
      super();
      markerRGB = marker.getRGB() & 0x00ffffff;
    }
    
    @Override
    public int filterRGB(int x, int y, int rgb) {
      int transparentRGB = (rgb & 0x00ffffff);
      if (transparentRGB == markerRGB) {
        return transparentRGB;
      } else {
        return rgb;
      }
    }
  }
  
  private static final SpriteManager manager = new SpriteManager();
  
  private Image spriteSheet; 
  
  SpriteManager() {
    try {
      spriteSheet = filterOutMarker(ImageIO.read(new File("resources/spritesheet.gif")), new Color(255, 0, 255));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private Image filterOutMarker(Image im, Color marker) {
    ImageFilter filter = new FilterMarker(marker);
    
    ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
    return Toolkit.getDefaultToolkit().createImage(ip);
  }
  
  public void drawSheet(Graphics g) {
    g.drawImage(spriteSheet, 0, 0, 32, 32, 0, 0, 32, 32, null);
  }
  
  public static SpriteManager getManager() {
    return manager;
  }
}
