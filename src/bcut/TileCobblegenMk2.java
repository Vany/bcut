package bcut;

public class TileCobblegenMk2 extends TileGenericCobblegen {
	public TileCobblegenMk2() {
		super();
		efficiency=8;
		init();
	}

	public int textureShift() {
        return UsefullThingsType.COBBLEGEN_II.textureShift;
    }
}
