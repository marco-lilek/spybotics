package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.TreeSet;

import core.keyboard.Key;
import entity.Entity;

public abstract class GameScreen {
  
  private ScreenType type;
  
  GameScreen(ScreenType type) {
    this.type = type;
  }
  
  public abstract ScreenType tick();
  public abstract void redraw(Graphics g);
  
  public ScreenType getType() {
    return type;
  }

  public abstract void handleKeyStroke(Key msgFromKeyboard);
}
