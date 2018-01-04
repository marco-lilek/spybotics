package core.keyboard;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import util.CallbackNotifier;

public class KeyboardManager extends CallbackNotifier<Key> {
  class ForwardKeyToListenerAction extends AbstractAction {
    
    private Key key;
    private KeyboardManager km;
    ForwardKeyToListenerAction(Key key, KeyboardManager km) {
      this.key = key;
      this.km = km;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      km.notifyListeners(key);
    } 
  }
  
  // TODO: cons of maybe needing to double each entry for key up might outweigh pros of having enum
  private static final Map<String, Key> keyStrokeKeyCodeMap;
  static {
    keyStrokeKeyCodeMap = new HashMap<String, Key>();
    keyStrokeKeyCodeMap.put("SPACE", Key.SPACE);
    keyStrokeKeyCodeMap.put("LEFT", Key.LEFT);
    keyStrokeKeyCodeMap.put("RIGHT", Key.RIGHT);
    keyStrokeKeyCodeMap.put("UP", Key.UP);
    keyStrokeKeyCodeMap.put("DOWN", Key.DOWN);
    keyStrokeKeyCodeMap.put("typed e", Key.E);
    keyStrokeKeyCodeMap.put("typed a", Key.A);
  }
  
  
  public KeyboardManager(InputMap inputMap, ActionMap actionMap) {
    for (Iterator<Map.Entry<String, Key>> entryIter = keyStrokeKeyCodeMap.entrySet().iterator(); entryIter.hasNext();) {
      Map.Entry<String, Key> entry =  entryIter.next();
      inputMap.put(KeyStroke.getKeyStroke(entry.getKey()), entry.getKey());
      actionMap.put(entry.getKey(), new ForwardKeyToListenerAction(entry.getValue(), this));
    }
  }
}
