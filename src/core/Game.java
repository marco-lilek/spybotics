package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import screen.GameScreen;
import screen.GameScreenFactory;
import screen.ScreenType;

public class Game {

  private KeyboardManager keyboardManager;
  private Map<String, String> globalGameConfig;
  private GameScreen activeScreen;
  
  Game() {
    keyboardManager = new KeyboardManager();
    globalGameConfig = new HashMap<String, String>();
    activeScreen = GameScreenFactory.getScreen(ScreenType.TEST_SCREEN, globalGameConfig);
  }
  
  private static final int SCREEN_WIDTH = 500;
  private static final int SCREEN_HEIGHT = 500;

  public static Dimension getDimensions() {
    return new Dimension(getScreenWidth(), getScreenHeight());
  }

  public static int getScreenWidth() {
    return SCREEN_WIDTH;
  }

  public static int getScreenHeight() {
    return SCREEN_HEIGHT;
  }

  public void redraw(Graphics g) {
    g.clearRect(0, 0, getScreenWidth(), getScreenHeight());
    activeScreen.redraw(g);
  }
  
  public void tick() {
    ScreenType nextScreenType = activeScreen.tick(keyboardManager.getPressedKeys());
    if (nextScreenType != activeScreen.getType()) {
      // Transition to a new screen, deletes all entities in the active screen
      activeScreen = GameScreenFactory.getScreen(nextScreenType, globalGameConfig);
    }
    
  }
  
  public void handleKeyStroke(ActionEvent e) {
    keyboardManager.handleKeyStroke(e);
  }
  
  public Set<Map.Entry<String, String>> getKeyStrokeActionMap() {
    return keyboardManager.getKeyStrokeActionMap();
  }
}
