package entity.painter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import entity.Entity;

public abstract class EntityPainter {

  public abstract void redraw(List<Graphics2D> g);

}
