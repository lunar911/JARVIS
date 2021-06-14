package bomberman;

import bios.BIOS;

public class Game {
    private static final int chunkSize = 20;
    private static final int pxInRow = chunkSize * chunkSize * 15;
    private Chunk[] chunks;

    public Game() {
        chunks = new Chunk[160];

        BIOS.enterGraphicmode();
        for(int i = 0; i < chunks.length; i++){
            byte color = i % 2 == 0 ? (byte) 1 : (byte) 2;
            chunks[i] = new Chunk(i * chunkSize + (i / 16 * pxInRow));
            chunks[i].setColor(color);
        }
    }
}
