package entity.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

import config.UnitConfig;
import core.sprite.SpriteManager;
import entity.Cursor;
import entity.Unit;
import screen.MatchScreen;
import util.Canvas;
import util.Colors;
import util.Direction;
import util.IPoint;

public class CursorPainter extends EntityPainter {
  
  private static final int UNITINFO_XOFFSET = 30;
  private static final int UNITINFO_YOFFSET = 25;
  private static final int UNITINFO_WIDTH = 210;
  private static final int UNITINFO_HEIGHT = 150;
  
  private final Cursor cursor;
  
  public CursorPainter(Cursor cursor) {
    this.cursor = cursor;
  }
  
  @Override
  public void redraw(List<Graphics2D> g) {
    if (((MatchScreen) cursor.getScreen()).whosTurn() != cursor.owner()) return;
    
    Graphics2D l0 = g.get(0);
    
    l0.setColor(Colors.WINDOW_TOP.darker());
    l0.fillRect(UNITINFO_XOFFSET + 2, UNITINFO_YOFFSET - 20 + 2, UNITINFO_WIDTH, UNITINFO_HEIGHT);
    l0.setColor(Colors.WINDOW_TOP);
    l0.fillRect(UNITINFO_XOFFSET, UNITINFO_YOFFSET - 20, UNITINFO_WIDTH, UNITINFO_HEIGHT);
    
    l0.setColor(Colors.WINDOW.darker());
    l0.fillRect(UNITINFO_XOFFSET + 2, UNITINFO_YOFFSET + 2, UNITINFO_WIDTH, UNITINFO_HEIGHT);
    l0.setColor(Colors.WINDOW);
    l0.fillRect(UNITINFO_XOFFSET, UNITINFO_YOFFSET, UNITINFO_WIDTH, UNITINFO_HEIGHT);
    
    {
      Unit selectedUnit = cursor.getSelectedUnit();
      int yoffset = 65;
      int TEXT_XOFFSET = UNITINFO_XOFFSET + 7;
      int TEXT_BLOCK_HEIGHT = 15;
      int idx = 0;
      
      l0.setColor(Colors.WINDOW_HIGH);
      l0.fillRect(TEXT_XOFFSET, UNITINFO_YOFFSET + yoffset - 12, UNITINFO_WIDTH - 14, TEXT_BLOCK_HEIGHT * 6);
      
      l0.setColor(Colors.WINDOW_HIGH);
      l0.fillRect(TEXT_XOFFSET, UNITINFO_YOFFSET + 7, 32, 32);
      l0.fillRect(TEXT_XOFFSET + 40, UNITINFO_YOFFSET + 7, 8, 32);
      l0.fillRect(TEXT_XOFFSET + 60, UNITINFO_YOFFSET + 7, UNITINFO_WIDTH - 74, 13);
      l0.fillRect(TEXT_XOFFSET + 60, UNITINFO_YOFFSET + 24, 90, 13);
      
      if (cursor.isPeeking()) l0.setColor(Colors.LIGHT_RED); 
      l0.fillRect(TEXT_XOFFSET + 186, UNITINFO_YOFFSET + 24, 10, 13);
      
      if (selectedUnit != null) {
        l0.setColor(selectedUnit.getConfig().getRGB());
        l0.fillRect(TEXT_XOFFSET, UNITINFO_YOFFSET + 7, 32, 32);
        l0.setColor(selectedUnit.getOwner().getColor());
        l0.fillRect(TEXT_XOFFSET + 40, UNITINFO_YOFFSET + 7, 8, 32);
        
        l0.setColor(Color.BLACK);
        l0.drawString(selectedUnit.getConfig().name, TEXT_XOFFSET + 60, UNITINFO_YOFFSET + 18);
        l0.drawString(String.format("S: %02d   MT: %02d", selectedUnit.getConfig().movement_speed, selectedUnit.getConfig().max_tail_length),
            TEXT_XOFFSET + 60, UNITINFO_YOFFSET + 35);
        
        SpriteManager.getManager().drawSprite(l0, selectedUnit.sprite(), TEXT_XOFFSET, UNITINFO_YOFFSET + 7);
        
        for (Iterator<UnitConfig.AttackConfig> it = selectedUnit.getConfig().attacks.iterator(); it.hasNext(); idx++ ) {
          UnitConfig.AttackConfig ac = it.next();
          if (selectedUnit.getSelectedAttack() == idx) {
            l0.setColor(Colors.LIGHT_RED);
            l0.fillRect(TEXT_XOFFSET, UNITINFO_YOFFSET + yoffset - 12, UNITINFO_WIDTH - 14, TEXT_BLOCK_HEIGHT);
          }
          
          l0.setColor(new Color(0,0,0));
          l0.drawString(ac.name, TEXT_XOFFSET, UNITINFO_YOFFSET + yoffset);
          yoffset += TEXT_BLOCK_HEIGHT;
        }
      }
    }
    
    
    Graphics l3 = g.get(2);
    Canvas drawCanvas = ((MatchScreen)cursor.getScreen()).getBoard().getTileDrawCanvas(cursor.gx(), cursor.gy());

    l3.setColor(cursor.owner().getColor().darker());
    l3.fillRect(drawCanvas.topLeft.gx() + 15, drawCanvas.topLeft.gy() + 15, 4, 4);
    l3.setColor(cursor.owner().getColor());
    l3.fillRect(drawCanvas.topLeft.gx() + 14, drawCanvas.topLeft.gy() + 14, 4, 4);
    l3.setColor(new Color(0,0,0));
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

}
