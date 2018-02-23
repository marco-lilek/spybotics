package entity.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

import config.UnitConfig;
import entity.Cursor;
import entity.Unit;
import screen.MatchScreen;
import util.Canvas;
import util.Direction;
import util.IPoint;

public class CursorPainter extends EntityPainter {
  
  private static final int UNITINFO_XOFFSET = 30;
  private static final int UNITINFO_YOFFSET = 30;
  private static final int UNITINFO_WIDTH = 210;
  private static final int UNITINFO_HEIGHT = 200;
  
  private final Cursor cursor;
  
  public CursorPainter(Cursor cursor) {
    this.cursor = cursor;
  }
  
  @Override
  public void redraw(List<Graphics2D> g) {
    if (((MatchScreen) cursor.getScreen()).whosTurn() != cursor.owner()) return;
    
    Graphics2D l0 = g.get(0);
    
    l0.drawRect(UNITINFO_XOFFSET, UNITINFO_YOFFSET, UNITINFO_WIDTH, UNITINFO_HEIGHT);
    Unit selectedUnit = cursor.getSelectedUnit();
    int yoffset = 100;
    if (selectedUnit != null) {
      int TEXT_XOFFSET = UNITINFO_XOFFSET + 10;
      int TEXT_BLOCK_HEIGHT = 15;
      int idx = 0;
      for (Iterator<UnitConfig.AttackConfig> it = selectedUnit.getConfig().attacks.iterator(); it.hasNext(); idx++ ) {
        UnitConfig.AttackConfig ac = it.next();
        if (selectedUnit.getSelectedAttack() == idx) {
          l0.setColor(new Color(200,200,200));
          l0.fillRect(TEXT_XOFFSET, UNITINFO_YOFFSET + yoffset - 12, UNITINFO_WIDTH - 20, TEXT_BLOCK_HEIGHT);
        }
        
        l0.setColor(new Color(0,0,0));
        l0.drawString(ac.name, TEXT_XOFFSET, UNITINFO_YOFFSET + yoffset);
        yoffset += TEXT_BLOCK_HEIGHT;
      }
    }
    
    Graphics l3 = g.get(2);
    Canvas drawCanvas = ((MatchScreen)cursor.getScreen()).getBoard().getTileDrawCanvas(cursor.gx(), cursor.gy());

    if (drawCanvas != null) {
      l3.setColor(new Color(0,0,255));
      l3.drawRect(drawCanvas.topLeft.gx(), drawCanvas.topLeft.gy(), drawCanvas.dimensions.gx(), drawCanvas.dimensions.gy());
      l3.setColor(new Color(0,0,0));
    }
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

}
