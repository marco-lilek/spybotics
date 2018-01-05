package entity.player;

import java.awt.Graphics;

import core.keyboard.Key;
import entity.Board;
import entity.Cursor;
import screen.MatchScreen;
import screen.Screen;

public class HumanPlayer extends Player {
  
  private final Cursor cursor;
  
  public HumanPlayer(Board board, Screen screen) {
    super(board, screen);
    cursor = new Cursor(board);
  }

  public void handleKeyStroke(Key key) {
    cursor.handleKeyStroke(key);
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
