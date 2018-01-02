package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Set;

import config.BoardConfig;
import config.UnitConfig;
import core.keyboard.Key;
import core.sprite.SpriteManager;
import entity.Board;
import entity.Cursor;
import entity.unit.Unit;
import util.Canvas;
import util.IPoint;
import util.JSONLoader;

public class MatchScreen extends Screen {
  
  private static final int BOARD_XOFFSET = 270;
  private static final int BOARD_YOFFSET = 30;
  private static final int BOARD_WIDTH = Board.getFullTileSize() * 14;
  private static final int BOARD_HEIGHT = Board.getFullTileSize() * 11;

  private Unit testPlayer;
  private Board board;
  private Cursor cursor;
  
  // TODO: temporary control
  private boolean controlCursor;
  
  MatchScreen(ScreenType type) { // TODO: load in level config 
    super(type);
    
    board = new Board(getBoardCanvas(), JSONLoader.getLoader().loadJSONFromFile("config/test_board.json", BoardConfig.class));
    cursor = new Cursor(board);
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
    cursor.redraw(g);
    SpriteManager.getManager().drawSheet(g);
  }

  @Override
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
      break;
    }
    
    if (controlCursor) {
      cursor.move(xd, yd);
    } else {
      testPlayer.move(xd, yd);
    }
  }

}
