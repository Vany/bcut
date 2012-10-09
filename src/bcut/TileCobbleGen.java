package bcut;

import java.util.ArrayList;

import buildcraft.BuildCraftFactory;
import buildcraft.api.core.Orientations;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.core.IMachine;
import buildcraft.core.TileBuildCraft;
import buildcraft.core.utils.BlockUtil;
import buildcraft.core.utils.Utils;
import buildcraft.factory.TileMachine;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;


public class TileCobbleGen extends TileMachine implements IPipeConnection {
	IPowerProvider powerProvider;
	final int MjPerItem=10;

	public TileCobbleGen() {
		powerProvider = PowerFramework.currentFramework.createPowerProvider();
		powerProvider.configure(10, MjPerItem, 10*64*MjPerItem, MjPerItem, 100*64*MjPerItem);
	}	

	@Override
	public int powerRequest() {
			return getPowerProvider().getMaxEnergyReceived();
	}
	
	public void doWork() {
		int  e=(int)(powerProvider.getEnergyStored());
		if (e<MjPerItem) return;
		int count = e/MjPerItem;
		if (count>64) count = 64;
		powerProvider.useEnergy(count*MjPerItem, count*MjPerItem, true);
		World world = worldObj;
		ItemStack stack = new ItemStack(Block.cobblestone, count);
		
		ItemStack added = Utils.addToRandomInventory(stack, worldObj, xCoord, yCoord, zCoord, Orientations.Unknown);
		stack.stackSize -= added.stackSize;
		if (stack.stackSize <= 0) return;
		if (Utils.addToRandomPipeEntry(this, Orientations.Unknown, stack) && stack.stackSize <= 0) return;
		float f = world.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

		EntityItem entityitem = new EntityItem(world, xCoord + f, yCoord + f1 + 0.5F, zCoord + f2, stack);

		float f3 = 0.05F;
		entityitem.motionX = (float) world.rand.nextGaussian() * f3;
		entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 1.0F;
		entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
		world.spawnEntityInWorld(entityitem);
	}


	@Override
	public void setPowerProvider(IPowerProvider provider) {
		powerProvider = provider;
	}

	@Override
	public IPowerProvider getPowerProvider() {
		return powerProvider;
	}

	@Override
	public boolean manageLiquids() {
		return false;
	}

	@Override
	public boolean manageSolids() {
		return true;
	}

	@Override
	public boolean isPipeConnected(Orientations with) {
		return true;
	}

	@Override
	public boolean allowActions() {
		return false;
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
