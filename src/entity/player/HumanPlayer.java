package entity.player;

import java.awt.Graphics;

import entity.Board;
import entity.Cursor;
import entity.painter.BoardPainter;
import entity.painter.CursorPainter;
import screen.MatchScreen;
import screen.Screen;
import util.communicator.Message;

public class HumanPlayer extends Player {
  
  private final Cursor cursor;
  
  public HumanPlayer(Board board, Screen screen, BoardPainter boardPainter) {
    super(board, screen);
    cursor = new Cursor(screen, board, new CursorPainter(boardPainter));
  }

  public void handleKeyboardMsg(Message msgFromKeyboard) {
    if (msgFromKeyboard == Message.KEYBOARD_KEY_A) finishTurn();
    cursor.handleKeyboardMsg(msgFromKeyboard);
  }

  @Override
  public void redraw(Graphics g) {
    super.redraw(g);
    cursor.redraw(g);
  }

  @Override
  public void tick() {
  }
}
