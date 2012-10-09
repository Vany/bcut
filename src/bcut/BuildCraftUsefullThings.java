package bcut;

import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import buildcraft.core.DefaultProps;
import buildcraft.core.Version;
import buildcraft.core.proxy.CoreProxy;
import buildcraft.factory.BlockMiningWell;
import buildcraft.factory.TileQuarry;
import buildcraft.factory.network.PacketHandlerFactory;
import buildcraft.usefullthings.BlockCobbleGen;
import buildcraft.usefullthings.TileCobbleGen;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(name="BuildCraft UsefullThings", version=Version.VERSION, useMetadata = false, modid = "BuildCraft|UsefullThings", dependencies = DefaultProps.DEPENDENCY_CORE)
@NetworkMod(channels = {DefaultProps.NET_CHANNEL_NAME}, packetHandler = PacketHandlerFactory.class, clientSideRequired = true, serverSideRequired = true)

public class BuildCraftUsefullThings {
	public BlockCobbleGen cobblegenBlock;
	public int cobblegenblockid;
	
	@Instance("BuildCraft|UsefullThings")
	public static BuildCraftUsefullThings instance;

	@PreInit
	public void initialize(FMLPreInitializationEvent evt) {
		Configuration cfg;
		cfg = new Configuration(evt.getSuggestedConfigurationFile());
		try { cfg.load(); } catch (Exception e) { FMLLog.log(Level.SEVERE, e, "UsefullThings config error"); }

		Property cobblegenId = cfg.getOrCreateBlockIdProperty("cobblegen.id", 200);
		cobblegenId.comment = "Block Id of cogglegen";
		cobblegenblockid = cobblegenId.getInt(1);
		cfg.save();
		
		cobblegenBlock = new BlockCobbleGen(cobblegenblockid);
		CoreProxy.proxy.registerBlock(cobblegenBlock.setBlockName("cobblegen"));
		CoreProxy.proxy.addName(cobblegenBlock, "Generator of cobblestone");
	}

	@Init
	public void load(FMLInitializationEvent evt) {
		CoreProxy.proxy.registerTileEntity(TileCobbleGen.class, "CobbleGen");
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(cobblegenBlock), 
				new Object[] { "opo", "lgw", "opo", 
				Character.valueOf('o'),	Block.obsidian,
				Character.valueOf('p'),	Block.pistonBase,
				Character.valueOf('l'),	Item.bucketLava,
				Character.valueOf('w'),	Item.bucketWater,
				Character.valueOf('g'),	BuildCraftCore.goldGearItem,
		}); 

	}
	
}
