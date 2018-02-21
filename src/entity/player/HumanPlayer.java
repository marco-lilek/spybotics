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
  private final MatchScreen screen;
  
  public HumanPlayer(Board board, MatchScreen screen, BoardPainter boardPainter) {
    super(board, screen);
    this.screen = screen;
    cursor = new Cursor(screen, board, new CursorPainter(boardPainter), this);
  }

  public void handleKeyboardMsg(Message msgFromKeyboard) {
    if (msgFromKeyboard == Message.KEYBOARD_KEY_A) finishTurn();
    cursor.handleKeyboardMsg(msgFromKeyboard);
  }

  @Override
  public void redraw(Graphics g) {
    super.redraw(g);
    if (screen.whosTurn() == this) {
      cursor.redraw(g);
    }
  }

  @Override
  public void tick() {
  }
}
