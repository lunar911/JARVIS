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

            /*
            StaticV24.print(i);
            StaticV24.print(" ");
            StaticV24.print(pxInRow);
            StaticV24.print(" ");
            StaticV24.println(pos);
            */

            chunks[i] = new Chunk(pos);
            chunks[i].setColor(Color.GREEN);
        }
    }

    public void resetChunk(int pos) {
        chunks[pos].reset();
    }

    public void setPlayer(int pos) {
        byte PLAYERCOLOR = Color.RED;
        chunks[pos].setColor(PLAYERCOLOR);
    }

    public void movePlayer(Player player, int pos) {
        if (pos < 0 || pos > 159) return;

        int oldPos = player.getPos();
        player.setPos(pos);
        setPlayer(pos);
        resetChunk(oldPos);
    }

    public void movePlayerRight(Player player) {
        int newPos = player.getPos() + 1;
        movePlayer(player, newPos);
    }

    public void movePlayerLeft(Player player) {
        int newPos = player.getPos() - 1;
        movePlayer(player, newPos);
    }

    public void movePlayerUp(Player player) {
        int newPos = player.getPos() - 16;
        movePlayer(player, newPos);
    }

    public void movePlayerDown(Player player) {
        int newPos = player.getPos() + 16;
        movePlayer(player, newPos);
    }
}
