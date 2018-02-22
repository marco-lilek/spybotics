package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.InputMap;

import core.sprite.SpriteManager;
import entity.Board;
import screen.Screen;
import screen.ScreenFactory;
import util.communicator.CallbackListener;
import util.communicator.Communicator;
import util.communicator.KeyboardMessage;

public class Game implements CallbackListener<KeyboardMessage> {

  private Map<String, Integer> globalGameConfig;
  private Screen activeScreen;
  
  Game(KeyboardManager keyboardManager) {
    keyboardManager.addListener("Game", this);
    globalGameConfig = new HashMap<String, Integer>();
    activeScreen = ScreenFactory.getScreen();
  }

  public void redraw(List<Graphics2D> gl) {
    activeScreen.redraw(gl);
  }

  @Override
  public void callbackRecv(KeyboardMessage msg) {
    activeScreen.handleInput(msg);
  }

  public Map<String, Integer> getGlobalConfig() {
    return globalGameConfig;
  }
}
