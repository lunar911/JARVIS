package bomberman;

import peripheral.StaticV24;

public class Chunk {
    private int pos = 0; // upper left corner of chunk
    private static final int chunkSize = 20;

    public Chunk(int pos) {
        this.pos = pos;
    }

    public void setColor(byte color) {
        for (int y = 0; y < chunkSize; y++) { // write col
            for (int x = 0; x < chunkSize; x++) { // write row
                MAGIC.wMem8(0xA0000 + pos + y * 320 + x, color);
            }
        }
    }

    public void reset() {
        setColor(Color.GREEN);
    }
}
