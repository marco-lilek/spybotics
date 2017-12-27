package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import core.keyboard.KeyboardManager;

public class GameWindowPanel extends JPanel implements ActionListener {  
  
  private Game game;
  private KeyboardManager keyboardManager;
  private Graphics bufferGraphics;
  private Image offscreen;
  
  @Override
  public Dimension getPreferredSize() {
    return Game.getDimensions();
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    if (game != null && bufferGraphics != null) {
      game.redraw(bufferGraphics);
      g.drawImage(offscreen, 0, 0, this);
      Toolkit.getDefaultToolkit().sync();
    }
  }
  
  public void init() {
    // Initialize image
    offscreen = createImage(Game.getScreenWidth(), Game.getScreenHeight());
    bufferGraphics = offscreen.getGraphics(); 
    
    keyboardManager = new KeyboardManager(this.getInputMap(), this.getActionMap());
    game = new Game(keyboardManager);
    
    
    // Tick, 60fps
    Timer tickTimer = new Timer(20, this);
    tickTimer.start();
  }
  
  public static void main(String[] args) {
    System.out.println("Starting game");
    
    JFrame frame = new JFrame();
    GameWindowPanel gameWindowPanel = new GameWindowPanel();
    frame.getContentPane().add(gameWindowPanel);
    frame.pack();
    frame.setVisible(true);
    
    gameWindowPanel.init();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    game.tick();
    this.repaint();
  }
}
