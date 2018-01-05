package screen;

import java.util.Map;

import core.Game;
import util.communicator.Message;

import java.util.LinkedHashMap;

public class ScreenFactory {
  
  public static Screen getScreen(Message msg, Game game) {
    switch (msg) {
    case GAME_SCREEN_TEST:
      return new TestScreen(game);
    case GAME_SCREEN_MATCH:
      return new MatchScreen(game);
    default:
      return null;
    }
  }
}
