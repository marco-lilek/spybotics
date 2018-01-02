package entity;

import java.awt.Graphics;

import config.UnitConfig;
import core.keyboard.Key;
import entity.unit.Unit;
import util.JSONLoader;

public class Player extends Entity {
  private Cursor cursor;
  private Unit unit;
  private boolean controlCursor;
  
  public Player(Board board) {
    controlCursor = true;
    cursor = new Cursor(board);
    unit = new Unit(0, 0, board, JSONLoader.getLoader().loadJSONFromFile("config/test_unit.json", UnitConfig.class));
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
      controlCursor = !controlCursor;
      unit.resetReachableTiles();
      break;
    }
    
    if (controlCursor) {
      cursor.move(xd, yd);
    } else {
      unit.move(xd, yd);
    }
  }

  @Override
  public void redraw(Graphics g) {
    cursor.redraw(g);
    unit.redraw(g);
  }

  @Override
  public void tick() {
    // TODO Auto-generated method stub
    
  }
}
