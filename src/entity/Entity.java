package entity;

import java.awt.Graphics;
import java.util.Set;

import com.google.gson.Gson;

public abstract class Entity implements Comparable {

  public abstract void redraw(Graphics g);
  public abstract void tick();
  
  @Override
  public int compareTo(Object arg0) {
    return Integer.compare(this.hashCode(), arg0.hashCode());
  }
}
