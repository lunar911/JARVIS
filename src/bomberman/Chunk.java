package bomberman;

public class Chunk {
    private int pos; // upper left corner of chunk
    private static final int chunkSize = 20;
    private boolean walkable;

    public Chunk(int pos, byte[] pattern, boolean walkable) {
        this.pos = pos;

        this.walkable = walkable;
        drawPattern(pattern);
    }

    public void drawPattern(byte[] pattern) {
        for (int y = 0; y < chunkSize; y++) { // write col
            for (int x = 0; x < chunkSize; x++) { // write row
                MAGIC.wMem8(0xA0000 + pos + y * 320 + x, pattern[y * chunkSize + x]);
            }
        }
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public void reset(byte[] pattern) {
        drawPattern(pattern);
    }
}
