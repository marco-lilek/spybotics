package util.communicator;

public enum Message {
  KEYBOARD_KEY_LEFT,
  KEYBOARD_KEY_RIGHT,
  KEYBOARD_KEY_UP,
  KEYBOARD_KEY_DOWN,
  KEYBOARD_KEY_SPACE,
  
  GAME_SCREEN_MATCH,
  GAME_SCREEN_TEST;
  
  public enum MsgTypes {
    KEYBOARD,
    GAME_SCREEN
  }
  
  private boolean inclBetween(Message low, Message high) {
    return compareTo(low) >= 0 && compareTo(high) <= 0;
  }
  
  public boolean is(MsgTypes t) {
    switch (t) {
    case KEYBOARD:
      return inclBetween(Message.KEYBOARD_KEY_LEFT, Message.KEYBOARD_KEY_SPACE);
    case GAME_SCREEN:
      return inclBetween(Message.GAME_SCREEN_MATCH, Message.GAME_SCREEN_TEST);
    default:
      return false;
    }
  }
}