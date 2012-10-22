package bcut;

import net.minecraft.src.ItemStack;
import buildcraft.energy.ItemEngine;

public class ItemUsefullEngine extends ItemEngine {
	public ItemUsefullEngine(int i) {
		super(i);
	}

	@SuppressWarnings({ "all" })
	@Override
	public String getItemNameIS(ItemStack itemstack) {
    	if (itemstack.getItemDamage() == 0) {
			return "tile.cooledcombustionengine";
		} else if (itemstack.getItemDamage() == 1) {
			return "tile.efficientengine";
		} else {
			return "tile.engine";
		}
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack) {
    	if (itemstack.getItemDamage() == 0) {
			return "Autocooled combustion engine";
		} else if (itemstack.getItemDamage() == 1) {
			return "Efficient Engine";
		} else {
			return "Autocooled combustion engine";
		}
    }


}
