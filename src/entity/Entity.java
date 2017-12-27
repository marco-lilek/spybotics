package entity;

import java.awt.Graphics;
import java.util.Set;

public abstract class Entity implements Comparable {

  public abstract void redraw(Graphics g);
  public abstract void tick();
  
  @Override
  public int compareTo(Object arg0) {
    return Integer.compare(this.hashCode(), arg0.hashCode()); 
  }
}
