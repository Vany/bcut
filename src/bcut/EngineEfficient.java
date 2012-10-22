package bcut;

import net.minecraft.src.ICrafting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import buildcraft.api.core.Orientations;
import buildcraft.api.fuels.IronEngineFuel;
import buildcraft.api.liquids.LiquidManager;
import buildcraft.api.liquids.LiquidStack;
import buildcraft.api.liquids.LiquidTank;
import buildcraft.core.utils.Utils;
import buildcraft.energy.Engine;
import buildcraft.energy.TileEngine;
import buildcraft.energy.Engine.EnergyStage;
import buildcraft.energy.gui.ContainerEngine;


public class EngineEfficient extends Engine {
	public static int MAX_LIQUID = LiquidManager.BUCKET_VOLUME * 25;
	public static int MAX_HEAT = 100000;
	public static int COOLANT_THRESHOLD = 49000;

	private ItemStack itemInInventory;
	int burnTime = 0;
	int liquidQty = 0;
	public int liquidId = 0;
	int heat = 0;
	boolean lastPowered = false;
	
	
	public EngineEfficient(TileEngine engine) {
		super(engine);

		maxEnergy = 250000;
		maxEnergyExtracted = 1500;
	}
	
	@Override
	public String getTextureFile() {
		return "/gfx/base_efficient.png";
	}
	
	
	@Override
	public int explosionRange() {
		return 8;
	}
	
	
	@Override
	public int maxEnergyReceived() {
		return 2000;
	}

	@Override
	public float getPistonSpeed() {
		switch (getEnergyStage()) {
		case Blue:
			return 0.08F;
		case Green:
			return 0.1F;
		default:
			return 0.0f;
		}
	}

	
	@Override
	public boolean isBurning() {
		return liquidQty > 0 && tile.isRedstonePowered;
	}

	
	@Override
	public void burn() {
		currentOutput = 0;
		IronEngineFuel currentFuel = IronEngineFuel.getFuelForLiquid(new LiquidStack(liquidId, liquidQty, 0));
		if (currentFuel == null) return;
		if (!tile.isRedstonePowered)	return;
		if (burnTime > 0 || liquidQty > 0) {
			if (burnTime > 0) {
				burnTime--;
			} else {
				liquidQty-=2;
				burnTime = currentFuel.totalBurningTime / LiquidManager.BUCKET_VOLUME;
			}
		currentOutput = currentFuel.powerPerCycle*2.5F;
		addEnergy(currentOutput);
		heat += currentOutput;
		}
	}

	
	@Override
	public void update() {
		super.update();

		if (itemInInventory != null) {
			LiquidStack liquid = null;
			liquid = LiquidManager.getLiquidForFilledItem(itemInInventory);
			if (liquid != null) {
				if (fill(Orientations.Unknown, liquid, false) == liquid.amount) {
					fill(Orientations.Unknown, liquid, true);
					tile.setInventorySlotContents(0, Utils.consumeItem(itemInInventory));
				}
			}
		}
		
		if (heat > COOLANT_THRESHOLD) heat = COOLANT_THRESHOLD;
		if (!tile.isRedstonePowered && heat>=30) heat -= 30;
	}
	
	
	@Override
	public int getScaledBurnTime(int i) {
		return (int) (((float) liquidQty / (float) (MAX_LIQUID)) * i);
	}
	
	@Override
	public void computeEnergyStage() {
		if (heat <= MAX_HEAT / 4) {
			energyStage = EnergyStage.Blue;
		} else if (heat <= MAX_HEAT / 2) {
			energyStage = EnergyStage.Green;
		} else if (heat <= MAX_HEAT * 3F / 4F) {
			energyStage = EnergyStage.Yellow;
		} else if (heat <= MAX_HEAT) {
			energyStage = EnergyStage.Red;
		} else {
			energyStage = EnergyStage.Explosion;
		}
	}

	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		liquidId = nbttagcompound.getInteger("liquidId");
		liquidQty = nbttagcompound.getInteger("liquidQty");
		burnTime = nbttagcompound.getInteger("burnTime");
		heat = nbttagcompound.getInteger("heat");

		if (nbttagcompound.hasKey("itemInInventory")) {
			NBTTagCompound cpt = nbttagcompound.getCompoundTag("itemInInventory");
			itemInInventory = ItemStack.loadItemStackFromNBT(cpt);
		}

	}
	
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("liquidId", liquidId);
		nbttagcompound.setInteger("liquidQty", liquidQty);
		nbttagcompound.setInteger("burnTime", burnTime);
		nbttagcompound.setInteger("heat", heat);
		
		if (itemInInventory != null) {
			NBTTagCompound cpt = new NBTTagCompound();
			itemInInventory.writeToNBT(cpt);
			nbttagcompound.setTag("itemInInventory", cpt);
		}

	}
	
	
	@Override
	public void delete() {

	}
	
	
	@Override
	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 0:
			energy = j / 10;
			break;
		case 1:
			currentOutput = j / 10;
			break;
		case 2:
			heat = j;
			break;
		case 3:
			liquidQty = j;
			break;
		case 4:
			liquidId = j;
			break;
		}
	}
	
	
	@Override
	public void sendGUINetworkData(ContainerEngine containerEngine, ICrafting iCrafting) {
		iCrafting.updateCraftingInventoryInfo(containerEngine, 0, Math.round(energy * 10));
		iCrafting.updateCraftingInventoryInfo(containerEngine, 1, Math.round(currentOutput * 10));
		iCrafting.updateCraftingInventoryInfo(containerEngine, 2, heat);
		iCrafting.updateCraftingInventoryInfo(containerEngine, 3, liquidQty);
		iCrafting.updateCraftingInventoryInfo(containerEngine, 4, liquidId);
	}
	
	@Override
	public boolean isActive() {
		return true;
	}
	
	
	@Override
	public int getHeat() {
		return heat;
	}
	
	/* ITANKCONTAINER */
	public int fill(Orientations from, LiquidStack resource, boolean doFill) {
		int res = 0;
		if (liquidQty > 0 && liquidId != resource.itemID) return 0;

		if (IronEngineFuel.getFuelForLiquid(resource) == null) return 0;

		if (liquidQty + resource.amount <= MAX_LIQUID) {
			if (doFill) liquidQty += resource.amount;
			res = resource.amount;
		} else {
			res = MAX_LIQUID - liquidQty;

			if (doFill)liquidQty = MAX_LIQUID;
		}
		liquidId = resource.itemID;
		return res;
	}
	
	
	@Override
	public LiquidTank[] getLiquidSlots() {
		return new LiquidTank[] { new LiquidTank(liquidId, liquidQty, MAX_LIQUID) };
	}
	

	/* IINVENTORY */
	@Override public int getSizeInventory() { return 1; }
	@Override public ItemStack getStackInSlot(int i) { return itemInInventory; }
	@Override public void setInventorySlotContents(int i, ItemStack itemstack) { itemInInventory = itemstack; }

	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (itemInInventory != null) {
			ItemStack newStack = itemInInventory.splitStack(j);
			if (itemInInventory.stackSize == 0)	itemInInventory = null;
			return newStack;
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		if (itemInInventory == null) return null;
		ItemStack toReturn = itemInInventory;
		itemInInventory = null;
		return toReturn;
	}

}
