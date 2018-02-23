package screen;

import java.awt.Graphics;
import java.util.List;
import java.awt.event.ActionEvent;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import config.BoardConfig;
import config.UnitConfig;
import core.Game;
import core.sprite.SpriteManager;
import entity.Board;
import entity.Cursor;
import entity.Entity;
import entity.Unit;
import entity.Unit.State;
import entity.painter.BoardPainter;
import entity.painter.CursorPainter;
import entity.painter.UnitPainter;
import entity.player.AIPlayer;
import entity.player.HumanPlayer;
import entity.player.Player;
import util.Canvas;
import util.IPoint;
import util.JSONLoader;
import util.communicator.Communicator;
import util.communicator.KeyboardMessage;

public class MatchScreen extends Screen {
  
  private Board board;
  private List<Player> players;
  private int activePlayer;
  
  MatchScreen() {
    activePlayer = 0;
    JSONLoader configLoader = JSONLoader.getLoader();
    Player p1 = new HumanPlayer(this);
    Player p2 = new HumanPlayer(this);
    players = new ArrayList<Player>();
    players.add(p1);
    players.add(p2);
    
    board = new Board(this, configLoader.loadJSONFromFile("config/test_board.json", BoardConfig.class));
    Unit u1 = new Unit(this, configLoader.loadJSONFromFile("config/test_unit.json", UnitConfig.class), 0, 0);
    Unit u2 = new Unit(this, configLoader.loadJSONFromFile("config/test_unit.json", UnitConfig.class), 5, 0);
    p1.own(u1);
    p2.own(u2);
  }
  
  public Player whosTurn() {
    return players.get(activePlayer);
  }

  @Override
  public void handleInput(KeyboardMessage msg) {
    HumanPlayer player = (HumanPlayer) players.get(activePlayer);
    Cursor activeCursor = player.getCursor(); // assuming both players are human for now
    int x = activeCursor.gx(); int y = activeCursor.gy();
    
    Unit selectedUnit = activeCursor.getSelectedUnit();
    Unit unitAt = board.getUnitAt(activeCursor.gx(), activeCursor.gy());
    
    int xd=0,yd=0;
    switch (msg) {
    case KEYBOARD_KEY_LEFT:
      xd = -1;
      break;
    case KEYBOARD_KEY_RIGHT:
      xd = 1;
      break;
    case KEYBOARD_KEY_UP:
      yd = -1;
      break;
    case KEYBOARD_KEY_DOWN:
      yd = 1;
      break;
    case KEYBOARD_KEY_SPACE:
      if (selectedUnit != null) {
        Unit.State unitState = selectedUnit.getState();
        switch (unitState) {
        case MOVING:
          if (player.getUnits().contains(selectedUnit)) {
            selectedUnit.flipSelected();
            activeCursor.setSelectedUnit(null);
          }
          
          break;
        case ATTACKING:
          if (selectedUnit != null && unitAt == selectedUnit && unitAt.gx() == activeCursor.gx() && unitAt.gy() == activeCursor.gy()) {
            selectedUnit.flipSelected();
            activeCursor.setSelectedUnit(null);
            break;
          }
          if (unitAt != null && !player.getUnits().contains(unitAt)) {
            selectedUnit.attack(unitAt);
            activeCursor.setSelectedUnit(null);
          }
          break;
        }
      } else if (unitAt != null && unitAt.gx() == activeCursor.gx() && unitAt.gy() == activeCursor.gy() && unitAt.getState() != State.DONE) {
        activeCursor.setSelectedUnit(unitAt);
        unitAt.flipSelected();
      }

      return;
    case KEYBOARD_KEY_E:
      if (selectedUnit != null) return;
      player.getUnits().stream().forEach(u -> u.reset());
      activePlayer = (activePlayer + 1) % players.size();
      return;
    case KEYBOARD_KEY_C:
      if (selectedUnit != null) {
        switch (selectedUnit.getState()) {
        case MOVING:
          IPoint prev = selectedUnit.undoMove();
          activeCursor.setPos(prev.gx(), prev.gy());
          selectedUnit.cancel(State.IDLE);
          activeCursor.setSelectedUnit(null);
          break;
        case ATTACKING:
          activeCursor.setPos(selectedUnit.gx(), selectedUnit.gy());
          selectedUnit.cancel(State.ATTACKING);
          activeCursor.setSelectedUnit(null);
          break;
        }
      }
      return;
    case KEYBOARD_KEY_F:
      if (selectedUnit != null && selectedUnit.getState() != State.ATTACKING) {
        if (activeCursor.togglePeeking()) {
          selectedUnit.peekAttack();
        } else {
          selectedUnit.cancelPeek();
        }
      }
      return;
    case KEYBOARD_KEY_U:
      if (activeCursor.isPeeking()) return;
      
      if (selectedUnit != null) {
        IPoint prev = selectedUnit.undoMove();
        if (prev != null) {
          activeCursor.setPos(prev.gx(), prev.gy());
        }
      }
      return;
    case KEYBOARD_KEY_1:
      if (selectedUnit != null && selectedUnit.gx() == x && selectedUnit.gy() == y) {
        selectedUnit.setActiveAttack(0);
      }
      return;
    case KEYBOARD_KEY_2:
      if (selectedUnit != null && selectedUnit.gx() == x && selectedUnit.gy() == y) {
        selectedUnit.setActiveAttack(1);
      }
      return;
    default:
      break;
    }
    
    if (selectedUnit != null && selectedUnit.getState() == Unit.State.MOVING && !selectedUnit.move(xd, yd)) {
      return;
    }
    if (selectedUnit != null && selectedUnit.getState() == Unit.State.ATTACKING && !selectedUnit.isReachable(x+xd, y+yd)) {
      return;
    }
    if (activeCursor.isPeeking()) return;
    
    activeCursor.move(xd, yd);
  }
  
  public Board getBoard() {
    return board;
  }

  public int getActivePlayer() {
    return activePlayer;
  }

  public List<Player> getPlayers() {
    return players;
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
