package entity;

import java.awt.Graphics;
import java.util.Set;

import com.google.gson.Gson;

import entity.painter.EntityPainter;
import util.communicator.CallbackListener;
import util.communicator.CallbackNotifier;
import util.communicator.Communicator;
import util.communicator.Message;

public abstract class Entity extends CallbackNotifier<Message> implements Comparable {

  public abstract void redraw(Graphics g); 
  
  public abstract void tick();
  
  @Override
  public int compareTo(Object arg0) {
    return Integer.compare(this.hashCode(), arg0.hashCode());
  }
  
  public void handleKeyboardMsg(Message msgFromKeyboard) {}
}
