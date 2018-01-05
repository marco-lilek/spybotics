package entity;

import java.awt.Graphics;

import util.Canvas;
import util.Direction;
import util.IPoint;

public class CursorPainter extends EntityPainter<Cursor> {

  private final BoardPainter boardPainter;
  
  public CursorPainter(BoardPainter boardPainter) {
    this.boardPainter = boardPainter;
  }
  
  @Override
  public void redraw(Graphics g) {
    Cursor cursor = this.getLogicContainer();
    Canvas drawCanvas = boardPainter.getTileDrawCanvas(cursor.gx(), cursor.gy());
    if (drawCanvas != null) {
      g.fillRect(drawCanvas.topLeft.gx(), drawCanvas.topLeft.gy(), drawCanvas.dimensions.gx(), drawCanvas.dimensions.gy());
    }
  }

  public void move(int xn, int yn) {
    Canvas tilesCanvas = boardPainter.getTilesCanvas();
    IPoint topLeft = tilesCanvas.topLeft;
    IPoint bottomRight = tilesCanvas.getBottomRight();
    
    Cursor c = getLogicContainer();
    if (c.gx() < topLeft.gx()) boardPainter.shiftCanvas(Direction.WEST);
    if (c.gx() > bottomRight.gx()) boardPainter.shiftCanvas(Direction.EAST);
    if (c.gy() < topLeft.gy()) boardPainter.shiftCanvas(Direction.NORTH);
    if (c.gy() > bottomRight.gy()) boardPainter.shiftCanvas(Direction.SOUTH);
  }

}
