package java.lang;

public class String {
    private char[] value;
    private int count;

    public String(char[] chararray){
        this.value = chararray;
        this.count = chararray.length;
    }

    @SJC.Inline
    public int length() {
        return count;
    }

    @SJC.Inline
    public char charAt(int i) {
        return value[i];
    }

    public boolean equals(String other) {
        if (this.length() != other.length()) return false;

        for(int i = 0; i < this.count; i++) {
            if(value[i] != other.value[i])
                return false;
        }
        return true;
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