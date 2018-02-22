package screen;

import java.util.Map;

import core.Game;

import java.util.LinkedHashMap;

public class ScreenFactory {
  
  public static Screen getScreen() {
    return new MatchScreen();
  }
}
