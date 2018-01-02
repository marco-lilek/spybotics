package util;

public class IPoint implements Comparable {
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return compareTo(obj) == 0;
  }

  private int x,y;
  public IPoint(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int gx() {
    return x;
  }
  
  public int gy() {
    return y;
  }

  @Override
  public int compareTo(Object obj) {
    if (this == obj)
      return 0;
    if (obj == null)
      return 0;
    if (getClass() != obj.getClass())
      return 0;
    IPoint other = (IPoint) obj;
    return hashCode() - obj.hashCode(); 
  }
  
  @Override
  public String toString() {
    return "(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
  }
}
