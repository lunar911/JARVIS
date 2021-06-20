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
            chunks[i] = new Chunk(pos, Pattern.EMPTYTILE, true);
        }

        setSpecialFields();
    }

    public void setSpecialFields() {
        // set all unwalkable fields
        // top row
        for (int i = 0; i < 16; i++) {
            chunks[i].setWalkable(false);
            chunks[i].drawPattern(Pattern.UNBREAKABLETILE);
        }

        // left column
        for (int i = 0; i < 160; i += 16) {
            chunks[i].setWalkable(false);
            chunks[i].drawPattern(Pattern.UNBREAKABLETILE);
        }

        // in between
        for (int i = 34; i < 143; i++) {
            if (i % 2 == 0 && i / 16 % 2 == 0) {
                chunks[i].setWalkable(false);
                chunks[i].drawPattern(Pattern.UNBREAKABLETILE);
            }
        }

    }

    public void resetChunk(int pos) {
        chunks[pos].reset(Pattern.EMPTYTILE);
    }

    public void setPlayer(int pos) {
        chunks[pos].drawPattern(Pattern.PLAYER);
    }

    public void setBomb(int pos) {
        chunks[pos].drawPattern(Pattern.BOMB);
    }

    public void setExplosionCenter(int pos) {
        chunks[pos].drawPattern(Pattern.EXPLOSIONCENTER);
    }

    public void setExplosionOrthogonal(int pos) {
        if(pos < 0 || pos > 160) return;
        if (chunks[pos].isWalkable()) chunks[pos].drawPattern(Pattern.EXPLOSIONORTHOGONAL);
    }

    public void setExplosionHorizontal(int pos) {
        if(pos < 0 || pos > 160) return;
        if (chunks[pos].isWalkable()) chunks[pos].drawPattern(Pattern.EXPLOSIONHORIZONTAL);
    }

    public boolean isWalkable(int pos) {
        return chunks[pos].isWalkable();
    }
}
