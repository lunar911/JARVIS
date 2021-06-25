package bomberman;

import helpers.Time;

public class Grid {
    private static final int chunkSize = 20;
    private static final int chunksPerRow = 320 / chunkSize;
    private static final int pxInRow = chunkSize * chunkSize * 16;
    private final Chunk[] chunks;


    Grid() {
        chunks = new Chunk[160];
        for (int i = 0; i < chunks.length; i++) {
            int pos = (i * chunkSize) % (chunksPerRow * chunkSize) + (i / 16 * pxInRow);
            chunks[i] = new Chunk(pos, Pattern.EMPTYTILE, true, false);
        }

        setSpecialFields();
    }

    public void setSpecialFields() {
        // set all unwalkable fields
        // top row
        for (int i = 0; i < 16; i++) {
            chunks[i].setWalkable(false);
            chunks[i].setBreakable(false);
            chunks[i].drawPattern(Pattern.UNBREAKABLETILE);
        }

        // left column
        for (int i = 0; i < 160; i += 16) {
            chunks[i].setWalkable(false);
            chunks[i].setBreakable(false);
            chunks[i].drawPattern(Pattern.UNBREAKABLETILE);
        }

        // in between
        for (int i = 34; i < 143; i++) {
            if (i % 2 == 0 && i / 16 % 2 == 0) {
                chunks[i].setWalkable(false);
                chunks[i].setBreakable(false);
                chunks[i].drawPattern(Pattern.UNBREAKABLETILE);
            }
        }

        // set all breakable tiles
        for (int i = 0; i < 40; i++) {
            int randpos = RNG.getRandomInt(1337, 160);
            if (chunks[randpos].isWalkable()) {
                chunks[randpos].setBreakable(true);
                chunks[randpos].setWalkable(false);
                chunks[randpos].drawPattern(Pattern.BREAKABLETILE);
            }
        }
    }

    public void resetChunk(int pos) {
        if (pos < 0 || pos > 160) return;
        if (chunks[pos].isWalkable() || chunks[pos].isBreakable()) chunks[pos].reset(Pattern.EMPTYTILE);
    }

    public void setPlayer(int pos) {
        chunks[pos].drawPattern(Pattern.PLAYER);
    }

    public void setEnemy(int pos) {
        if (pos < 0 || pos > 160) return;
        chunks[pos].drawPattern(Pattern.ENEMY);
    }

    public void setBomb(int pos) {
        chunks[pos].drawPattern(Pattern.BOMB);
    }

    public void setExplosionCenter(int pos) {
        chunks[pos].drawPattern(Pattern.EXPLOSIONCENTER);
    }

    public void setExplosionOrthogonal(int pos) {
        if (pos < 0 || pos > 160) return;
        if (chunks[pos].isWalkable()) chunks[pos].drawPattern(Pattern.EXPLOSIONORTHOGONAL);
    }

    public void setExplosionHorizontal(int pos) {
        if (pos < 0 || pos > 160) return;
        if (chunks[pos].isWalkable()) chunks[pos].drawPattern(Pattern.EXPLOSIONHORIZONTAL);
    }

    public boolean isWalkable(int pos) {
        if (pos < 0 || pos > 159) return false;
        return chunks[pos].isWalkable();
    }

    public void win() {
        byte WINNINGBACKGROUND = 3;
        byte WINNINGWRITING = 0;
        while (true) {
            for (Chunk chunk : chunks) {
                chunk.drawColor(WINNINGBACKGROUND);
            }

            // W
            for (int i = 0; i < 4; i++) chunks[33 + i * 16].drawColor(WINNINGWRITING);
            chunks[98].drawColor(WINNINGWRITING);
            for (int i = 0; i < 4; i++) chunks[35 + i * 16].drawColor(WINNINGWRITING);
            chunks[100].drawColor(WINNINGWRITING);
            for (int i = 0; i < 4; i++) chunks[37 + i * 16].drawColor(WINNINGWRITING);

            // I
            for (int i = 0; i < 5; i++) chunks[39 + i * 16].drawColor(WINNINGWRITING);

            // N
            for (int i = 0; i < 5; i++) chunks[41 + i * 16].drawColor(WINNINGWRITING);
            chunks[58].drawColor(WINNINGWRITING);
            chunks[75].drawColor(WINNINGWRITING);
            for (int i = 0; i < 5; i++) chunks[44 + i * 16].drawColor(WINNINGWRITING);

            // !
            for (int i = 0; i < 3; i++) chunks[46 + i * 16].drawColor(WINNINGWRITING);
            chunks[110].drawColor(WINNINGWRITING);

            WINNINGBACKGROUND = (byte) RNG.getRandomInt(21, 256);
            WINNINGWRITING = (byte) RNG.getRandomInt(21, 256);

            Time.wait(10);
        }
    }

    public void lose() {
        final byte LOSEBACKGROUND = 0;
        final byte LOSEWRITING = 4;

        for (Chunk chunk : chunks) {
            chunk.drawColor(LOSEBACKGROUND);
        }

        // L
        for (int i = 0; i < 6; i++) chunks[33 + i * 16].drawColor(LOSEWRITING);
        chunks[114].drawColor(LOSEWRITING);
        chunks[115].drawColor(LOSEWRITING);

        // O
        for (int i = 0; i < 6; i++) chunks[37 + i * 16].drawColor(LOSEWRITING);
        for (int i = 0; i < 6; i++) chunks[37 + i * 16].drawColor(LOSEWRITING);
        chunks[38].drawColor(LOSEWRITING);
        chunks[118].drawColor(LOSEWRITING);
        for (int i = 0; i < 6; i++) chunks[39 + i * 16].drawColor(LOSEWRITING);

        // S
        for (int i = 0; i < 4; i++) chunks[41 + i * 16].drawColor(LOSEWRITING);
        chunks[42].drawColor(LOSEWRITING);
        chunks[43].drawColor(LOSEWRITING);
        chunks[90].drawColor(LOSEWRITING);
        for (int i = 0; i < 3; i++) chunks[91 + i * 16].drawColor(LOSEWRITING);
        chunks[121].drawColor(LOSEWRITING);
        chunks[122].drawColor(LOSEWRITING);

        // T
        chunks[45].drawColor(LOSEWRITING);
        for (int i = 0; i < 6; i++) chunks[46 + i * 16].drawColor(LOSEWRITING);
        chunks[47].drawColor(LOSEWRITING);

        while(true);
    }
}
