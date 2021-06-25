package bomberman;

public class Chunk {
    private final int pos; // upper left corner of chunk
    private static final int chunkSize = 20;
    private boolean walkable;
    private boolean breakable;

    public Chunk(int pos, byte[] pattern, boolean walkable, boolean breakable) {
        this.pos = pos;

        this.walkable = walkable;
        this.breakable = breakable;
        drawPattern(pattern);
    }

    public void drawPattern(byte[] pattern) {
        for (int y = 0; y < chunkSize; y++) { // write col
            for (int x = 0; x < chunkSize; x++) { // write row
                MAGIC.wMem8(0xA0000 + pos + y * 320 + x, pattern[y * chunkSize + x]);
            }
        }
    }

    public void drawColor(byte color) {
        for (int y = 0; y < chunkSize; y++) { // write col
            for (int x = 0; x < chunkSize; x++) { // write row
                MAGIC.wMem8(0xA0000 + pos + y * 320 + x, color);
            }
        }
    }

    public boolean isWalkable() {
        return walkable;
    }

    public boolean isBreakable() {
        return breakable;
    }


    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public void reset(byte[] pattern) {
        setWalkable(true);
        drawPattern(pattern);
    }
}
