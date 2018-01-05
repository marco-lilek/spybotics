package entity;

import java.awt.Graphics;

public abstract class EntityPainter<T> {

  public abstract void redraw(Graphics g);
 
  private T logicContainer;
  public void attach(T logicContainer) {
    this.logicContainer = logicContainer;
  }
  
  public T getLogicContainer() {
    return logicContainer;
  }
}
