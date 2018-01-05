package screen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import core.Game;
import core.sprite.SpriteManager;
import entity.Entity;
import entity.particle.FireworksEmitter;
import entity.particle.Particle;
import entity.particle.ParticleDrawer;
import util.communicator.Message;

public class TestScreen extends Screen {
  
  TestScreen(Game game) {
    super(game);
/*    entities = new TreeSet<Entity>();
    entities.add(new FireworksEmitter(200,200,50, 2, 60, 0, 2, new ParticleDrawer() {
      private int w = 5;
      @Override
      public void draw(Graphics g, Particle p) {
        g.drawRect(p.gx() - w / 2, p.gy() - w / 2, w, w);
      }
    }));*/
  }

  @Override
  public void callbackRecv(Message msg) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void handleKeyStroke(Message msgFromKeyboard) {
    if (msgFromKeyboard == Message.KEYBOARD_KEY_SPACE) {
      notifyListener(Game.NAME, Message.GAME_SCREEN_MATCH);
    }
  }
}
