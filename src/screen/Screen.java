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
import entity.Board;
import entity.Entity;
import util.communicator.CallbackListener;
import util.communicator.CallbackNotifier;
import util.communicator.Communicator;
import util.communicator.KeyboardMessage;

public abstract class Screen {
  
  private ArrayList<Entity> entities;
  
  Screen() {
    entities = new ArrayList<Entity>();
  }
  
  public void redraw(List<Graphics2D> g) {
    for (Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
      it.next().redraw(g);
    }
  }

  public void addEntity(Entity e) {
    entities.add(e);
  }
  
  public void removeEntity(Entity e) {
    entities.remove(e);
  }
  
  protected ArrayList<Entity> getEntities() {
    return entities;
  }

  abstract public void handleInput(KeyboardMessage msg);

}
