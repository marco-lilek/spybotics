package entity.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

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
  public void redraw(List<Graphics2D> g) {
    Graphics l3 = g.get(2);
    Canvas drawCanvas = boardPainter.getTileDrawCanvas(cursor.gx(), cursor.gy());
    if (drawCanvas != null) {
      l3.setColor(new Color(0,255,255));
      l3.drawRect(drawCanvas.topLeft.gx(), drawCanvas.topLeft.gy(), drawCanvas.dimensions.gx(), drawCanvas.dimensions.gy());
      l3.setColor(new Color(0,0,0));
    }
  }

}
