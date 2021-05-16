package screen;

public class Cursor {

    private int X;
    private int Y;

    private byte color;

    private final int width = 80;
    private final int height = 25;

    public Cursor() {
        X = 0;
        Y = 0;
        color = Constants.GREY;
        writeCursor(0x0A, 0x01);
    }

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

        setX(pos % width);
        setY(pos / width);

        writeCursor(0x0F, pos);
        writeCursor(0x0E, pos >>> 8);
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

    public void setColor(int newColor) {
        color = (byte) newColor;
    }

    public byte getColor() {
        return color;
    }

    @SJC.Inline
    private void writeCursor(int b1, int b2) {
        MAGIC.wIOs8(0x03D4, (byte) b1);
        MAGIC.wIOs8(0x03D5, (byte) b2);
    }
}
