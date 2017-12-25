package screen;

import java.awt.Graphics;
import java.util.Set;
import java.util.TreeSet;

import entity.Entity;

public abstract class GameScreen {
  
  private ScreenType type;
  
  GameScreen(ScreenType type) {
    this.type = type;
  }
  
  public abstract ScreenType tick(Set<String> activeKeys);
  public abstract void redraw(Graphics g);
  
  public ScreenType getType() {
    return type;
  }
  
}
