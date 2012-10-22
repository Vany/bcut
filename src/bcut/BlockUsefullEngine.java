package bcut;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import buildcraft.api.tools.IToolWrench;
import buildcraft.core.IItemPipe;
import buildcraft.core.proxy.CoreProxy;
import buildcraft.energy.BlockEngine;

public class BlockUsefullEngine extends BlockEngine {

	public BlockUsefullEngine(int i) {
		super(i);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileUsefullEngine();
	}
	
	
    @Override
    public String getTextureFile() {
        return "/gfx/usefullthings.png";
    }
    
    
    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        return 14+meta;
    }

    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(int blockid, CreativeTabs par2CreativeTabs, List itemList) {
		itemList.add(new ItemStack(this, 1, 0));
		itemList.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		TileUsefullEngine tile = (TileUsefullEngine) world.getBlockTileEntity(i, j, k);

		if (entityplayer.isSneaking()) return false;

		// Switch orientation if whacked with a wrench.
		Item equipped = entityplayer.getCurrentEquippedItem() != null ? entityplayer.getCurrentEquippedItem().getItem() : null;
		if (equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(entityplayer, i, j, k)) {
			tile.switchOrientation();
			((IToolWrench) equipped).wrenchUsed(entityplayer, i, j, k);
			return true;

		}
		// Do not open guis when having a pipe in hand
		if (entityplayer.getCurrentEquippedItem() != null && 
			entityplayer.getCurrentEquippedItem().getItem() instanceof IItemPipe)
					return false;

		if (tile.engine instanceof EngineCooledCombustion) {
				if (!CoreProxy.proxy.isRenderWorld(tile.worldObj))
					entityplayer.openGui(UsefullThings.instance, 1, world, i, j, k);
				return true;
		}
		if (tile.engine instanceof EngineEfficient) {
			if (!CoreProxy.proxy.isRenderWorld(tile.worldObj))
				entityplayer.openGui(UsefullThings.instance, 2, world, i, j, k);
			return true;
		}

		return false;
	}
}
