package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import core.Game;
import entity.Entity;
import util.communicator.CallbackListener;
import util.communicator.CallbackNotifier;
import util.communicator.Communicator;
import util.communicator.Message;

public abstract class Screen extends Communicator {
  
  private Set<Entity> entities;
  
  Screen(Game game) {
    addListener(game.getName(), game);
    entities = new TreeSet<Entity>();
  }
  
  public void tick() {
    for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
      it.next().tick();
    }
  }
  
  public void redraw(Graphics g) {
    for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
      it.next().redraw(g);
    }
  }

  protected Set<Entity> getEntities() {
    return entities;
  }
}
