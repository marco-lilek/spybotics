package entity;

import java.awt.Graphics;
import java.util.Set;
import java.util.TreeSet;

public class ParticleEmitter extends Entity {

  private class Particle implements Comparable<Object> {
    private int x, y;
    private double xv, yv, xa, ya;
    private int ticksUntilDeath;
    
    public void init(int x, int y, double xv, double yv, double xa, double ya, int ticksUntilDeath) {
      this.x = x;
      this.y = y;
      this.xv = xv;
      this.yv = yv;
      this.xa = xa;
      this.ya = ya;
      this.ticksUntilDeath = ticksUntilDeath;
    }
    
    public Particle(int x, int y, double xs, double ys, double xa, double ya, int ticksUntilDeath) {
      init(x,y,xs,ys,xa,ya,ticksUntilDeath);
    }
    

    public void redraw(Graphics g) {
      g.drawRect(x, y, 2, 2);
    }
    
    public boolean update() {
      x += xv;
      y += yv;
      xv += xa;
      yv += ya;
      return --ticksUntilDeath > 0;
    }

    @Override
    public int compareTo(Object arg0) {
      return Integer.compare(this.hashCode(), arg0.hashCode()); 
    }
  }
  
  private class SlowDownParticle extends Particle {

    public SlowDownParticle(int x, int y, double direction, double initialSpeed, int ticksUntilDeath) {
      super(0, 0, 0, 0, 0, 0, 0);
      double xv = Math.sin(direction) * initialSpeed;
      double yv = Math.cos(direction) * initialSpeed;
      
      double xa = (xv / (ticksUntilDeath - 30)) * -1;
      double ya = (yv / (ticksUntilDeath - 30)) * -1;
      init(x,y,xv,yv,xa,ya, ticksUntilDeath);
    }
  }
  
  private Set<Particle> particles;
  private int maxNumParticles;
  private int x, y;
  
  public ParticleEmitter(int numParticles) {
    x = 200;
    y = 200;
    particles = new TreeSet<Particle>();
    this.maxNumParticles = numParticles;
  }

  @Override
  public void redraw(Graphics g) {
    for (Particle p : particles) {
      p.redraw(g);
    }
  }

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
    for (int i = 0; i < maxNumParticles - numActiveParticles; i++) {
      double direction = Math.random() * Math.PI * 2;
      particles.add(new SlowDownParticle(x, y, direction, 5, 60));
    }
  }

}
