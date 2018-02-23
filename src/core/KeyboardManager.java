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
import util.communicator.KeyboardMessage;

public class KeyboardManager extends CallbackNotifier<KeyboardMessage> {
  class ForwardMsgToListenerAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private KeyboardMessage msg;
    private KeyboardManager km;
    ForwardMsgToListenerAction(KeyboardMessage msg, KeyboardManager km) {
      this.msg = msg;
      this.km = km;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      km.notifyListeners(msg);
    } 
  }
  
  // TODO: cons of maybe needing to double each entry for key up might outweigh pros of having enum
  private static final Map<String, KeyboardMessage> keyStrokeMsgMap;
  static {
    keyStrokeMsgMap = new HashMap<String, KeyboardMessage>();
    keyStrokeMsgMap.put("SPACE", KeyboardMessage.KEYBOARD_KEY_SPACE);
    keyStrokeMsgMap.put("LEFT", KeyboardMessage.KEYBOARD_KEY_LEFT);
    keyStrokeMsgMap.put("RIGHT", KeyboardMessage.KEYBOARD_KEY_RIGHT);
    keyStrokeMsgMap.put("UP", KeyboardMessage.KEYBOARD_KEY_UP);
    keyStrokeMsgMap.put("DOWN", KeyboardMessage.KEYBOARD_KEY_DOWN);
    //keyStrokeMsgMap.put("typed e", Key.E);
    keyStrokeMsgMap.put("typed e", KeyboardMessage.KEYBOARD_KEY_E);
    keyStrokeMsgMap.put("typed a", KeyboardMessage.KEYBOARD_KEY_A);
    keyStrokeMsgMap.put("typed u", KeyboardMessage.KEYBOARD_KEY_U);
    keyStrokeMsgMap.put("typed f", KeyboardMessage.KEYBOARD_KEY_F);
    keyStrokeMsgMap.put("typed c", KeyboardMessage.KEYBOARD_KEY_C);
    keyStrokeMsgMap.put("typed 1", KeyboardMessage.KEYBOARD_KEY_1);
    keyStrokeMsgMap.put("typed 2", KeyboardMessage.KEYBOARD_KEY_2);
  }
  
  
  public KeyboardManager(InputMap inputMap, ActionMap actionMap) {
    for (Iterator<Map.Entry<String, KeyboardMessage>> entryIter = keyStrokeMsgMap.entrySet().iterator(); entryIter.hasNext();) {
      Map.Entry<String, KeyboardMessage> entry =  entryIter.next();
      inputMap.put(KeyStroke.getKeyStroke(entry.getKey()), entry.getKey());
      actionMap.put(entry.getKey(), new ForwardMsgToListenerAction(entry.getValue(), this));
    }
  }
}
