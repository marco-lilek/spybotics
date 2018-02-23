package entity.player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import entity.Board;
import entity.Cursor;
import entity.painter.BoardPainter;
import entity.painter.CursorPainter;
import screen.MatchScreen;
import screen.Screen;
import util.Canvas;

public class HumanPlayer extends Player {
  
  private final Cursor cursor;
  private final MatchScreen screen;
  
  public HumanPlayer(MatchScreen screen) {
    super(screen);
    this.screen = screen;
    cursor = new Cursor(screen, this);
  }

  @Override
  public void redraw(List<Graphics2D> g) {
    super.redraw(g);
  }
  
  public Cursor getCursor() { return cursor; }
}
