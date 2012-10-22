package bcut;

import buildcraft.BuildCraftEnergy;
import buildcraft.core.DefaultProps;
import buildcraft.core.render.RenderingEntityBlocks;
import buildcraft.core.render.RenderingEntityBlocks.EntityRenderIndex;
import buildcraft.energy.render.RenderEngine;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/gfx/usefullthings.png");
		ClientRegistry.bindTileEntitySpecialRenderer(TileUsefullEngine.class, new RenderEngine());
	}
	
	
    @Override
    public World getClientWorld() {
    	return FMLClientHandler.instance().getClient().theWorld;
    }
    
	@Override
	public void registerBlockRenderers() {
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(UsefullThings.usefullEngineBlock, 0),
				new RenderEngine("/gfx/base_cooled_combustion.png"));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(UsefullThings.usefullEngineBlock, 1),
				new RenderEngine(DefaultProps.TEXTURE_PATH_BLOCKS + "/gfx/base_efficient.png"));
	}
}
