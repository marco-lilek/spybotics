package entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import config.UnitConfig;
import entity.Board;
import entity.Cursor;
import entity.Entity;
import entity.Unit;
import screen.MatchScreen;
import screen.Screen;
import util.Colors;
import util.JSONLoader;
import util.communicator.CallbackNotifier;

public abstract class Player extends Entity {
  
  protected Set<Unit> units;
  private final Color color;
  
  public Player(MatchScreen screen) {
    super(screen);
    color = Colors.PLAYER_COLORS.get(ThreadLocalRandom.current().nextInt(0, Colors.PLAYER_COLORS.size()));
    units = new TreeSet<Unit>();
  }
  
  @Override
  public void redraw(List<Graphics2D> g) {
    for (Iterator<Unit> it = units.iterator(); it.hasNext(); ) {
      it.next().redraw(g);
    }
  }
  
  public Set<Unit> getUnits() {
    return units;
  }

  public void own(Unit u) {
    units.add(u);
    u.setOwner(this);
  }

  public void disown(Unit unit) {
    units.remove(unit);
  }

  public Color getColor() {
    return color;
  }
}
