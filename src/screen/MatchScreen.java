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
import entity.Player;
import entity.unit.Unit;
import util.Canvas;
import util.IPoint;
import util.JSONLoader;

public class MatchScreen extends Screen {
  
  private static final int BOARD_XOFFSET = 270;
  private static final int BOARD_YOFFSET = 30;
  private static final int BOARD_WIDTH = Board.getFullTileSize() * 14;
  private static final int BOARD_HEIGHT = Board.getFullTileSize() * 11;

  private Board board;
  private Player player;
  
  // TODO: temporary control
  private boolean controlCursor;
  
  MatchScreen(ScreenType type) { // TODO: load in level config 
    super(type);
    
    board = new Board(getBoardCanvas(), JSONLoader.getLoader().loadJSONFromFile("config/test_board.json", BoardConfig.class));
    player = new Player(board);
  }

  private Canvas getBoardCanvas() {
    return new Canvas(new IPoint(BOARD_XOFFSET, BOARD_YOFFSET), new IPoint(BOARD_WIDTH, BOARD_HEIGHT));
  }

  @Override
  public ScreenType tick() {
    player.tick();
    return this.getType();
  }

  @Override
  public void redraw(Graphics g) {
    board.redraw(g);
    player.redraw(g);
    SpriteManager.getManager().drawSheet(g);
  }

  @Override
  public void handleKeyStroke(Key key) {
    player.handleKeyStroke(key);
  }

}
