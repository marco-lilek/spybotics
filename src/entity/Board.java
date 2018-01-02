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
  private int canvasWidthTiles, canvasHeightTiles;
  private int topLeftx, topLefty;
  
  public Board(Canvas canvas, BoardConfig boardConfig) {
    this.drawCanvas = canvas;
    
    IPoint boardDimensions = canvas.dimensions;
    canvasWidthTiles = boardDimensions.gx() / (TILEW + TILESPACE);
    canvasHeightTiles = boardDimensions.gy() / (TILEW + TILESPACE);
    topLeftx = 0;
    topLefty = 0;
    
    this.boardConfig = boardConfig;
    this.floorTiles = boardConfig.getFloorTiles();
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
          Canvas floorTileCanvas = getTileDrawCanvas(i, j);
          if (floorTileCanvas != null) {
            IPoint ftTopLeft = floorTileCanvas.topLeft;
            IPoint ftDimensions = floorTileCanvas.dimensions;
            g.drawRect(ftTopLeft.gx(), ftTopLeft.gy(), ftDimensions.gx(), ftDimensions.gy());
          }
        }
      }
    }
  }

  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }
  
  public Canvas getTileDrawCanvas(int x, int y) {
    if (x < topLeftx || y < topLefty  || x >= topLeftx + canvasWidthTiles || y >= topLefty + canvasHeightTiles) {
      return null; // don't draw
    }
    int adjX = offsetTilex(x);
    int adjY = offsetTiley(y);
    IPoint topLeft = new IPoint(adjX, adjY);
    IPoint dimensions = new IPoint(TILEW, TILEW);
    return new Canvas(topLeft, dimensions);
  }
  
  public IPoint getShadowOffset() {
    return new IPoint(TILESPACE / 2, TILESPACE / 2);
  }
  
  public boolean isInBounds(int x, int y) {
    if (x < 0 || x >= boardConfig.board_width || y < 0 || y >= boardConfig.board_height) {
      return false;
    }
    return true;
  }
  
  public void addUnitAt(int x, int y, Unit u) {
    this.unitAtTiles[x][y] = u;
  }
  
  private int offsetTilex(int x) {
    return drawCanvas.topLeft.gx() + (x - topLeftx) * (TILEW + TILESPACE);
  }
  
  private int offsetTiley(int y) {
    return drawCanvas.topLeft.gy() + (y - topLefty) * (TILEW + TILESPACE);
  }

  public Canvas getTilesCanvas() {
    return new Canvas(new IPoint(topLeftx, topLefty), new IPoint(canvasWidthTiles - 1, canvasHeightTiles - 1));
  }

  public void shiftCanvas(Direction direction) {
    switch (direction) {
    case NORTH:
      topLefty--;
      break;
    case SOUTH:
      topLefty++;
      break;
    case EAST:
      topLeftx++;
      break;
    case WEST:
      topLeftx--;
      break;
    }
  }
}
