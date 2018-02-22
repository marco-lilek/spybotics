package config;

import java.awt.Color;
import java.util.List;

public class UnitConfig {
  public class AttackConfig {
    public String name;
    public int damage;
    public int range;
  }
  public String name;
  public String color_rgb_hex;
  public int max_tail_length;
  public int movement_speed;
  
  public List<AttackConfig> attacks; 
  
  public Color getRGB() {
    return new Color(Integer.parseInt(color_rgb_hex, 16));
  }
}
