package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Set;

import config.UnitConfig;
import core.keyboard.Key;
import core.sprite.SpriteManager;
import entity.Board;
import entity.unit.Unit;
import util.JSONLoader;

public class MatchScreen extends Screen {

  private Unit testPlayer;
  private Board board;
  
  MatchScreen(ScreenType type) { // TODO: load in level config 
    super(type);
    board = new Board();
    testPlayer = new Unit(0, 0, board, JSONLoader.getLoader().loadJSONFromFile("config/test_unit.json", UnitConfig.class));
  }

  @Override
  public ScreenType tick() {
    testPlayer.tick();
    return this.getType();
  }

  @Override
  public void redraw(Graphics g) {
    board.redraw(g);
    testPlayer.redraw(g);

    SpriteManager.getManager().drawSheet(g);
  }

  @Override
  public void handleKeyStroke(Key key) {
    switch (key) {
    case LEFT:
        testPlayer.move(-1, 0);
        break;
    case RIGHT:
      testPlayer.move(1, 0);
      break;
    case UP:
      testPlayer.move(0, -1);
      break;
    case DOWN:
      testPlayer.move(0, 1);
      break;
    }
  }

}
