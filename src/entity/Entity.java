package entity;

import java.awt.Graphics;
import java.util.Set;

import com.google.gson.Gson;

import util.communicator.CallbackListener;
import util.communicator.CallbackNotifier;
import util.communicator.Communicator;

public abstract class Entity<T extends EntityPainter> extends Communicator implements Comparable {

  private final T painter;
  
  public Entity(T painter) {
    this.painter = painter;
  }
  
  public void redraw(Graphics g) {
    painter.redraw(g);
  }
  
  public abstract void tick();
  
  @Override
  public int compareTo(Object arg0) {
    return Integer.compare(this.hashCode(), arg0.hashCode());
  }
  
  protected T getPainter() {
    return painter;
  }
}
