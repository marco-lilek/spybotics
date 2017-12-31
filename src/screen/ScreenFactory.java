package screen;

import java.util.Map;
import java.util.LinkedHashMap;

public class ScreenFactory {
  
  public static Screen getScreen(ScreenType type, Map<String, String> globalGameConfig) {
    switch (type) {
    default:
      return new MatchScreen(type);
    }
  }
}
