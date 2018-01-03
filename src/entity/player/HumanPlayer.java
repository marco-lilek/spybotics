package entity.player;

import java.awt.Graphics;

import core.keyboard.Key;
import entity.Board;
import entity.Cursor;
import screen.MatchScreen;
import screen.Screen;

public class HumanPlayer extends Player {

  private boolean controlCursor;
  private final Cursor cursor;
  
  public HumanPlayer(Board board, Screen screen) {
    super(board, screen);
    controlCursor = true;
    cursor = new Cursor(board);
  }

  public void handleKeyStroke(Key key) {
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
      controlCursor = !controlCursor;
      if (!controlCursor) {
        unit.resetReachableTiles();
      }
      break;
    case E:
      finishTurn();
    }
    if (controlCursor) {
      cursor.move(xd, yd);
    } else {
      unit.move(xd, yd);
    }
  }

  @Override
  public void redraw(Graphics g) {
    super.redraw(g);
    cursor.redraw(g);
  }

  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void startTurn() {
    // TODO Auto-generated method stub
    
  }
}
