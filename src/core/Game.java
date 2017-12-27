package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.InputMap;

import core.keyboard.Key;
import core.keyboard.KeyboardManager;
import screen.GameScreen;
import screen.GameScreenFactory;
import screen.ScreenType;
import util.CallbackListener;

public class Game implements CallbackListener<Key> {

  private KeyboardManager keyboardManager;
  private Map<String, String> globalGameConfig;
  private GameScreen activeScreen;
  
  Game(KeyboardManager keyboardManager) {
    keyboardManager.addListener(this);
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
    ScreenType nextScreenType = activeScreen.tick();
    if (nextScreenType != activeScreen.getType()) {
      // Transition to a new screen, deletes all entities in the active screen
      activeScreen = GameScreenFactory.getScreen(nextScreenType, globalGameConfig);
    }
    
  }

  @Override
  public void callback(Key msgFromKeyboard) {
    activeScreen.handleKeyStroke(msgFromKeyboard);
  }

}
