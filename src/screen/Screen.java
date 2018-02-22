package screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import core.Game;
import entity.Entity;
import util.communicator.CallbackListener;
import util.communicator.CallbackNotifier;
import util.communicator.Communicator;
import util.communicator.Message;

public abstract class Screen extends Communicator {
  
  private ArrayList<Entity> entities;
  
  Screen(Game game) {
    addListener(game.getName(), game);
    entities = new ArrayList<Entity>();
  }
  
  public void tick() {
    for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
      it.next().tick();
    }
  }
  
  public void redraw(List<Graphics2D> g) {
    for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
      it.next().redraw(g);
    }
  }

  protected ArrayList<Entity> getEntities() {
    return entities;
  }
}
