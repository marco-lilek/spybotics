package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.TreeSet;

import core.keyboard.Key;
import entity.Entity;
import util.CallbackListener;

public abstract class Screen implements CallbackListener<Boolean> {
  
  private ScreenType type;
  
  Screen(ScreenType type) {
    this.type = type;
  }
  
  public abstract ScreenType tick();
  public abstract void redraw(Graphics g);
  
  public ScreenType getType() {
    return type;
  }

  public abstract void handleKeyStroke(Key msgFromKeyboard);
  
  @Override
  public void callback(Boolean msg) {
    // TODO Auto-generated method stub
    
  }
}
