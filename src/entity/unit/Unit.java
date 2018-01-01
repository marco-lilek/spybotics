package entity.unit;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import entity.Board;
import entity.Entity;
import util.Direction;
import util.IPoint;

public class Unit extends Entity {

  private Board board;
  private int x,y, maxTailLength;
  private Map<IPoint,Boolean> tail;
  
  public Unit(int x, int y, Board board) {
    this.x = x;
    this.y = y;
    maxTailLength = 5;
    tail = new LinkedHashMap<IPoint,Boolean>();
    
    this.board = board;
    this.board.addUnitAt(this.x, this.y, this);
  }

  @Override
  public void redraw(Graphics g) {
    int idx = 0;
    for (Iterator<IPoint> it = tail.keySet().iterator(); it.hasNext(); idx++) {
      IPoint p = it.next();
      int px = p.gx();
      int py = p.gy();
      board.drawTailTile(g, px, py, idx + 1);
    }
    board.drawHeadTile(g, x, y);
  }

  @Override
  public void tick() {
    // TODO: move out control logic to the screen w/ forwarding
    
  }

  private Direction getDirectionBetween(int fromx, int fromy, int tox, int toy) {
    Direction dtoPrev = Direction.NONE;
    if (tox == fromx - 1) dtoPrev = Direction.WEST;
    if (tox == fromx + 1) dtoPrev = Direction.EAST;
    if (toy == fromy - 1) dtoPrev = Direction.NORTH;
    if (toy == fromy + 1) dtoPrev = Direction.SOUTH;
    return dtoPrev;
  }
  
  public void move(int xd, int yd) {
    System.out.println(tail);
    int xn = x + xd;
    int yn = y + yd;
    
    tail.put(new IPoint(x,y), true);
    if (tail.size() > maxTailLength) {
      IPoint top = tail.keySet().iterator().next();
      tail.remove(top);
    }
    tail.remove(new IPoint(xn, yn));
    
    x = xn;
    y = yn;
    
  }

}
