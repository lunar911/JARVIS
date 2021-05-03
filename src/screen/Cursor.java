package screen;

public class Cursor {

  private int X = 0;
  private int Y = 0;
  private final int width = 80;
  private final int height = 25;

  public void setX(int newX) {
    if (newX < width && newX >= 0) {
      X = newX;
    }
  }

  public void setY(int newY) {
    if (newY < height && newY >= 0) {
      Y = newY;
    }
  }

  public void setPos(int pos) {
    if (pos < 0 || pos >= 2000) pos = 0;

    setY(pos / width);
    setX(pos % width);
  }

  public int getPos() {
    return (Y * 80) + X;
  }

  public void moveNextPos() {
    setPos(getPos() + 1);
  }

  public void nextTabStop() {
    if (getPos() % 8 != 0) {
      int next = getPos() + 8 - (getPos() % 8);
      setPos(next);
    }
  }

  public void jumpNextLine() {
    if (getPos() % width == 0) {
      setPos(getPos() + width);
    } else {
      while (getPos() % width != 0) {
        moveNextPos();
      }
    }
  }
}
