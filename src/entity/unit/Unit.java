package entity.unit;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import entity.Board;
import entity.Entity;

public class Unit extends Entity {

  private Board board;
  private int x,y, maxTailLength;
  private Map<Point,Boolean> tail;
  
  public Unit(int x, int y, Board board) {
    this.x = x;
    this.y = y;
    maxTailLength = 5;
    tail = new LinkedHashMap<Point,Boolean>();
    
    this.board = board;
    this.board.addUnitAt(this.x, this.y, this);
  }

  @Override
  public void redraw(Graphics g) {
    for (Iterator<Point> it = tail.keySet().iterator(); it.hasNext(); ) {
      Point p = it.next();
      board.drawUnitTile(g, (int)p.getX(), (int)p.getY(), true);
    }
    board.drawUnitTile(g, x, y, false);
  }

  @Override
  public void tick() {
    // TODO: move out control logic to the screen w/ forwarding
    
  }

  public void move(int xd, int yd) {
    tail.put(new Point(x,y), true);
    if (tail.size() > maxTailLength) {
      Point top = tail.keySet().iterator().next();
      tail.remove(top);
    }
    x += xd;
    y += yd;
    tail.remove(new Point(x, y));
  }

}
