package screen;

import java.util.Map;

public class GameScreenFactory {
  
  public static GameScreen getScreen(ScreenType type, Map<String, String> globalGameConfig) {
    switch (type) {
    default:
      return new TestScreen(type, globalGameConfig);
    }
  }
}
