package bcut;

public enum UsefullThingsType
{
    COBBLEGEN_I(0, TileCobblegenMk1.class, "Cobblestone generator Mk1"),
    COBBLEGEN_II(8, TileCobblegenMk2.class, "Cobblestone generator Mk2"),
    COBBLEGEN_III(16, TileCobblegenMk3.class, "Cobblestone generator Mk3");

    int textureShift;
    Class <? extends TileUsefullThings > TileClass;
    String Name;

    UsefullThingsType(int textureShift, Class <? extends TileUsefullThings > TileClass, String Name) {
    	this.textureShift = textureShift;
        this.TileClass = TileClass;
        this.Name = Name;
    }

    public static TileUsefullThings makeTile(int meta) {
        if (validateMeta(meta) == meta)  try {
        	TileUsefullThings	te = values()[meta].TileClass.newInstance();
        	return te;
        } catch (InstantiationException e) {
        	e.printStackTrace();
		}
	    catch (IllegalAccessException e) {
	    	e.printStackTrace();
	    };
        return null;
    }

    public static int validateMeta(int i) {
        if (i < values().length) {
            return i;
        }
        return 0;
    }
}
