package util.communicator;

public abstract class Communicator extends CallbackNotifier<Message> implements CallbackListener<Message> {
  
  public abstract String getName();
  
}
