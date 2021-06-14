package bomberman;

import bios.BIOS;
import peripheral.StaticV24;

public class Game {
    private static final int chunkSize = 20;
    private static final int chunksPerRow = 320 / chunkSize;
    private static final int pxInRow = chunkSize * chunkSize * 16;
    private Chunk[] chunks;

    public Game() {
        chunks = new Chunk[160];

        BIOS.enterGraphicmode();
        for(int i = 0; i < chunks.length; i++){
            byte color = i % 2 == 0 ? (byte) 1 : (byte) 2;
            int pos = (i * chunkSize) % (chunksPerRow * chunkSize) + (i / 16 * pxInRow);

            /*
            StaticV24.print(i);
            StaticV24.print(" ");
            StaticV24.print(pxInRow);
            StaticV24.print(" ");
            StaticV24.println(pos);
            */

            chunks[i] = new Chunk(pos);
            chunks[i].setColor((byte) i);

        }
    }
}
