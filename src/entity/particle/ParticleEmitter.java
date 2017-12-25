package entity.particle;

import java.awt.Graphics;
import java.util.Set;
import java.util.TreeSet;

import entity.Entity;

public abstract class ParticleEmitter extends Entity {

  private Set<Particle> particles;
  private ParticleDrawer drawer;
  private int maxNumParticles, maxReleasesPerTick;
  
  public ParticleEmitter(int maxNumParticles, int maxReleasesPerTick, ParticleDrawer drawer) {
    particles = new TreeSet<Particle>();
    this.maxNumParticles = maxNumParticles;
    this.maxReleasesPerTick = maxReleasesPerTick;
    this.drawer = drawer;
  }

  @Override
  public void redraw(Graphics g) {
    for (Particle p : particles) {
      drawer.draw(g, p);
    }
  }

  protected abstract Particle createParticle();
  
  @Override
  public void tick(Set<String> pressedKeys) {
    Set<Particle> toRemove = new TreeSet<Particle>();
    for (Particle particle : particles) {
      if (!particle.update()) {
        toRemove.add(particle);
      }
    }
    
    for (Particle p : toRemove) {
      particles.remove(p);
    }
    
    int numActiveParticles = particles.size();
    for (int i = 0; i < Math.min(maxReleasesPerTick, maxNumParticles - numActiveParticles); i++) {
      particles.add(createParticle());
    }
  }

}
