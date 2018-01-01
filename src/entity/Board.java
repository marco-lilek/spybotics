package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Set;

import entity.unit.Unit;
import util.Direction;
import util.IPoint;

public class Board extends Entity {
  private static final int WIDTH_TILES = 16;
  private static final int HEIGHT_TILES = 14;
  private static final int XOFFSET = 300;
  private static final int YOFFSET = 30;
  private static final int TILEW = 32;
  private static final int TILESPACE = 4;
  private static final int XPADDING = 30;
  private static final int YPADDING = 30;
  
  public static int getBoardScreenWidth() {
    return offsetTilex(WIDTH_TILES) + XPADDING;
  }
  
  public static int getBoardScreenHeight() {
    return offsetTiley(HEIGHT_TILES) + YPADDING;
  }
  
  private boolean floorTiles[][]; // TODO: make sure its a bitarray
  private Unit unitAtTiles[][];
  
  public Board() {
    floorTiles = new boolean[WIDTH_TILES][HEIGHT_TILES];
    
    //floorTiles[3][4] = true;
    
    unitAtTiles = new Unit[WIDTH_TILES][HEIGHT_TILES];
  }

  @Override
  public void redraw(Graphics g) {
    g.drawRect(XOFFSET, YOFFSET, offsetTilex(WIDTH_TILES - 1) - XOFFSET, offsetTiley(HEIGHT_TILES - 1) - YOFFSET);
    for (int i = 0; i < WIDTH_TILES; i++) {
      for (int j = 0; j < HEIGHT_TILES; j++) {
        if (floorTiles[i][j]) {
          g.drawRect(offsetTilex(i), offsetTiley(j), TILEW, TILEW);
        }
      }
    }
  }

  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }
  
  private IPoint drawUnitTile(Graphics g, int x, int y) {
    int adjX = offsetTilex(x);
    int adjY = offsetTiley(y);
    
    // Shadow
    g.setColor(new Color(212,28,28));
    g.fillRect(adjX + TILESPACE / 2, adjY + TILESPACE / 2, TILEW, TILEW);
    
    g.setColor(new Color(22,160,22));
    g.fillRect(adjX, adjY, TILEW, TILEW);
    g.setColor(new Color(0,0,0));
    return new IPoint(adjX, adjY);
  }
  
  public void drawTailTile(Graphics g, int x, int y, int idx) {
    IPoint p = drawUnitTile(g, x, y);
    g.drawString(String.valueOf(idx), p.gx() + TILEW / 8, p.gy() + TILEW / 2);
  }
  
  public void drawHeadTile(Graphics g, int x, int y) {
    drawUnitTile(g, x, y);
  }

  public void addUnitAt(int x, int y, Unit u) {
    this.unitAtTiles[x][y] = u;
  }
  
  private static int offsetTilex(int x) {
    return XOFFSET + x * (TILEW + TILESPACE);
  }
  
  private static int offsetTiley(int y) {
    return YOFFSET + y * (TILEW + TILESPACE);
  }
}
