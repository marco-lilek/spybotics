package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import config.UnitConfig;
import entity.Unit.State;
import entity.painter.UnitPainter;
import entity.player.Player;
import screen.MatchScreen;
import screen.Screen;
import entity.Board;
import util.Canvas;
import util.Direction;
import util.IPoint;

public class Unit extends Entity {

  public enum State {
    IDLE,
    MOVING,
    PEEKING,
    ATTACKING,
    DONE
  }
  
  private final UnitConfig config;
  private final MatchScreen screen;
  private final UnitPainter painter;
  
  private State prevState;
  private State state;
  private boolean isSelected;
  private int selectedAttack;
  private int numRemainingMoves;
  private int x,y;
  private Player owner;
  
  private IPoint prev;
  private Map<IPoint,Boolean> prevTail;
  
  private Map<IPoint,Boolean> tail;
  
  public Unit(MatchScreen screen, UnitConfig config, int x, int y) {
    super(screen);
    this.config = config;
    this.screen = screen;
    this.painter = new UnitPainter(this);
    
    prevState = State.IDLE;
    state = State.IDLE;
    isSelected = false;
    selectedAttack = 0;
    this.x = x;
    this.y = y;
    tail = new LinkedHashMap<IPoint,Boolean>();
    prevTail = new LinkedHashMap<IPoint,Boolean>();
    
    numRemainingMoves = 5;
    screen.getBoard().addUnitAt(x, y, this);
  }
  
  private void removeTail() {
    if (tail.isEmpty()) {
      screen.getBoard().removeUnitAt(x, y);
      owner.disown(this);
      screen.removeEntity(this);
      return;
    }
    IPoint backOfTail = tail.keySet().iterator().next();
    removeTail(backOfTail.gx(), backOfTail.gy());
  }
  
  private void removeTail(int x, int y) {
    tail.remove(new IPoint(x, y));
    screen.getBoard().removeUnitAt(x, y);
  }
  
  private void addTail(int x, int y) {
    tail.put(new IPoint(x, y), true);
    screen.getBoard().addUnitAt(x, y, this);
  }
  
  public boolean move(int xd, int yd) {
    if (numRemainingMoves == 0) return false;
    
    int xn = x + xd;
    int yn = y + yd;
    Unit unitAtN = screen.getBoard().getUnitAt(xn, yn);
    if (!screen.getBoard().isOpenAt(xn, yn) || (unitAtN != this && unitAtN != null)) {
      return false;
    }
    
    addTail(x, y);
    if (tail.size() > config.max_tail_length) {
      removeTail();
    }
    tail.remove(new IPoint(xn, yn));
    screen.getBoard().addUnitAt(xn, yn, this);
    
    x = xn;
    y = yn;
    System.out.println(new IPoint(x, y));
    numRemainingMoves--;
    painter.update();
    return true;
  }

  public IPoint undoMove() {
    if (state != State.MOVING) return null; // TODO: consider changing to exception-based model
    
    for (Iterator<IPoint> it = tail.keySet().iterator(); it.hasNext();) {
      IPoint p = it.next();
      screen.getBoard().removeUnitAt(p.gx(), p.gy());
    }
    
    for (Iterator<IPoint> it = prevTail.keySet().iterator(); it.hasNext();) {
      IPoint p = it.next();
      screen.getBoard().addUnitAt(p.gx(),p.gy(), this);
    }
    
    tail.clear();
    tail.putAll(prevTail);
    screen.getBoard().removeUnitAt(x, y);
    x = prev.gx();
    y = prev.gy();
    screen.getBoard().addUnitAt(x, y, this);
    numRemainingMoves = 5;
    painter.update(); // TODO: initial remaining moves
    
    return prev;
  }

  public int gx() {
    // TODO Auto-generated method stub
    return x;
  }
  
  public int gy() {
    // TODO Auto-generated method stub
    return y;
  }
  
  public Map<IPoint,Boolean> getTail() {
    return tail;
  }
  
  public UnitConfig getConfig() {
    return config;
  }
  
  public void cancel(State newState) {
    state = newState;
    isSelected = false;
    painter.update();
  }
  
  public void flipSelected() {
    if (!isSelected && state == State.IDLE) {
      state = State.MOVING;
      prevTail.clear();
      prevTail.putAll(tail);
      prev = new IPoint(x,y);
      painter.update();
    } else if (isSelected) {
      switch (state) {
      case MOVING:
        state = State.ATTACKING;
        numRemainingMoves = 0;
        painter.update();
        break;
      case ATTACKING:
        state = State.DONE;
        numRemainingMoves = 0;
        painter.update();
        break;
      }
      
    } 
    
    isSelected = !isSelected;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public State getState() {
    return state;
  }

  public void attack(Unit other) {
    screen.getBoard().attack(other, config.attacks.get(selectedAttack).damage);
    flipSelected();
  }
  
  public void damage(int amount) {
    while (amount-- >= 0) {
      removeTail();
    }
  }

  public boolean isReachable(int xn, int yn) {
    return painter.getReachable().contains(new IPoint(xn, yn));
  }

  public void setOwner(Player player) {
    owner = player;
  }
  
  public Player getOwner() {
    return owner;
  }
  
  public void setActiveAttack(int attackId) {
    if (state == State.DONE) return;
    
    if (attackId >= 0 && attackId < config.attacks.size()) {
      selectedAttack = attackId;
    }
    painter.update();
  }

  public int getRemainingMoves() {
    return numRemainingMoves;
  }

  public int getAttackRange() {
    return config.attacks.get(selectedAttack).range;
  }

  @Override
  public void redraw(List<Graphics2D> g) {
    painter.redraw(g);
  }

  public int getSelectedAttack() {
    return selectedAttack;
  }

  public void peekAttack() {
    prevState = state;
    state = State.PEEKING;
    painter.update();
  }

  public void cancelPeek() {
    state = prevState;
    painter.update();
  }

  public void reset() {
    state = State.IDLE;
    numRemainingMoves = 5;
    screen.getBoard().addUnitAt(x, y, this);
  }

  public int sprite() {
    // TODO Auto-generated method stub
    return 0;
  }
}

/*
public class Unit extends Entity<UnitPainter> {
  

  
  private final Board board;
  private final UnitConfig config;
  private final Player player;
  
  private State state;
  private int x,y;
  private int numRemainingMoves;
  private Set<IPoint> reachableTiles;
  private Map<IPoint,Boolean> tail;
  
  public Unit(int x, int y, Board board, UnitConfig config, Player player) {
    this.x = x;
    this.y = y;
    this.config = config;
    this.player = player;
    
    state = State.DONE;
    tail = new LinkedHashMap<IPoint,Boolean>();
    reachableTiles = new TreeSet<IPoint>();
    
    this.board = board;
    this.board.addUnitAt(this.x, this.y, this);
  }

  @Override
  public void redraw(Graphics g) {
    int idx = 0;
    for (Iterator<IPoint> it = tail.keySet().iterator(); it.hasNext(); idx++) {
      IPoint p = it.next();
      Canvas drawCanvas = board.getTileDrawCanvas(p.gx(), p.gy());
      if (drawCanvas != null) {
        drawTailTile(g, drawCanvas, idx + 1);
      }
    }
    
    Canvas headDrawCanvas = board.getTileDrawCanvas(x, y);
    if (headDrawCanvas != null) {
      drawHeadTile(g, board.getTileDrawCanvas(x, y));
    }
    
    for (Iterator<IPoint> it = reachableTiles.iterator(); it.hasNext(); ) {
      IPoint p = it.next();
      Canvas drawCanvas = board.getTileDrawCanvas(p.gx(), p.gy());
      if (drawCanvas != null) {
        g.setColor(new Color(12,12,12));
        g.fillRect(drawCanvas.topLeft.gx() + 8, drawCanvas.topLeft.gy() + 8, drawCanvas.dimensions.gx() - 16, drawCanvas.dimensions.gy() - 16);
        g.setColor(new Color(0,0,0));
      }
    }
    
    for (Iterator<IPoint> it = board.getAdjacentTiles(new IPoint(x, y)).iterator(); it.hasNext();) {
      IPoint p = it.next();
      Canvas drawCanvas = board.getTileDrawCanvas(p.gx(), p.gy());
      if (drawCanvas != null) {
        g.setColor(new Color(255,12,12));
        g.fillRect(drawCanvas.topLeft.gx() + 8, drawCanvas.topLeft.gy() + 8, drawCanvas.dimensions.gx() - 16, drawCanvas.dimensions.gy() - 16);
        g.setColor(new Color(0,0,0));
      }
    }
  }


  private Canvas drawTile(Graphics g, int x, int y) {
    Canvas drawCanvas = board.getTileDrawCanvas(x, y);
    if (drawCanvas == null) return null;
    
    g.setColor(config.getRGB());
    IPoint topLeft = drawCanvas.topLeft;
    IPoint dimensions = drawCanvas.dimensions;
    IPoint shadowOffset = board.getShadowOffset();
    g.setColor(config.getRGB().darker());
    g.fillRect(shadowOffset.gx() + topLeft.gx(), shadowOffset.gy() + topLeft.gy(), dimensions.gx(), dimensions.gy());
    g.setColor(config.getRGB());
    g.fillRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
    g.setColor(player.getColor());
    g.drawRect(topLeft.gx(), topLeft.gy(), dimensions.gx(), dimensions.gy());
    g.setColor(new Color(0,0,0));
    return drawCanvas;
  }
  
  public void drawTailTile(Graphics g, int idx) {
    IPoint topLeft = drawCanvas.topLeft;
    drawTile(g, drawCanvas);
    g.setColor(config.getRGB().darker().darker());
    g.drawString(String.valueOf(idx), topLeft.gx() + 6, topLeft.gy() + 16);
    g.setColor(new Color(0,0,0));
  }
  
  public void drawHeadTile(Graphics g, ) {
    drawTile(g, drawCanvas);
  }
  
  @Override
  public void tick() {
    // TODO: move out control logic to the screen w/ forwarding
    
  }
  
  private void setReachableTiles(int availMoves) {
    reachableTiles = board.getAdjacentTiles(new IPoint(x, y), availMoves);
  }
  
  public void resetReachableTiles() {
    numRemainingMoves = config.getMovementSpeed();
    setReachableTiles(numRemainingMoves);
  }
  
  private void removeTail() {
    if (tail.isEmpty()) {
      // TODO: delete self
    }
    IPoint backOfTail = tail.keySet().iterator().next();
    removeTail(backOfTail.gx(), backOfTail.gy());
  }
  
  private void removeTail(int x, int y) {
    tail.remove(new IPoint(x, y));
    board.removeUnitFromTile(x, y);
  }
  
  private void addTail(int x, int y) {
    tail.put(new IPoint(x, y), true);
    board.addUnitAt(x, y, this); // TODO: bad naming
  }
  
  public void move(int xd, int yd) {
    int xn = x + xd;
    int yn = y + yd;
    if (!board.open(xn, yn) || (xn == x && yn == y) || reachableTiles.size() <= 1) {
      return;
    }
    
    addTail(x, y);
    if (tail.size() > config.max_tail_length) {
      removeTail();
    }
    tail.remove(new IPoint(xn, yn));
    board.addUnitAt(xn, yn, this);
    
    x = xn;
    y = yn;
    System.out.println(new IPoint(x, y));
    setReachableTiles(--numRemainingMoves);
  }

  public void attack(int xt, int yt) {
    // TODO: just temp
    int xtt = xt + x;
    int ytt = yt + y;
    board.attack(xtt, ytt);
    isAttacking = false;
  }

  public void damage(int amount) {
    while (amount-- >= 0) {
      popTail();
    }
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public void callbackRecv(Message msg) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

}
*/
