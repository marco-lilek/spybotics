package entity.particle;

import java.awt.Graphics;
import java.util.Set;
import java.util.TreeSet;

import entity.Entity;

public class FireworksEmitter extends ParticleEmitter {

  private int particleLife;
  private double initialParicleSpeed, particleSpeedMaxDeviation;
  private int x, y;
  
  public FireworksEmitter(
      int x, 
      int y, 
      int maxNumParticles,
      int maxReleasesPerTick, 
      int particleLife, 
      double initialParicleSpeed,
      double particleSpeedMaxDeviation, 
      ParticleDrawer particleDrawer
    ) {
    super(maxNumParticles, maxReleasesPerTick, particleDrawer);
    this.x = x;
    this.y = y;
    this.particleLife = particleLife;
    this.initialParicleSpeed = initialParicleSpeed;
    this.particleSpeedMaxDeviation = particleSpeedMaxDeviation;
  }

  @Override
  protected Particle createParticle() {
    double direction = Math.random() * Math.PI * 2;
    return new FireworksParticle(x, y, direction, initialParicleSpeed + Math.random() * particleSpeedMaxDeviation, particleLife);
  }



}
