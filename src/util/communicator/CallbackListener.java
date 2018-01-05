package util.communicator;

public interface CallbackListener<T> {
  void callbackRecv(T msg);
}
