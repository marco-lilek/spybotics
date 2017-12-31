package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Set;

import core.keyboard.Key;
import entity.Board;
import entity.unit.Unit;

public class LevelScreen extends GameScreen {

  private Unit testPlayer;
  private Board board;
  
  LevelScreen(ScreenType type) { // TODO: load in level config 
    super(type);
    board = new Board(10, 10);
    testPlayer = new Unit(0, 0, board);
  }

  @Override
  public ScreenType tick() {
    testPlayer.tick();
    return this.getType();
  }

  @Override
  public void redraw(Graphics g) {
    board.redraw(g);
    testPlayer.redraw(g);
  }

  @Override
  public void handleKeyStroke(Key key) {
    switch (key) {
    case LEFT:
        testPlayer.move(-1, 0);
        break;
    case RIGHT:
      testPlayer.move(1, 0);
      break;
    case UP:
      testPlayer.move(0, -1);
      break;
    case DOWN:
      testPlayer.move(0, 1);
      break;
    }
  }

}
