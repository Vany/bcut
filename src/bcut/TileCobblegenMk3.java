package bcut;

public class TileCobblegenMk3 extends TileGenericCobblegen
{
	public TileCobblegenMk3() {
		super();
		efficiency=64;
		init();
	}

	public int textureShift() {
        return UsefullThingsType.COBBLEGEN_III.textureShift;
    }
}
