package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Set;

import config.BoardConfig;
import config.UnitConfig;
import core.Game;
import core.sprite.SpriteManager;
import entity.Board;
import entity.BoardPainter;
import entity.Cursor;
import entity.player.AIPlayer;
import entity.player.HumanPlayer;
import entity.player.Player;
import entity.unit.Unit;
import util.Canvas;
import util.IPoint;
import util.JSONLoader;
import util.communicator.Communicator;
import util.communicator.Message;

public class MatchScreen extends Screen {

  private static final int BOARD_XOFFSET = 270;
  private static final int BOARD_YOFFSET = 30;
  private static final int BOARD_WIDTH = BoardPainter.getFullTileSize() * 14;
  private static final int BOARD_HEIGHT = BoardPainter.getFullTileSize() * 11;
  
  MatchScreen(Game game) {
    super(game);
    this.getEntities().add(new Board(
        new BoardPainter(new Canvas(new IPoint(BOARD_XOFFSET, BOARD_YOFFSET), new IPoint(BOARD_WIDTH, BOARD_HEIGHT))), 
        JSONLoader.getLoader().loadJSONFromFile("config/test_board.json", BoardConfig.class)
        ));
  }

  @Override
  public void callbackRecv(Message msg) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void handleKeyStroke(Message msgFromKeyboard) {
    if (msgFromKeyboard == Message.KEYBOARD_KEY_SPACE) {
      notifyListener(Game.NAME, Message.GAME_SCREEN_TEST);
    }
  }
  
/*  private enum MatchState {
    LOADING,  // Players are selecting their units
    RUNNING   // Match has begun 
  }
  
  

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
    return ;
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
  public void callbackRecv(Boolean msg) {
    activePlayerIdx = (activePlayerIdx + 1) % players.length;
    players[activePlayerIdx].startTurn();
  }*/
}
