package entity;

import java.awt.Graphics;
import java.util.Set;

import entity.unit.Unit;

public class Board extends Entity {
  private int widthTiles, heightTiles;
  private int xOffset, yOffset;
  private static final int TILEW = 10;
  private static final int TILEH = 10;
  
  private boolean floorTiles[][]; // TODO: make sure its a bitarray
  private Unit unitAtTiles[][];
  
  public Board(int widthTiles, int heightTiles) {
    this.widthTiles = widthTiles;
    this.heightTiles = heightTiles;
    this.xOffset = 50;
    this.yOffset = 50;
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
  public void tick(Set<String> pressedKeys) {
    // TODO Auto-generated method stub
    
  }
  
  private int getxOffset() {
    return xOffset;
  }
  
  private int getyOffset() {
    return yOffset;
  }
  
  public int offsetTilex(int tilex) {
    return getxOffset() + tilex * TILEW;
  }
  
  public int offsetTiley(int tiley) {
    return getyOffset() + tiley * TILEH;
  }

  public void addUnitAt(int x, int y, Unit u) {
    // TODO: throw error if tile is unavailable
    this.unitAtTiles[x][y] = u;
  }
  
  public int getTilew() {
    return TILEW;
  }
  
  public int getTileh() {
    return TILEH;
  }
}
