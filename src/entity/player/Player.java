package entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
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
    Color color = new Color(ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256),ThreadLocalRandom.current().nextInt(0, 256));
    units = new TreeSet<Unit>();
    addListener(screen.getName(), screen);
  }
  
  @Override
  public void redraw(Graphics g) {
    for (Iterator<Unit> it = units.iterator(); it.hasNext(); ) {
      Unit activeUnit = it.next();
      activeUnit.redraw(g);
    }
  }
  
  protected Set<Unit> getUnits() {
    return units;
  }
  
  protected void finishTurn() {
    notifyListeners(Message.PLAYER_TURN_COMPLETE);
  }

}
