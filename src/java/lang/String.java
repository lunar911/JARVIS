package java.lang;

public class String {
    private char[] value;
    private int count;

    @SJC.Inline
    public int length() {
        return count;
    }

    @SJC.Inline
    public char charAt(int i) {
        return value[i];
    }

    public boolean startsWith(String s) {
        if (this.length() < s.length()) return false;

        for (int i = 0; i < s.length(); i++) {
            if (this.charAt(i) != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}