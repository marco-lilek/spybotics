package entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import config.UnitConfig;
import entity.Board;
import entity.Cursor;
import entity.Entity;
import entity.unit.Unit;
import screen.MatchScreen;
import screen.Screen;
import util.JSONLoader;
import util.communicator.CallbackNotifier;
import util.communicator.Message;

public abstract class Player extends Entity {
  
  protected Set<Unit> units;
  
  public Player(Board board, Screen screen) {
    units = new TreeSet<Unit>();
    addListener(screen.getName(), screen);
  }
  
  @Override
  public void redraw(List<Graphics2D> g) {
    for (Iterator<Unit> it = units.iterator(); it.hasNext(); ) {
      Unit activeUnit = it.next();
      activeUnit.redraw(g);
    }
  }
  
  public Set<Unit> getUnits() {
    return units;
  }
  
  protected void finishTurn() {
    notifyListeners(Message.PLAYER_TURN_COMPLETE);
  }

  public void own(Unit u) {
    units.add(u);
    u.setOwner(this);
  }

  public void disown(Unit unit) {
    units.remove(unit);
  }

}
