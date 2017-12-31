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
import core.sprite.SpriteManager;
import entity.Board;
import screen.Screen;
import screen.ScreenFactory;
import screen.ScreenType;
import util.CallbackListener;

public class Game implements CallbackListener<Key> {

  private KeyboardManager keyboardManager;
  private Map<String, String> globalGameConfig;
  private Screen activeScreen;
  
  Game(KeyboardManager keyboardManager) {
    keyboardManager.addListener(this);
    globalGameConfig = new HashMap<String, String>();
    activeScreen = ScreenFactory.getScreen(ScreenType.TEST_SCREEN, globalGameConfig);
  }
  
  private static final int SCREEN_WIDTH = Board.getBoardScreenWidth();
  private static final int SCREEN_HEIGHT = Board.getBoardScreenHeight();

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
      activeScreen = ScreenFactory.getScreen(nextScreenType, globalGameConfig);
    }
  }

  @Override
  public void callback(Key msgFromKeyboard) {
    activeScreen.handleKeyStroke(msgFromKeyboard);
  }
}
