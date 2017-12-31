package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;

import entity.unit.Unit;

public class Board extends Entity {
  private static final int WIDTH_TILES = 16;
  private static final int HEIGHT_TILES = 14;
  private static final int XOFFSET = 300;
  private static final int YOFFSET = 30;
  private static final int TILEW = 32;
  private static final int TILEH = 32;
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
          g.drawRect(offsetTilex(i), offsetTiley(j), TILEW, TILEH);
        }
      }
    }
  }

  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }
  
  public void drawUnitTile(Graphics g, int x, int y, boolean fill) {
    int adjX = offsetTilex(x);
    int adjY = offsetTiley(y);
    g.setColor(new Color(212,28,28));
    g.fillRect(adjX + TILESPACE / 2, adjY + TILESPACE / 2, TILEW, TILEH);
    
    g.setColor(new Color(22,160,22));
    g.fillRect(adjX, adjY, TILEW, TILEH);
    g.setColor(new Color(0,0,0));
  }

  public void addUnitAt(int x, int y, Unit u) {
    this.unitAtTiles[x][y] = u;
  }
  
  private static int offsetTilex(int x) {
    return XOFFSET + x * (TILEW + TILESPACE);
  }
  
  private static int offsetTiley(int y) {
    return YOFFSET + y * (TILEH + TILESPACE);
  }
}
