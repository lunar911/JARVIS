public class Screen {
    private static int vidMem = 0xB8000;
    
    public static void clear() {
      vidMem = 0xB8000;
      int i;
      for(i = 0; i < 2000; i++)
      {
        print((char)' ', (byte) 0x00);
      }
      vidMem = 0xB8000;
    }

    public static void print(char c, byte b) {
      MAGIC.wMem8(vidMem++, (byte) c);
      MAGIC.wMem8(vidMem++, (byte) 0x01);
    }   
}
