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

public abstract class TileGenericCobblegen extends TileUsefullThings implements IMachine, IPowerReceptor, IPipeConnection {

	IPowerProvider powerProvider;
	final int MjPerItem=30;
	int efficiency;
	
	public TileGenericCobblegen() {
	}
	
	void init() {
		powerProvider = PowerFramework.currentFramework.createPowerProvider();
		powerProvider.configure(20, 1, efficiency, MjPerItem, efficiency*MjPerItem);
	}


	@Override
	public void doWork() {
		int  e=(int)(powerProvider.getEnergyStored());
		if (e<MjPerItem) return;
		int count = e/MjPerItem;
		if (count>efficiency) count = efficiency;
		powerProvider.useEnergy(count*MjPerItem, count*MjPerItem, true);
		World world = worldObj;
		ItemStack stack = new ItemStack(Block.cobblestone, count);
		ItemStack added = Utils.addToRandomInventory(stack, worldObj, xCoord, yCoord, zCoord, Orientations.Unknown);
		stack.stackSize -= added.stackSize;
		this.worldObj.spawnParticle("smoke", xCoord, yCoord+1, zCoord, 0.0D, 0.1D, 0.0D);
        this.worldObj.spawnParticle("flame", xCoord, yCoord+1, zCoord, 0.0D, 0.1D, 0.0D);
		if (stack.stackSize <= 0) return;
		if (Utils.addToRandomPipeEntry(this, Orientations.Unknown, stack) && stack.stackSize <= 0) return;
		float f = world.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

		EntityItem entityitem = new EntityItem(world, xCoord + f, yCoord + f1 + 0.5F, zCoord + f2, stack);
		float f3 = 0.05F;
		entityitem.motionX = (float) world.rand.nextGaussian() * f3;
		entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
		entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
		world.spawnEntityInWorld(entityitem);
	}

	
	@Override
	public int powerRequest() {
		return efficiency;
	}

	@Override
	public boolean isPipeConnected(Orientations with) {
		return true;
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
	public boolean isActive() {
		return true;
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
	public boolean allowActions() {
		return false;
	}
	
}
