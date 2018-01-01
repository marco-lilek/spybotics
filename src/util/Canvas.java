package util;

public class Canvas {
  public final IPoint topLeft;
  public final IPoint dimensions;
  
  public Canvas(IPoint topLeft, IPoint dimensions) {
    this.topLeft = topLeft;
    this.dimensions = dimensions;
  }
}
