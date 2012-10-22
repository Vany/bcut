package bcut;

import buildcraft.energy.gui.ContainerEngine;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{

	public void registerRenderInformation() {
	}

	
	public World getClientWorld() {
		return null;
	}

	
    @Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z)) return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileUsefullEngine)) return null;
		TileUsefullEngine engine = (TileUsefullEngine) tile;

		switch (ID) {

		case 1:
			return new GuiCooledCombustionEngine(player.inventory, engine);

		case 2:
			return new GuiEfficientEngine(player.inventory, engine);

		default:
			return null;
		}
	}

	
    @Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		if (!world.blockExists(x, y, z)) return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileUsefullEngine)) return null;

		TileUsefullEngine engine = (TileUsefullEngine) tile;

		switch (ID) {

		case 1:
			return new ContainerEngine(player.inventory, engine);

		case 2:
			return new ContainerEngine(player.inventory, engine);
			
		default:
			return null;
		}
	}
	
    
    public void registerBlockRenderers() {
	}
}
