package core.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

public class SpriteManager {
  private static final SpriteManager manager = new SpriteManager();
  
  private Image spriteSheet; 
  
  SpriteManager() {
    try {
      spriteSheet = ImageIO.read(new File("resources/test.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void drawSheet(Graphics g) {
    g.drawImage(spriteSheet, 0, 0, 32, 32, 0, 0, 32, 32, null);
  }
  
  public static SpriteManager getManager() {
    return manager;
  }
}
