package config;

import java.awt.Color;

public class UnitConfig {
  public String name;
  public String color_rgb_hex;
  public int max_tail_length;
  
  public Color getRGB() {
    return new Color(Integer.parseInt(color_rgb_hex, 16));
  }

  public int getMaxTailLength() {
    return max_tail_length;
  }
  
}
