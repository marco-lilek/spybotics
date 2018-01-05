package entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import config.UnitConfig;
import core.keyboard.Key;
import entity.Board;
import entity.Cursor;
import entity.Entity;
import entity.unit.Unit;
import screen.MatchScreen;
import screen.Screen;
import util.JSONLoader;
import util.communicator.CallbackNotifier;

public abstract class Player extends Entity {
  public static final String TURN_COMPLETE = "TURN_COMPLETE";
  
  protected Set<Unit> units;
  
  public Player(Board board, Screen screen) {
    Color color = new Color(ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256),ThreadLocalRandom.current().nextInt(0, 256));
    units = new TreeSet<Unit>();
    units.add(new Unit(0, 0, board, JSONLoader.getLoader().loadJSONFromFile("config/test_unit.json", UnitConfig.class), this));
    addListener(screen);
  }

  public void handleKeyStroke(Key key) {
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
    notifyListeners(TURN_COMPLETE);
  }

  public abstract void startTurn();

  public Color getColor() {
    // TODO Auto-generated method stub
    return null;
  }
}
