package bcut;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockBreakable;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLuminolamp extends Block {
	
	
	public BlockLuminolamp(int id) {
		super(id, Material.glass);
	    setBlockName("Luminolamp");
	    setHardness(3.0F);
	    setLightValue(1.0F);
	    setRequiresSelfNotify();
	    setBlockBounds(0.1F, 0.9F, 0.1F, 0.9F, 1F, 0.9F);
	    setCreativeTab(CreativeTabs.tabDecorations);
	}

	
	@Override
	public String getTextureFile() {
		return "/gfx/usefullthings.png";
	}


	// like a blockglass
    public int getRenderBlockPass() {
        return 0;
    }

	@Override
	public int getBlockTextureFromSide(int side){
		return side < 2 ? 6 : 7;
	}
	
	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
    protected boolean canSilkHarvest() {
        return true;
    }
	
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        int id = w.getBlockId(x,y,z);
        return (id == 0 && w.doesBlockHaveSolidTopSurface(x, y + 1, z));
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, int id) {
    	if (w.doesBlockHaveSolidTopSurface(x, y + 1, z))  return;
        this.dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
        w.setBlockWithNotify(x, y, z, 0);
    }

}
