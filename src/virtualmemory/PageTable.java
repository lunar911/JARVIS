package virtualmemory;

public class PageTable extends STRUCT {
    @SJC(offset = 0, count = 1024)
    public int[] pages;
}
