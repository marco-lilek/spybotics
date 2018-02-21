package entity;

import java.awt.Graphics;

import core.Game;
import entity.painter.CursorPainter;
import entity.player.Player;
import entity.unit.Unit;
import screen.Screen;
import util.Canvas;
import util.Direction;
import util.IPoint;
import util.communicator.Message;

public class Cursor extends Entity {

  private int x,y;
  private Board board;
  private Unit selectedUnit;
  private CursorPainter painter;
  
  public Cursor(Screen screen, Board board, CursorPainter painter) {
    this.board = board;
    this.painter = painter;
    painter.attach(this);
  }

  private void move(int xd, int yd) {
    int xn = x + xd;
    int yn = y + yd;
    if (!board.isInBounds(xn, yn)) {
      return;
    }
    x = xn;
    y = yn;
  }

/*  public void handleKeyStroke(Key key) {
    int xd=0,yd=0;
    switch (key) {
    case LEFT:
      xd = -1;
      break;
    case RIGHT:
      xd = 1;
      break;
    case UP:
      yd = -1;
      break;
    case DOWN:
      yd = 1;
      break;
    case SPACE:
      Unit unitUnderMe = board.getUnitAtTile(x, y);
      if (activeUnit.getPlayer() == player) {
        unitUnderControl = unitUnderMe;
      }
      return;
    case A:
      return;
    }
    
    move(xd, yd);
    if (unitUnderControl != null) {
      unitUnderControl.move(xd, yd);
    }
  }
*/
  public int gx() {
    return x;
  }
  
  public int gy() {
    return y;
  }

  @Override
  public void handleKeyboardMsg(Message msg) {
    int xd=0,yd=0;
    switch (msg) {
    case KEYBOARD_KEY_LEFT:
      xd = -1;
      break;
    case KEYBOARD_KEY_RIGHT:
      xd = 1;
      break;
    case KEYBOARD_KEY_UP:
      yd = -1;
      break;
    case KEYBOARD_KEY_DOWN:
      yd = 1;
      break;
    case KEYBOARD_KEY_SPACE:
      toggleUnitSelection();
      return;
    default:
      break;
    }
    
    if (selectedUnit != null && !selectedUnit.move(xd, yd)) {
      return;
    }
    move(xd, yd);
  }
  
  private void toggleUnitSelection() {
    if (selectedUnit != null) {
      selectedUnit.undoMove();
      selectedUnit = null;
    } else {
      selectedUnit = board.getUnitAt(x, y);
    }
  }


  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void redraw(Graphics g) {
    painter.redraw(g);
  }
}
