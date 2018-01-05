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

import com.google.gson.Gson;

import entity.Board;
import util.IPoint;

public class GameWindow extends JPanel implements ActionListener {
  
  private static final int SCREEN_WIDTH = 800;
  private static final int SCREEN_HEIGHT = 450;
  
  private Game game;
  private KeyboardManager keyboardManager;
  private Graphics bufferGraphics;
  private Image offscreen;
  
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    if (bufferGraphics != null) {
      bufferGraphics.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
      game.redraw(bufferGraphics);
      
      g.drawImage(offscreen, 0, 0, this);
      Toolkit.getDefaultToolkit().sync();
    }
  }
  
  GameWindow() {
    super();
    keyboardManager = new KeyboardManager(this.getInputMap(), this.getActionMap());
    game = new Game(keyboardManager);
  }
  
  public void init() {
    // Initialize image
    offscreen = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
    bufferGraphics = offscreen.getGraphics(); 
    
    // Tick, 60fps
    Timer tickTimer = new Timer(20, this);
    tickTimer.start();
  }
  
  public static void main(String[] args) {
    System.out.println("Starting game");
    System.out.println(System.getProperty("user.dir"));
    
    JFrame frame = new JFrame();
    GameWindow gameWindowPanel = new GameWindow();
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
