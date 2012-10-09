package bcut;

import java.util.ArrayList;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;
import buildcraft.core.DefaultProps;
import buildcraft.core.utils.Utils;
import buildcraft.factory.BlockMachineRoot;

public class BlockCobbleGen  extends BlockMachineRoot {

	
	public BlockCobbleGen(int i) {
		super(i, Material.ground);
		setHardness(1.5F);
		setResistance(10F);
		setStepSound(soundStoneFootstep);
	}
	
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if(i==1) return 1;
		return 2;
	}

	
	@Override
	public String getTextureFile() {
		return "cobblegen.png";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addCreativeItems(ArrayList itemList) {
		itemList.add(new ItemStack(this));
	}


	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileCobbleGen();
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		Orientations orientation = Utils.get2dOrientation(new Position(entityliving.posX, entityliving.posY, entityliving.posZ),
				new Position(i, j, k));
		world.setBlockMetadataWithNotify(i, j, k, orientation.reverse().ordinal());
	}
	
}
