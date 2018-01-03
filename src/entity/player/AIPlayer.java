package entity.player;

import entity.Board;
import screen.MatchScreen;
import screen.Screen;

public class AIPlayer extends Player {

  public AIPlayer(Board board, Screen screen) {
    super(board, screen);
  }

  @Override
  public void startTurn() {
    finishTurn();
  }
}
