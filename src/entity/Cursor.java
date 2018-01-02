package entity;

import java.awt.Graphics;

import util.Canvas;
import util.Direction;
import util.IPoint;

public class Cursor extends Entity {

  private int x,y;
  private Board board;
  
  public Cursor(Board board) {
    this.board = board;
  }
  
  @Override
  public void redraw(Graphics g) {
    Canvas drawCanvas = board.getTileDrawCanvas(x, y);
    if (drawCanvas != null) {
      g.fillRect(drawCanvas.topLeft.gx(), drawCanvas.topLeft.gy(), drawCanvas.dimensions.gx(), drawCanvas.dimensions.gy());
    }
  }

  @Override
  public void tick() {
    
  }
  
  public void move(int xd, int yd) {
    int xn = x + xd;
    int yn = y + yd;
    if (!board.isInBounds(xn, yn)) {
      return;
    }
    x = xn;
    y = yn;
    Canvas tilesCanvas = board.getTilesCanvas();
    IPoint topLeft = tilesCanvas.topLeft;
    IPoint bottomRight = tilesCanvas.getBottomRight();
    
    if (x < topLeft.gx()) board.shiftCanvas(Direction.WEST);
    if (x > bottomRight.gx()) board.shiftCanvas(Direction.EAST);
    if (y < topLeft.gy()) board.shiftCanvas(Direction.NORTH);
    if (y > bottomRight.gy()) board.shiftCanvas(Direction.SOUTH);
    
    
  }

}
