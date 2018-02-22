package entity.painter;

import util.Canvas;
import util.Direction;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import entity.Board;
import entity.unit.Unit;
import util.IPoint;

public class BoardPainter extends EntityPainter {
  private static final int TILEW = 32;
  private static final int TILESPACE = 4;
  
  private final Canvas drawCanvas;
  private Board board;
  private int canvasWidthTiles, canvasHeightTiles;
  
  public BoardPainter(Canvas availCanvasFromScreen) {
    this.drawCanvas = availCanvasFromScreen;
  }
  
  public void attach(Board board) {
    this.board = board;
    System.out.println(board.getConfig());
    IPoint boardDimensions = drawCanvas.dimensions;
    canvasWidthTiles = Math.min(board.getConfig().getWidthTiles(), boardDimensions.gx() / getFullTileSize());
    canvasHeightTiles = Math.min(board.getConfig().getHeightTiles(), boardDimensions.gy() / getFullTileSize());
    
    int totalWidthTiles = board.getConfig().getWidthTiles();
    int totalHeightTiles = board.getConfig().getHeightTiles();
    if (totalWidthTiles != canvasWidthTiles) throw new IllegalArgumentException("canvas width " + String.valueOf(canvasWidthTiles) + " " + String.valueOf(totalWidthTiles));
    if (totalHeightTiles != canvasHeightTiles) throw new IllegalArgumentException("canvas height " + String.valueOf(canvasHeightTiles) + " " + String.valueOf(totalHeightTiles));
    
  }
  
  public static int getFullTileSize() {
    return TILEW + TILESPACE;
  }

  @Override
  public void redraw(List<Graphics2D> g) {
    Graphics l0 = g.get(0);
    IPoint topLeft = drawCanvas.topLeft;
    IPoint dimensions = drawCanvas.dimensions;
    l0.drawRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
    
    int totalWidthTiles = board.getConfig().getWidthTiles();
    int totalHeightTiles = board.getConfig().getHeightTiles();
    
    for (int i = 0; i < totalWidthTiles; i++) {
      for (int j = 0; j < totalHeightTiles; j++) {
        if (board.hasFloorTileAt(i, j)) {
          Canvas floorTileCanvas = getTileDrawCanvas(i, j);
          if (floorTileCanvas != null) {
            IPoint ftTopLeft = floorTileCanvas.topLeft;
            IPoint ftDimensions = floorTileCanvas.dimensions;
            l0.drawRect(ftTopLeft.gx(), ftTopLeft.gy(), ftDimensions.gx(), ftDimensions.gy());
          }
        }
      }
    }
  }
  
  public Canvas getTileDrawCanvas(int x, int y) {
    if (x < 0 || y < 0  || x >= canvasWidthTiles || y >= canvasHeightTiles) {
      return null; // don't draw
    }
    int adjX = offsetTilex(x);
    int adjY = offsetTiley(y);
    IPoint topLeft = new IPoint(adjX, adjY);
    IPoint dimensions = new IPoint(TILEW, TILEW);
    return new Canvas(topLeft, dimensions);
  }
  
  private int offsetTilex(int x) {
    return drawCanvas.topLeft.gx() + (x) * getFullTileSize();
  }
  
  private int offsetTiley(int y) {
    return drawCanvas.topLeft.gy() + (y) * getFullTileSize();
  }

  public Canvas getTilesCanvas() {
    return new Canvas(new IPoint(0, 0), new IPoint(canvasWidthTiles - 1, canvasHeightTiles - 1));
  }
  
  public Set<IPoint> getAdjacentTiles(IPoint tile, int distance, Unit unit) {
    Set<IPoint> adjTiles = new TreeSet<IPoint>();
    adjTiles.add(tile);
    for (int i = 0; i < distance; i++) {
      Set<IPoint> newEntries = new TreeSet<IPoint>();
      for (Iterator<IPoint> rti = adjTiles.iterator(); rti.hasNext();) {
        IPoint reachableTile = rti.next();
        newEntries.addAll(getAdjacentTiles(reachableTile, unit));
      }
      adjTiles.addAll(newEntries);
    }
    return adjTiles;
  }
  
  private Set<IPoint> getAdjacentTiles(IPoint tile, Unit unit) {
    Set<IPoint>toRet = new TreeSet<IPoint>();
    int tx = tile.gx(), ty = tile.gy();
    int[][] moves = new int[][] {{-1,0}, {1,0}, {0,-1}, {0,1}};
    for (int[] move : moves) {
      int xn = tx + move[0], yn = ty + move[1];
      if ((unit == null && board.isOpenAt(xn, yn)) || 
          (unit != null && board.isOpenAt(xn, yn) && (board.getUnitAt(xn, yn) == unit || board.getUnitAt(xn, yn) == null))) {
        toRet.add(new IPoint(xn, yn));
      }
    }
    return toRet;
  }
}

/*
package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import config.BoardConfig;
import entity.unit.Unit;
import util.Canvas;
import util.Direction;
import util.IPoint;
import util.communicator.Message;

public class Board extends Entity {
  

  
  private BoardConfig boardConfig;
  private boolean floorTiles[][]; // TODO: make sure its a bitarray
  private Unit unitAtTiles[][];
  private int canvasWidthTiles, canvasHeightTiles;
  private int topLeftx, topLefty;
  
  public Board(Canvas canvas, BoardConfig boardConfig) {
    this.drawCanvas = canvas;
    
    IPoint boardDimensions = canvas.dimensions;
    canvasWidthTiles = Math.min(boardConfig.board_width, boardDimensions.gx() / getFullTileSize());
    canvasHeightTiles = Math.min(boardConfig.board_height, boardDimensions.gy() / getFullTileSize());
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
  
  public boolean open(int x, int y) {
    return isInBounds(x, y) && floorTiles[x][y];
  }
  
  public void addUnitAt(int x, int y, Unit u) {
    this.unitAtTiles[x][y] = u;
  }
  
  private int offsetTilex(int x) {
    return drawCanvas.topLeft.gx() + (x - topLeftx) * getFullTileSize();
  }
  
  private int offsetTiley(int y) {
    return drawCanvas.topLeft.gy() + (y - topLefty) * getFullTileSize();
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

  public static int getFullTileSize() {
    return TILEW + TILESPACE;
  }
  
  private Set<IPoint> getAdjacentTiles(IPoint tile) {
    Set<IPoint>toRet = new TreeSet<IPoint>();
    int tx = tile.gx(), ty = tile.gy();
    int[][] moves = new int[][] {{-1,0}, {1,0}, {0,-1}, {0,1}};
    for (int[] move : moves) {
      int xn = tx + move[0], yn = ty + move[1];
      if (open(xn, yn)) {
        toRet.add(new IPoint(xn, yn));
      }
    }
    return toRet;
  }
  
  public Set<IPoint> getAdjacentTiles(IPoint tile, int distance) {
    Set<IPoint> adjTiles = new TreeSet<IPoint>();
    adjTiles.add(tile);
    for (int i = 0; i < distance; i++) {
      Set<IPoint> newEntries = new TreeSet<IPoint>();
      for (Iterator<IPoint> rti = adjTiles.iterator(); rti.hasNext();) {
        IPoint reachableTile = rti.next();
        newEntries.addAll(getAdjacentTiles(reachableTile));
      }
      adjTiles.addAll(newEntries);
    }
    return adjTiles;
  }
  

  public void addUnitToTile(int x, int y, Unit unit) {
    unitAtTiles[x][y] = unit;
  }
  
  public void removeUnitFromTile(int x, int y) {
    unitAtTiles[x][y] = null;
  }
  
  public void attack(int xt, int yt) {
    if (!isInBounds(xt, yt)) return;
    if (unitAtTiles[xt][yt] == null) return;
    
    unitAtTiles[xt][yt].damage(3);
  }

  public Unit getUnitAtTile(int x, int y) {
    if (!isInBounds(x, y)) return null;
    return unitAtTiles[x][y];
  }

  @Override
  public void callbackRecv(Message msg) {
    // TODO Auto-generated method stub
    
  }
}
*/
