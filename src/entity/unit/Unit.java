package entity.unit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import config.UnitConfig;
import entity.Board;
import entity.Entity;
import util.Canvas;
import util.Direction;
import util.IPoint;

public class Unit extends Entity {

  private final Board board;
  private final UnitConfig unitConfig;
  private final Color playerColor;
  
  private int x,y;
  private Set<IPoint> reachableTiles;
  private int availMoves = 0;
  private boolean isAttacking;
  
  private Map<IPoint,Boolean> tail;
  
  public Unit(int x, int y, Board board, UnitConfig unitConfig, Color playerColor) {
    this.x = x;
    this.y = y;
    this.unitConfig = unitConfig;
    this.playerColor = playerColor;
    
    tail = new LinkedHashMap<IPoint,Boolean>();
    reachableTiles = new TreeSet<IPoint>();
    isAttacking = false;
    
    this.board = board;
    this.board.addUnitAt(this.x, this.y, this);
  }

  @Override
  public void redraw(Graphics g) {
    int idx = 0;
    for (Iterator<IPoint> it = tail.keySet().iterator(); it.hasNext(); idx++) {
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
    
    if (isAttacking()) {
      
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
    
  }


  private void drawTile(Graphics g, Canvas drawCanvas) {
    g.setColor(unitConfig.getRGB());
    IPoint topLeft = drawCanvas.topLeft;
    IPoint dimensions = drawCanvas.dimensions;
    IPoint shadowOffset = board.getShadowOffset();
    g.setColor(unitConfig.getRGB().darker());
    g.fillRect(shadowOffset.gx() + topLeft.gx(), shadowOffset.gy() + topLeft.gy(), dimensions.gx(), dimensions.gy());
    g.setColor(unitConfig.getRGB());
    g.fillRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
    g.setColor(playerColor);
    g.drawRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
    g.setColor(new Color(0,0,0));
  }
  
  public void drawTailTile(Graphics g, Canvas drawCanvas, int idx) {
    IPoint topLeft = drawCanvas.topLeft;
    drawTile(g, drawCanvas);
    g.setColor(unitConfig.getRGB().darker().darker());
    g.drawString(String.valueOf(idx), topLeft.gx() + 6, topLeft.gy() + 16);
    g.setColor(new Color(0,0,0));
  }
  
  public void drawHeadTile(Graphics g, Canvas drawCanvas) {
    drawTile(g, drawCanvas);
  }
  
  @Override
  public void tick() {
    // TODO: move out control logic to the screen w/ forwarding
    
  }
  
  private void setReachableTiles(int availMoves) {
    reachableTiles.clear();
    reachableTiles.add(new IPoint(x, y));
    for (int i = 0; i < availMoves; i++) {
      Set<IPoint> newEntries = new TreeSet<IPoint>();
      for (Iterator<IPoint> rti = reachableTiles.iterator(); rti.hasNext();) {
        IPoint reachableTile = rti.next();
        newEntries.addAll(board.getAdjacentTiles(reachableTile));
      }
      reachableTiles.addAll(newEntries);
    }
    System.out.println(reachableTiles);
  }
  
  public void resetReachableTiles() {
    availMoves = unitConfig.movement_speed;
    setReachableTiles(availMoves);
  }
  
  private void popTail() {
    if (tail.isEmpty()) { // TODO: ovi not sufficient
      return;
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
    if (tail.size() > unitConfig.max_tail_length) {
      popTail();
    }
    tail.remove(new IPoint(xn, yn));
    board.addUnitAt(xn, yn, this);
    
    x = xn;
    y = yn;
    System.out.println(new IPoint(x, y));
    setReachableTiles(--availMoves);
  }

  public void setAtacking() {
    isAttacking = true;
    setReachableTiles(0);
  }

  public boolean isAttacking() {
    return isAttacking;
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

}
