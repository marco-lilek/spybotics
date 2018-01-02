package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Set;

import config.BoardConfig;
import config.UnitConfig;
import core.keyboard.Key;
import core.sprite.SpriteManager;
import entity.Board;
import entity.unit.Unit;
import util.Canvas;
import util.IPoint;
import util.JSONLoader;

public class MatchScreen extends Screen {
  
  private static final int BOARD_XOFFSET = 300;
  private static final int BOARD_YOFFSET = 30;
  private static final int BOARD_WIDTH = 400;
  private static final int BOARD_HEIGHT = 400;

  private Unit testPlayer;
  private Board board;
  
  MatchScreen(ScreenType type) { // TODO: load in level config 
    super(type);
    board = new Board(getBoardCanvas(), JSONLoader.getLoader().loadJSONFromFile("config/test_board.json", BoardConfig.class));
    testPlayer = new Unit(0, 0, board, JSONLoader.getLoader().loadJSONFromFile("config/test_unit.json", UnitConfig.class));
  }

  private Canvas getBoardCanvas() {
    return new Canvas(new IPoint(BOARD_XOFFSET, BOARD_YOFFSET), new IPoint(BOARD_WIDTH, BOARD_HEIGHT));
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

    SpriteManager.getManager().drawSheet(g);
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
