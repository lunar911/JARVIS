package screen;

public class VidMem extends STRUCT {

  @SJC(offset = 0, count = 2000)
  public VidChar[] expl;

  @SJC(offset = 0, count = 2000)
  public short[] chars;
}
