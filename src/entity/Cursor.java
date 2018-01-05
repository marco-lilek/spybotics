package entity;

import java.awt.Graphics;

import core.Game;
import entity.player.Player;
import entity.unit.Unit;
import screen.Screen;
import util.Canvas;
import util.Direction;
import util.IPoint;
import util.communicator.Message;

public class Cursor extends Entity<CursorPainter> {

  private int x,y;
  private Board board;
  
  public Cursor(Screen screen, Board board, CursorPainter painter) {
    super(painter);
    this.board = board;
    painter.attach(this);
    screen.addListener(getName(), this);
  }

  public void move(int xd, int yd) {
    int xn = x + xd;
    int yn = y + yd;
    if (!board.isInBounds(xn, yn)) {
      return;
    }
    x = xn;
    y = yn;
    
    this.getPainter().move(xn, yn);
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
  public void callbackRecv(Message msg) {
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
    default:
      break;
    }
    
    move(xd, yd);
  }


  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getName() {
    return "Cursor";
  }
}
