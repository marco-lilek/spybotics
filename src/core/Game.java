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
import util.communicator.Message;

public class Game extends Communicator {

  private Map<String, String> globalGameConfig;
  private Screen activeScreen;
  
  Game(KeyboardManager keyboardManager) {
    keyboardManager.addListener(getName(), this);
    globalGameConfig = new HashMap<String, String>();
    activeScreen = ScreenFactory.getScreen(Message.GAME_SCREEN_MATCH, this);
  }

  public void redraw(List<Graphics2D> gl) {
    activeScreen.redraw(gl);
  }
  
  public void tick() {
    activeScreen.tick();
/*    ScreenType nextScreenType = activeScreen.tick();
    if (nextScreenType != activeScreen.getType()) {
      // Transition to a new screen, deletes all entities in the active screen
      ;
    }*/
  }

  @Override
  public void callbackRecv(Message msg) {
    if (msg.is(Message.MsgTypes.KEYBOARD)) {
      notifyListener(activeScreen.getName(), msg);
    } else if (msg.is(Message.MsgTypes.GAME_SCREEN)) {
      activeScreen = ScreenFactory.getScreen(msg, this);
    }
  }
  
  @Override
  public String getName() {
    return "Game";
  }
}
