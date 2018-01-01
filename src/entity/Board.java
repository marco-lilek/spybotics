package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Set;

import config.BoardConfig;
import entity.unit.Unit;
import util.Canvas;
import util.Direction;
import util.IPoint;

public class Board extends Entity {
  private static final int WIDTH_TILES = 4;//16;
  private static final int HEIGHT_TILES = 4;//14;
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
  
  public Board(BoardConfig boardConfig) {
    // TODO: width and height here specifies the dim on the screen, but the actual board could be larger than the screen
    this.floorTiles = boardConfig.getFloorTiles(); //new boolean[WIDTH_TILES][HEIGHT_TILES]; 
    
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
  
  public Canvas getTileDrawCanvas(int x, int y) {
    int adjX = offsetTilex(x);
    int adjY = offsetTiley(y);
    IPoint topLeft = new IPoint(adjX, adjY);
    IPoint dimensions = new IPoint(TILEW, TILEW);
    return new Canvas(topLeft, dimensions);
  }
  
  public IPoint getShadowOffset() {
    return new IPoint(TILESPACE / 2, TILESPACE / 2);
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
