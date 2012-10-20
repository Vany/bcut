package bcut;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import buildcraft.api.core.Orientations;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.core.IMachine;
import buildcraft.core.utils.Utils;
import buildcraft.factory.TileMachine;

public class TileCobblegenMk1 extends TileGenericCobblegen  {
	
	
	public TileCobblegenMk1() {
		super();
		efficiency=1;
		init();
	}
	
	public int textureShift() {
        return UsefullThingsType.COBBLEGEN_I.textureShift;
    }
	
}
