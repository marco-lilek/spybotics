package entity.painter;

import java.awt.Color;
import java.awt.Graphics;

import entity.Cursor;
import util.Canvas;
import util.Direction;
import util.IPoint;

public class CursorPainter extends EntityPainter {

  private Cursor cursor;
  private final BoardPainter boardPainter;
  
  public CursorPainter(BoardPainter boardPainter) {
    this.boardPainter = boardPainter;
  }
  
  public void attach(Cursor cursor) {
    this.cursor = cursor;
  }
  
  @Override
  public void redraw(Graphics g) {
    Canvas drawCanvas = boardPainter.getTileDrawCanvas(cursor.gx(), cursor.gy());
    if (drawCanvas != null) {
      g.setColor(new Color(0,255,255));
      g.drawRect(drawCanvas.topLeft.gx(), drawCanvas.topLeft.gy(), drawCanvas.dimensions.gx(), drawCanvas.dimensions.gy());
      g.setColor(new Color(0,0,0));
    }
  }

  public void move(int xn, int yn) {
    Canvas tilesCanvas = boardPainter.getTilesCanvas();
    IPoint topLeft = tilesCanvas.topLeft;
    IPoint bottomRight = tilesCanvas.getBottomRight();
    
    if (cursor.gx() < topLeft.gx()) boardPainter.shiftCanvas(Direction.WEST);
    if (cursor.gx() > bottomRight.gx()) boardPainter.shiftCanvas(Direction.EAST);
    if (cursor.gy() < topLeft.gy()) boardPainter.shiftCanvas(Direction.NORTH);
    if (cursor.gy() > bottomRight.gy()) boardPainter.shiftCanvas(Direction.SOUTH);
  }

}
