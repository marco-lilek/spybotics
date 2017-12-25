package screen;

import java.awt.Graphics;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import entity.Entity;
import entity.ParticleEmitter;

public class TestScreen extends GameScreen {
  
  private Set<Entity> entities;
  
  TestScreen(ScreenType type, Map<String, String> globalGameConfig) {
    super(type);
    entities = new TreeSet<Entity>();
    entities.add(new ParticleEmitter(50));
  }

  private Set<Entity> getEntities() {
    return null;
  }

  @Override
  public ScreenType tick(Set<String> pressedKeys) {
    for (Entity entity : entities) {
      entity.tick(pressedKeys);
    }
    return this.getType();
  }

  @Override
  public void redraw(Graphics g) {
    for (Entity entity : entities) {
      entity.redraw(g);
    }
  }

}
