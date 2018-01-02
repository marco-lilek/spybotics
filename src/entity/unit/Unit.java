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

  private Board board;
  private int x,y;
  private Set<IPoint> reachableTiles;
  private int availMoves = 0;
  
  private Map<IPoint,Boolean> tail;
  private UnitConfig unitConfig;
  
  public Unit(int x, int y, Board board, UnitConfig unitConfig) {
    this.x = x;
    this.y = y;
    this.unitConfig = unitConfig;
    
    tail = new LinkedHashMap<IPoint,Boolean>();
    reachableTiles = new TreeSet<IPoint>();
    
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
      Canvas drawCanvas = board.getTileDrawCanvas(px, py);
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
      g.setColor(new Color(12,12,12));
      g.fillRect(drawCanvas.topLeft.gx(), drawCanvas.topLeft.gy(), drawCanvas.dimensions.gx(), drawCanvas.dimensions.gy());
      g.setColor(new Color(0,0,0));
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
  
  
  
  public void move(int xd, int yd) {
    int xn = x + xd;
    int yn = y + yd;
    if (!board.open(xn, yn) || (xn == x && yn == y) || reachableTiles.size() <= 1) {
      return;
    }
    
    setReachableTiles(--availMoves);
    
    tail.put(new IPoint(x,y), true);
    if (tail.size() > unitConfig.max_tail_length) {
      IPoint top = tail.keySet().iterator().next();
      tail.remove(top);
    }
    tail.remove(new IPoint(xn, yn));
    
    x = xn;
    y = yn;
  }

}
