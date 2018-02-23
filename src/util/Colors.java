package util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Colors {
  public static final Color WINDOW_TOP = new Color(200,200,200);
  public static final Color WINDOW = new Color(210, 210, 210);
  public static final Color WINDOW_HIGH = new Color(225, 225, 225);
  public static final Color LIGHT_RED = new Color(255, 0, 0, 50);
  public static final Color LIGHT_BLUE = new Color(0, 0, 200, 50);
  public static final List<Color> PLAYER_COLORS;
  static {
    PLAYER_COLORS = new ArrayList<Color>();
    PLAYER_COLORS.add(new Color(200,0,0));
    PLAYER_COLORS.add(new Color(0,200,0));
    PLAYER_COLORS.add(new Color(0,0,200));
    PLAYER_COLORS.add(new Color(255,127,0));
    PLAYER_COLORS.add(new Color(255,255,0));
    PLAYER_COLORS.add(new Color(75,0,130));
    PLAYER_COLORS.add(new Color(143,0,255));
  }
}
