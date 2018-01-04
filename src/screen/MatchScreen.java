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
import entity.player.AIPlayer;
import entity.player.HumanPlayer;
import entity.player.Player;
import entity.unit.Unit;
import util.Canvas;
import util.IPoint;
import util.JSONLoader;

public class MatchScreen extends Screen {
  
  private enum MatchState {
    LOADING,  // Players are selecting their units
    RUNNING   // Match has begun 
  }
  
  private static final int BOARD_XOFFSET = 270;
  private static final int BOARD_YOFFSET = 30;
  private static final int BOARD_WIDTH = Board.getFullTileSize() * 14;
  private static final int BOARD_HEIGHT = Board.getFullTileSize() * 11;

  private Board board;
  private Player[] players;
  private int activePlayerIdx;
  
  MatchScreen(ScreenType type) { // TODO: load in level config 
    super(type);
    
    board = new Board(getBoardCanvas(), JSONLoader.getLoader().loadJSONFromFile("config/test_board.json", BoardConfig.class));
    players = new Player[] {new HumanPlayer(board, this), new HumanPlayer(board, this)};
    activePlayerIdx = 0;
  }

  private Canvas getBoardCanvas() {
    return new Canvas(new IPoint(BOARD_XOFFSET, BOARD_YOFFSET), new IPoint(BOARD_WIDTH, BOARD_HEIGHT));
  }

  @Override
  public ScreenType tick() {
    for (Player p : players) {
      p.tick();
    }
    return this.getType();
  }

  @Override
  public void redraw(Graphics g) {
    board.redraw(g);
    for (Player p : players) {
      p.redraw(g);
    }
    SpriteManager.getManager().drawSheet(g);
  }

  @Override
  public void handleKeyStroke(Key key) {
    players[activePlayerIdx].handleKeyStroke(key);
  }

  @Override
  public void callback(Boolean msg) {
    activePlayerIdx = (activePlayerIdx + 1) % players.length;
    players[activePlayerIdx].startTurn();
  }
}
