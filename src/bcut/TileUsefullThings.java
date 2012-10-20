package bcut;

import buildcraft.core.TileBuildCraft;
import buildcraft.core.network.TileNetworkData;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public class TileUsefullThings extends TileBuildCraft {
	 
    public byte facing;

    public int textureShift() {
        return 0;
    }

    void setFacing(byte face) {
        facing = face;
    }

    
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        facing = nbttagcompound.getByte("facing"); 
    }

    
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setByte("facing", facing);
        super.writeToNBT(nbttagcompound);
    }

    
    @Override
    public void updateEntity() {
    	super.updateEntity();
    	if (worldObj==null ) return;
      	worldObj.addBlockEvent(xCoord, yCoord, zCoord, UsefullThings.instance.usefullthingsblockId, 1, facing);
    }
    
	
	@Override
	public void receiveClientEvent(int i, int j) {
		if (1==i) {
			facing=(byte) j;
		}
	}
	
}
