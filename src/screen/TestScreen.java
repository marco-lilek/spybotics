package screen;

import java.awt.Graphics;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TestScreen extends GameScreen {
  
  
  TestScreen(ScreenType type, Map<String, String> globalGameConfig) {
    super(type);
  }


  @Override
  public ScreenType tick(Set<String> pressedKeys) {
    return this.getType();
  }

  @Override
  public void redraw(Graphics g) {
    g.drawString("test", 50, 50);
  }

}
