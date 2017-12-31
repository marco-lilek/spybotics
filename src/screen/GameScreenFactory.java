package screen;

import java.util.Map;
import java.util.LinkedHashMap;

public class GameScreenFactory {
  
  public static GameScreen getScreen(ScreenType type, Map<String, String> globalGameConfig) {
    switch (type) {
    default:
      return new LevelScreen(type);
    }
  }
}