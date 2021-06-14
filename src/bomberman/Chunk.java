package bomberman;

import peripheral.StaticV24;

public class Chunk {
    private byte color = 0;
    private int pos = 0; // upper left corner of chunk
    private final int chunkSize = 20;

    public Chunk(int pos) {
        this.pos = pos;
    }

    public void setColor(byte color) {
        StaticV24.println(pos);
        for(int i = 0; i < chunkSize; i++) {
            for(int j = 0; j < chunkSize; j++) {
                MAGIC.wMem8(0xA0000 + pos + i * 320 + j, (byte) color);
            }
        }
    }
}
