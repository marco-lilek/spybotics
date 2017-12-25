package entity.unit;

import java.awt.Graphics;
import java.util.Set;

import entity.Board;
import entity.Entity;

public class Unit extends Entity {

  private Board board;
  private int x,y;
  
  public Unit(int x, int y, Board board) {
    this.x = x;
    this.y = y;
    this.board = board;
    this.board.addUnitAt(this.x, this.y, this);
  }

  @Override
  public void redraw(Graphics g) {
    g.drawRect(board.offsetTilex(x), board.offsetTiley(y), board.getTilew(), board.getTileh());
  }

  @Override
  public void tick(Set<String> pressedKeys) {
    // TODO: move out control logic to the screen w/ forwarding
    
  }

}
