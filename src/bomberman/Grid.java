package bomberman;

public class Grid {
    private static final int chunkSize = 20;
    private static final int chunksPerRow = 320 / chunkSize;
    private static final int pxInRow = chunkSize * chunkSize * 16;
    private final Chunk[] chunks;

    Grid() {
        chunks = new Chunk[160];
        for (int i = 0; i < chunks.length; i++) {
            int pos = (i * chunkSize) % (chunksPerRow * chunkSize) + (i / 16 * pxInRow);
            chunks[i] = new Chunk(pos, Color.GREEN, true);
        }

        setSpecialFields();
    }

    public void setSpecialFields() {
        // set all unwalkable fields
        // top row
        for (int i = 0; i < 16; i++) {
            chunks[i].setWalkable(false);
            chunks[i].setColor(Color.BLACK);
        }

        // left column
        for (int i = 0; i < 160; i += 16) {
            chunks[i].setWalkable(false);
            chunks[i].setColor(Color.BLACK);
        }

        // in between
        for (int i = 34; i < 143; i++) {
            if ( i % 2 == 0 && i / 16 % 2 == 0 ) {
                chunks[i].setWalkable(false);
                chunks[i].setColor(Color.BLACK);
            }
        }

    }

    public void resetChunk(int pos) {
        chunks[pos].reset();
    }

    public void setPlayer(int pos) {
        byte PLAYERCOLOR = Color.RED;
        chunks[pos].setColor(PLAYERCOLOR);
    }

    public void setBomb(int pos) {
        byte BOMBCOLOR = Color.GRAY;
        chunks[pos].setColor(BOMBCOLOR);
    }

    public boolean isWalkable(int pos) {
        return chunks[pos].isWalkable();
    }
}
