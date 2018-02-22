package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import entity.painter.EntityPainter;
import screen.Screen;
import util.communicator.CallbackListener;
import util.communicator.CallbackNotifier;
import util.communicator.Communicator;

public abstract class Entity implements Comparable {
  
  private final Screen screen;
  
  protected Entity(Screen screen) {
    this.screen = screen;
    screen.addEntity(this);
  }
  
  public abstract void redraw(List<Graphics2D> g);
  
  @Override
  public int compareTo(Object arg0) {
    return Integer.compare(this.hashCode(), arg0.hashCode());
  }
  
  public Screen getScreen() { return screen; }
}
