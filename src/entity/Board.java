package entity;

import java.awt.Graphics;
import java.util.Set;

import entity.unit.Unit;

public class Board extends Entity {
  private int widthTiles, heightTiles;
  private int xOffset, yOffset;
  private static final int TILEW = 32;
  private static final int TILEH = 32;
  
  private boolean floorTiles[][]; // TODO: make sure its a bitarray
  private Unit unitAtTiles[][];
  
  public Board(int widthTiles, int heightTiles) {
    this.widthTiles = widthTiles;
    this.heightTiles = heightTiles;
    this.xOffset = 64;
    this.yOffset = 64;
    floorTiles = new boolean[this.widthTiles][this.heightTiles];
    
    floorTiles[3][4] = true;
    
    unitAtTiles = new Unit[this.widthTiles][this.heightTiles];
  }

  @Override
  public void redraw(Graphics g) {
    for (int i = 0; i < widthTiles; i++) {
      for (int j = 0; j < heightTiles; j++) {
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
  
  public void drawTile(Graphics g, int x, int y, boolean fill) {
    int adjX = offsetTilex(x);
    int adjY = offsetTiley(y);
    if (fill) {
      g.fillRect(adjX, adjY, TILEW, TILEH);
    } else {
      g.drawRect(adjX, adjY, TILEW, TILEH);
    }
  }

  public void addUnitAt(int x, int y, Unit u) {
    this.unitAtTiles[x][y] = u;
  }
  
  private int offsetTilex(int x) {
    return xOffset + x * TILEW;
  }
  
  private int offsetTiley(int y) {
    return yOffset + y * TILEH;
  }
}
