package bcut;

import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/gfx/usefullthings.png");
		UsefullThings.rendererId = RenderingRegistry.getNextAvailableRenderId();
	}
	
	
    @Override
    public World getClientWorld() {
    	return FMLClientHandler.instance().getClient().theWorld;
    }
}
