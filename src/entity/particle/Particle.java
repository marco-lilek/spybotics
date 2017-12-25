package entity.particle;

import java.awt.Graphics;

public class Particle implements Comparable<Object> {
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
  
  public Particle(int x, int y, double xv, double yv, double xa, double ya, int ticksUntilDeath) {
    init(x,y,xv,yv,xa,ya,ticksUntilDeath);
  }
  
  public boolean update() {
    x += Math.round(xv);
    y += Math.round(yv);
    xv += xa;
    yv += ya;
    return --ticksUntilDeath > 0;
  }

  @Override
  public int compareTo(Object arg0) {
    return Integer.compare(this.hashCode(), arg0.hashCode()); 
  }

  public int gx() {
    return x;
  }
  
  public int gy() {
    return y;
  }
}