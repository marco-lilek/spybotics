package core;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import util.communicator.CallbackNotifier;
import util.communicator.Message;

public class KeyboardManager extends CallbackNotifier<Message> {
  class ForwardMsgToListenerAction extends AbstractAction {
    
    private Message msg;
    private KeyboardManager km;
    ForwardMsgToListenerAction(Message msg, KeyboardManager km) {
      this.msg = msg;
      this.km = km;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      km.notifyListeners(msg);
    } 
  }
  
  // TODO: cons of maybe needing to double each entry for key up might outweigh pros of having enum
  private static final Map<String, Message> keyStrokeMsgMap;
  static {
    keyStrokeMsgMap = new HashMap<String, Message>();
    keyStrokeMsgMap.put("SPACE", Message.KEYBOARD_KEY_SPACE);
    keyStrokeMsgMap.put("LEFT", Message.KEYBOARD_KEY_LEFT);
    keyStrokeMsgMap.put("RIGHT", Message.KEYBOARD_KEY_RIGHT);
    keyStrokeMsgMap.put("UP", Message.KEYBOARD_KEY_UP);
    keyStrokeMsgMap.put("DOWN", Message.KEYBOARD_KEY_DOWN);
    //keyStrokeMsgMap.put("typed e", Key.E);
    keyStrokeMsgMap.put("typed a", Message.KEYBOARD_KEY_A);
  }
  
  
  public KeyboardManager(InputMap inputMap, ActionMap actionMap) {
    for (Iterator<Map.Entry<String, Message>> entryIter = keyStrokeMsgMap.entrySet().iterator(); entryIter.hasNext();) {
      Map.Entry<String, Message> entry =  entryIter.next();
      inputMap.put(KeyStroke.getKeyStroke(entry.getKey()), entry.getKey());
      actionMap.put(entry.getKey(), new ForwardMsgToListenerAction(entry.getValue(), this));
    }
  }
}
