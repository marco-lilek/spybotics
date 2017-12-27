package util;

import java.util.List;
import java.util.ArrayList;

public abstract class CallbackNotifier<T> {
  private List<CallbackListener<T>> listeners = new ArrayList<CallbackListener<T>>();
  
  public void notifyListeners(T msg) {
    for (CallbackListener<T> listener : listeners) {
      listener.callback(msg);
    }
  }
  
  public void addListener(CallbackListener<T> listener) {
    listeners.add(listener);
  }
}
