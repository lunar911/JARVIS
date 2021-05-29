package virtualmemory;

public class PageDir extends STRUCT{
    @SJC(offset = 0, count = 1024)
    public int[] tables;
}
