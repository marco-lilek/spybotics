package screen;

import java.awt.Graphics;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import entity.Entity;
import entity.particle.FireworksEmitter;
import entity.particle.Particle;
import entity.particle.ParticleDrawer;

public class TestScreen extends GameScreen {
  
  private Set<Entity> entities;
  
  TestScreen(ScreenType type, Map<String, String> globalGameConfig) {
    super(type);
    entities = new TreeSet<Entity>();
    entities.add(new FireworksEmitter(200,200,50, 2, 60, 2, 1, new ParticleDrawer() {
      private int w = 5;
      @Override
      public void draw(Graphics g, Particle p) {
        g.drawRect(p.gx() - w / 2, p.gy() - w / 2, w, w);
      }
    }));
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
