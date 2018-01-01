package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BoardConfig {
  public String tiles_file;
  public int board_width;
  public int board_height;
  
  public boolean[][] getFloorTiles() {
    String tilesStr = null;
    try {
      tilesStr = new String(Files.readAllBytes(Paths.get(tiles_file)));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    boolean[][] floorTiles = new boolean[board_width][board_height];
    int i = 0;
    int j = 0;
    for (char c : tilesStr.toCharArray()) {
      switch (c) {
      case '.':
        floorTiles[i][j] = true;
      case ',':
        i += 1;
        if (i >= board_width) {
          j += 1;
          i = 0;
        }
        break;
      }
      
      
    }
    
    return floorTiles;
  }
}
