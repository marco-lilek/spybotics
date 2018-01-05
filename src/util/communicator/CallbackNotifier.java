package util.communicator;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class CallbackNotifier<T> {
  private Map<String, CallbackListener<T>> listeners = new HashMap<String, CallbackListener<T>>();
  
  public void notifyListeners(T msg) {
    for (Iterator<CallbackListener<T>> it = listeners.values().iterator() ; it.hasNext(); ) {
      it.next().callbackRecv(msg);
    }
  }
  
  public void notifyListener(String key, T msg) {
    listeners.get(key).callbackRecv(msg);
  }
  
  public void addListener(String key, CallbackListener<T> listener) {
    listeners.put(key, listener);
  }
}
