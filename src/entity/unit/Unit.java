package entity.unit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import config.UnitConfig;
import entity.Board;
import entity.Entity;
import util.Canvas;
import util.Direction;
import util.IPoint;

public class Unit extends Entity {

  private Board board;
  private int x,y;
  private Map<IPoint,Boolean> tail;
  private UnitConfig unitConfig;
  
  public Unit(int x, int y, Board board, UnitConfig unitConfig) {
    this.x = x;
    this.y = y;
    this.unitConfig = unitConfig;
    
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
      drawTailTile(g, board.getTileDrawCanvas(px, py), idx + 1);
    }
    
    drawHeadTile(g, board.getTileDrawCanvas(x, y));
    //board.drawHeadTile(g, x, y);
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
  
  public void move(int xd, int yd) {
    System.out.println(tail);
    int xn = x + xd;
    int yn = y + yd;
    
    tail.put(new IPoint(x,y), true);
    if (tail.size() > unitConfig.getMaxTailLength()) {
      IPoint top = tail.keySet().iterator().next();
      tail.remove(top);
    }
    tail.remove(new IPoint(xn, yn));
    
    x = xn;
    y = yn;
    
  }

}
