package core;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyboardManager {
  private static final Map<String, String> keyStrokeActionMap;
  static {
    keyStrokeActionMap = new HashMap<String, String>();
    keyStrokeActionMap.put("SPACE", "space");
  }
  
  public Set<Map.Entry<String, String>> getKeyStrokeActionMap() {
    return keyStrokeActionMap.entrySet();
  }

  public Set<String> getPressedKeys() {
    // TODO Auto-generated method stub
    return null;
  }

  public void handleKeyStroke(ActionEvent e) {
    // TODO Auto-generated method stub
    System.out.println("key pressed");
    
  }
}
