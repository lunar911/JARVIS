package terminal;

public class Wordbuffer {
    char[] buffer = new char[80];
    int cur = 0;
    final int size = 80;

    public Wordbuffer() {
        reset();
    }

    public void storeChar(char c) {
        buffer[cur] = c;

        if (cur++ > size) cur = 0;
    }

    public void removeLast() {
        if (cur-- < 0) cur = 0;

        buffer[cur] = ' ';
    }

    public String getWord() {
        char[] tmp = new char[cur];

        // create Subarray
        for(int i = 0; i < cur; i++) {
            tmp[i] = buffer[i];
            buffer[i] = ' ';
        }
        cur = 0;
        return new String(tmp);
    }

    public void reset() {
        cur = 0;
        for(int i = 0; i < size; i++) buffer[i] = ' ';
    }
}
