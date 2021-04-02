package screen;

public class Cursor {

  public int X = 0;
  public int Y = 0;
  private final int width = 80;
  private final int height = 25;

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

  // handle scrolling in terminal internally
  public void moveNextPos() {
    if (X + 1 > width) {
      X = 0;
      if (Y + 1 > height) {
        Y = 0;
      } else {
        Y += 1;
      }
    } else {
      X += 1;
    }
  }
}
