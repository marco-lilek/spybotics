package entity.particle;

public class FireworksParticle extends Particle {

  public FireworksParticle(int x, int y, double direction, double initialSpeed, int ticksUntilDeath) {
    super(0, 0, 0, 0, 0, 0, 0);
    double xv = Math.sin(direction) * initialSpeed;
    double yv = Math.cos(direction) * initialSpeed;
    
    double xa = (xv / (ticksUntilDeath)) * -1;
    double ya = (yv / (ticksUntilDeath)) * -1;
    init(x,y,xv,yv,xa,ya, ticksUntilDeath);
  }
}