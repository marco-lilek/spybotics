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
  private static final int TILEW = 32;
  private static final int TILESPACE = 4;

  private Canvas drawCanvas;
  private BoardConfig boardConfig;
  private boolean floorTiles[][]; // TODO: make sure its a bitarray
  private Unit unitAtTiles[][];
  
  public Board(Canvas canvas, BoardConfig boardConfig) {
    this.drawCanvas = canvas;
    this.boardConfig = boardConfig;
    this.floorTiles = boardConfig.getFloorTiles();
    
    //floorTiles[3][4] = true;
    
    unitAtTiles = new Unit[boardConfig.board_width][boardConfig.board_height];
  }

  @Override
  public void redraw(Graphics g) {
    IPoint topLeft = drawCanvas.topLeft;
    IPoint dimensions = drawCanvas.dimensions;
    g.drawRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
    for (int i = 0; i < boardConfig.board_width; i++) {
      for (int j = 0; j < boardConfig.board_height; j++) {
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
  
  private int offsetTilex(int x) {
    return drawCanvas.topLeft.gx() + x * (TILEW + TILESPACE);
  }
  
  private int offsetTiley(int y) {
    return drawCanvas.topLeft.gy() + y * (TILEW + TILESPACE);
  }
}
