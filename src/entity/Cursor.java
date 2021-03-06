package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import core.Game;
import entity.Unit.State;
import entity.painter.CursorPainter;
import entity.player.Player;
import screen.MatchScreen;
import screen.Screen;
import util.Canvas;
import util.Direction;
import util.IPoint;

public class Cursor extends Entity {
  
  private final CursorPainter painter;
  private final Player owner;
  private Unit selectedUnit;
  private int x,y;
  private boolean peeking;
  
  public Cursor(MatchScreen screen, Player p) {
    super(screen);
    this.owner = p;
    this.painter = new CursorPainter(this);
    x = 0; y = 0;
  }

  public int gx() {return x;}
  public int gy() {return y;}
  
  @Override
  public void redraw(List<Graphics2D> g) {
    painter.redraw(g);
  }
  
  public Unit getSelectedUnit() { return selectedUnit; }
  public void setSelectedUnit(Unit u) { selectedUnit = u; }

  public void move(int xd, int yd) {
    MatchScreen screen = (MatchScreen)this.getScreen();
    int xn = x + xd;
    int yn = y + yd;
    if (!screen.getBoard().isInBounds(xn, yn)) {
      return;
    }
    x = xn;
    y = yn;
  }

  public void setPos(int gx, int gy) {
    x = gx;
    y = gy;
  }

  public boolean togglePeeking() {
    peeking = !peeking;
    return peeking;
  }

  public boolean isPeeking() {
    return peeking;
  }

  public Player owner() {
    return owner;
  }
  
/*
  
  private final Board board;
  
  private final Player player;
  private final CursorPainter painter;
  
  public Cursor(Screen screen, Board board, CursorPainter painter, Player player) {
    this.player = player;
    this.board = board;
    this.painter = painter;
    painter.attach(this);
  }

  private void move(int xd, int yd) {
    int xn = x + xd;
    int yn = y + yd;
    if (!board.isInBounds(xn, yn)) {
      return;
    }
    x = xn;
    y = yn;
  }

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
      Unit unitUnderMe = board.getUnitAtTile(x, y);
      if (activeUnit.getPlayer() == player) {
        unitUnderControl = unitUnderMe;
      }
      return;
    case A:
      return;
    }
    
    move(xd, yd);
    if (unitUnderControl != null) {
      unitUnderControl.move(xd, yd);
    }
  }

  public int gx() {
    return x;
  }
  
  public int gy() {
    return y;
  }

  @Override
  public void handleKeyboardMsg(Message msg) {
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
      Unit unitAt = board.getUnitAt(x, y);
      if (selectedUnit != null) {
        Unit.State unitState = selectedUnit.getState();
        switch (unitState) {
        case MOVING:
          selectedUnit.flipSelected(); // TODO
          selectedUnit = null;
          break;
        case ATTACKING:
          if (selectedUnit != null && unitAt == selectedUnit) {
            selectedUnit.flipSelected(); // TODO
            selectedUnit = null;
            break;
          }
          if (unitAt != null && !player.getUnits().contains(unitAt)) {
            selectedUnit.attack(unitAt);
          }
          break;
        }
      } else if (unitAt != null && player.getUnits().contains(unitAt)) {
        selectedUnit = unitAt;
        selectedUnit.flipSelected();
      }

      return;
    case KEYBOARD_KEY_U:
      if (selectedUnit != null) {
        IPoint prev = selectedUnit.undoMove();
        if (prev != null) {
          x = prev.gx(); y = prev.gy();
        }
      }
      return;
    case KEYBOARD_KEY_1:
      if (selectedUnit != null && selectedUnit.gx() == x && selectedUnit.gy() == y && selectedUnit.getState() == State.ATTACKING) {
        selectedUnit.setActiveAttack(0);
      }
      return;
    case KEYBOARD_KEY_2:
      if (selectedUnit != null && selectedUnit.gx() == x && selectedUnit.gy() == y && selectedUnit.getState() == State.ATTACKING) {
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
    move(xd, yd);
  }

  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void redraw(List<Graphics2D> g) {
    painter.redraw(g);
  }*/
}
