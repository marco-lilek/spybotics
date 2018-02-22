package entity.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import config.UnitConfig;
import entity.Board;
import entity.Entity;
import entity.player.Player;
import entity.unit.Unit;
import entity.unit.Unit.State;
import util.Canvas;
import util.IPoint;

public class UnitPainter extends EntityPainter {

  private BoardPainter boardPainter;
  private Unit unit;
  private Set<IPoint> reachableTiles;
  
  public UnitPainter(BoardPainter boardPainter) {
    this.boardPainter = boardPainter;
    reachableTiles = new TreeSet<IPoint>();
  }
  
  public void redraw(Graphics g) {
    Canvas canvas = boardPainter.getTileDrawCanvas(unit.gx(), unit.gy());
    if (canvas != null) {
      drawTile(g, canvas);
    }
    
    int idx = 0;
    for (Iterator<IPoint> it = unit.getTail().keySet().iterator(); it.hasNext(); idx++) {
      IPoint p = it.next();
      Canvas drawCanvas = boardPainter.getTileDrawCanvas(p.gx(), p.gy());
      if (drawCanvas != null) {
        drawTailTile(g, drawCanvas, idx + 1);
      }
    }
    
    if (!unit.isSelected()) return;
    
    State unitState = unit.getState();
    switch (unitState) {
    case MOVING:
      for (Iterator<IPoint> it = reachableTiles.iterator(); it.hasNext(); ) {
        IPoint p = it.next();
        Canvas drawCanvas = boardPainter.getTileDrawCanvas(p.gx(), p.gy());
        if (drawCanvas != null) {
          g.setColor(new Color(12,12,12));
          g.fillRect(drawCanvas.topLeft.gx() + 8, drawCanvas.topLeft.gy() + 8, drawCanvas.dimensions.gx() - 16, drawCanvas.dimensions.gy() - 16);
          g.setColor(new Color(0,0,0));
        }
      }
      break;
    case ATTACKING:
      for (Iterator<IPoint> it = reachableTiles.iterator(); it.hasNext(); ) {
        IPoint p = it.next();
        Canvas drawCanvas = boardPainter.getTileDrawCanvas(p.gx(), p.gy());
        if (drawCanvas != null) {
          g.setColor(new Color(0,255,12));
          g.fillRect(drawCanvas.topLeft.gx() + 8, drawCanvas.topLeft.gy() + 8, drawCanvas.dimensions.gx() - 16, drawCanvas.dimensions.gy() - 16);
          g.setColor(new Color(0,0,0));
        }
      }
      break;
    }
  }

  private void drawTile(Graphics g, Canvas canvas) {
    g.setColor(new Color(255,0,0));
    g.fillRect(canvas.topLeft.gx(), canvas.topLeft.gy(), canvas.dimensions.gx(), canvas.dimensions.gy());
    g.setColor(new Color(0,0,0));
  }
  
  private void drawTailTile(Graphics g, Canvas drawCanvas, int idx) {
    IPoint topLeft = drawCanvas.topLeft;
    drawTile(g, drawCanvas);
    g.setColor(unit.getConfig().getRGB().darker().darker());
    g.drawString(String.valueOf(idx), topLeft.gx() + 6, topLeft.gy() + 16);
    g.setColor(new Color(0,0,0));
  }
  
  public void attach(Unit unit) {
    this.unit = unit;
  }

  public void updateReachableWhenAttacking(int x, int y, int attackRange) {
    reachableTiles = boardPainter.getAdjacentTiles(new IPoint(x, y), attackRange, null);
  }
  
  public void updateReachable(int x, int y, int numRemainingMoves) {
    reachableTiles = boardPainter.getAdjacentTiles(new IPoint(x, y), numRemainingMoves, unit);
  }
  
  public Set<IPoint> getReachable() {
    return reachableTiles;
  }
}

/*
public class Unit extends Entity<UnitPainter> {

public enum State {
  MOVING,
  ATTACKING,
  DONE
}

private final Board board;
private final UnitConfig config;
private final Player player;

private State state;
private int x,y;
private int numRemainingMoves;
private Set<IPoint> reachableTiles;
private Map<IPoint,Boolean> tail;

public Unit(int x, int y, Board board, UnitConfig config, Player player) {
  this.x = x;
  this.y = y;
  this.config = config;
  this.player = player;
  
  state = State.DONE;
  tail = new LinkedHashMap<IPoint,Boolean>();
  reachableTiles = new TreeSet<IPoint>();
  
  this.board = board;
  this.board.addUnitAt(this.x, this.y, this);
}

@Override
public void redraw(Graphics g) {
  int idx = 0;
  /*for (Iterator<IPoint> it = tail.keySet().iterator(); it.hasNext(); idx++) {
    IPoint p = it.next();
    Canvas drawCanvas = board.getTileDrawCanvas(p.gx(), p.gy());
    if (drawCanvas != null) {
      drawTailTile(g, drawCanvas, idx + 1);
    }
  }
  
  Canvas headDrawCanvas = board.getTileDrawCanvas(x, y);
  if (headDrawCanvas != null) {
    drawHeadTile(g, board.getTileDrawCanvas(x, y));
  }
  
  for (Iterator<IPoint> it = reachableTiles.iterator(); it.hasNext(); ) {
    IPoint p = it.next();
    Canvas drawCanvas = board.getTileDrawCanvas(p.gx(), p.gy());
    if (drawCanvas != null) {
      g.setColor(new Color(12,12,12));
      g.fillRect(drawCanvas.topLeft.gx() + 8, drawCanvas.topLeft.gy() + 8, drawCanvas.dimensions.gx() - 16, drawCanvas.dimensions.gy() - 16);
      g.setColor(new Color(0,0,0));
    }
  }
  
  for (Iterator<IPoint> it = board.getAdjacentTiles(new IPoint(x, y)).iterator(); it.hasNext();) {
    IPoint p = it.next();
    Canvas drawCanvas = board.getTileDrawCanvas(p.gx(), p.gy());
    if (drawCanvas != null) {
      g.setColor(new Color(255,12,12));
      g.fillRect(drawCanvas.topLeft.gx() + 8, drawCanvas.topLeft.gy() + 8, drawCanvas.dimensions.gx() - 16, drawCanvas.dimensions.gy() - 16);
      g.setColor(new Color(0,0,0));
    }
  }
}


private Canvas drawTile(Graphics g, int x, int y) {
  Canvas drawCanvas = board.getTileDrawCanvas(x, y);
  if (drawCanvas == null) return null;
  
  g.setColor(config.getRGB());
  IPoint topLeft = drawCanvas.topLeft;
  IPoint dimensions = drawCanvas.dimensions;
  IPoint shadowOffset = board.getShadowOffset();
  g.setColor(config.getRGB().darker());
  g.fillRect(shadowOffset.gx() + topLeft.gx(), shadowOffset.gy() + topLeft.gy(), dimensions.gx(), dimensions.gy());
  g.setColor(config.getRGB());
  g.fillRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
  g.setColor(player.getColor());
  g.drawRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
  g.setColor(new Color(0,0,0));
  return drawCanvas;
}

/*  public void drawTailTile(Graphics g, int idx) {
  IPoint topLeft = drawCanvas.topLeft;
  drawTile(g, drawCanvas);
  g.setColor(config.getRGB().darker().darker());
  g.drawString(String.valueOf(idx), topLeft.gx() + 6, topLeft.gy() + 16);
  g.setColor(new Color(0,0,0));
}*/

/*  public void drawHeadTile(Graphics g, ) {
  drawTile(g, drawCanvas);
}

@Override
public void tick() {
  // TODO: move out control logic to the screen w/ forwarding
  
}

private void setReachableTiles(int availMoves) {
  reachableTiles = board.getAdjacentTiles(new IPoint(x, y), availMoves);
}

public void resetReachableTiles() {
  numRemainingMoves = config.getMovementSpeed();
  setReachableTiles(numRemainingMoves);
}

private void removeTail() {
  if (tail.isEmpty()) {
    // TODO: delete self
  }
  IPoint backOfTail = tail.keySet().iterator().next();
  removeTail(backOfTail.gx(), backOfTail.gy());
}

private void removeTail(int x, int y) {
  tail.remove(new IPoint(x, y));
  board.removeUnitFromTile(x, y);
}

private void addTail(int x, int y) {
  tail.put(new IPoint(x, y), true);
  board.addUnitAt(x, y, this); // TODO: bad naming
}

public void move(int xd, int yd) {
  int xn = x + xd;
  int yn = y + yd;
  if (!board.open(xn, yn) || (xn == x && yn == y) || reachableTiles.size() <= 1) {
    return;
  }
  
  addTail(x, y);
  if (tail.size() > config.max_tail_length) {
    removeTail();
  }
  tail.remove(new IPoint(xn, yn));
  board.addUnitAt(xn, yn, this);
  
  x = xn;
  y = yn;
  System.out.println(new IPoint(x, y));
  setReachableTiles(--numRemainingMoves);
}

public void attack(int xt, int yt) {
  // TODO: just temp
  int xtt = xt + x;
  int ytt = yt + y;
  board.attack(xtt, ytt);
  isAttacking = false;
}

public void damage(int amount) {
  while (amount-- >= 0) {
    popTail();
  }
}

public Player getPlayer() {
  return player;
}

}*/