package screen;

public class Cursor {

  public int X = 0;
  public int Y = 0;
  private int width = 80;
  private int height = 25;

  public void setX(int newX) {
    if (newX <= 80 && newX >= 0) {
      X = newX;
    }
  }

  public void setY(int newY) {
    if (newY <= 80 && newY >= 0) {
      Y = newY;
    }
  }

  public int getPos() {
    return Y * width + X;
  }
}
