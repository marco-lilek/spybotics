package entity.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import config.UnitConfig;
import core.keyboard.Key;
import entity.Board;
import entity.Cursor;
import entity.Entity;
import entity.unit.Unit;
import screen.MatchScreen;
import screen.Screen;
import util.CallbackNotifier;
import util.JSONLoader;

public abstract class Player extends Entity {
  protected Unit unit; // TODO: just temp for experimenting
  private final Color color;
  
  public Player(Board board, Screen screen) {
    color = new Color(ThreadLocalRandom.current().nextInt(0, 256), ThreadLocalRandom.current().nextInt(0, 256),ThreadLocalRandom.current().nextInt(0, 256));
    unit = new Unit(0, 0, board, JSONLoader.getLoader().loadJSONFromFile("config/test_unit.json", UnitConfig.class), color);
    addListener(screen);
  }

  public void handleKeyStroke(Key key) {
  }

  // TODO: remove block BEGIN
  @Override
  public void redraw(Graphics g) {
    unit.redraw(g);
  }

  @Override
  public void tick() {
  }
  // TODO: remove block END
  
  protected void finishTurn() {
    notifyListeners(true);
  }

  public abstract void startTurn();
}
