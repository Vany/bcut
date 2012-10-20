package bcut;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemBlockUsefullThing extends ItemBlock
{
    public ItemBlockUsefullThing(int id) {
        super(id);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getItemNameIS(ItemStack itemstack) {
        return UsefullThingsType.values()[itemstack.getItemDamage()].Name;
    }
    
    @Override
	public String getItemDisplayName(ItemStack itemstack) {
    	return UsefullThingsType.values()[itemstack.getItemDamage()].Name;
    }
}
