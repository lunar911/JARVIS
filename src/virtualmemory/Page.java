package virtualmemory;

public class Page extends STRUCT{
    @SJC(offset = 0, count = 1024)
    public int[] data;
}
