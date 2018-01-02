package util;

public class Canvas {
  public final IPoint topLeft;
  public final IPoint dimensions;
  
  public Canvas(IPoint topLeft, IPoint dimensions) {
    this.topLeft = topLeft;
    this.dimensions = dimensions;
  }

  public IPoint getBottomRight() {
    return new IPoint(topLeft.gx() + dimensions.gx(), topLeft.gy() + dimensions.gy());
  }
}
