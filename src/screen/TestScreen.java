package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import core.keyboard.Key;
import core.sprite.SpriteManager;
import entity.Entity;
import entity.particle.FireworksEmitter;
import entity.particle.Particle;
import entity.particle.ParticleDrawer;

public class TestScreen extends Screen {
  
  private Set<Entity> entities;
  
  TestScreen(ScreenType type, Map<String, String> globalGameConfig) {
    super(type);
    entities = new TreeSet<Entity>();
    entities.add(new FireworksEmitter(200,200,50, 2, 60, 0, 2, new ParticleDrawer() {
      private int w = 5;
      @Override
      public void draw(Graphics g, Particle p) {
        g.drawRect(p.gx() - w / 2, p.gy() - w / 2, w, w);
      }
    }));
  }

  @Override
  public ScreenType tick() {
    for (Entity entity : entities) {
      entity.tick();
    }
    return this.getType();
  }

  @Override
  public void redraw(Graphics g) {
    for (Entity entity : entities) {
      entity.redraw(g);
    }
  }

  @Override
  public void handleKeyStroke(Key key) {
    // TODO Auto-generated method stub
    
  }
}
