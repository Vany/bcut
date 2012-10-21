package bcut;

import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import buildcraft.BuildCraftCore;
import buildcraft.core.DefaultProps;
import buildcraft.factory.BlockMiningWell;
import buildcraft.factory.TileQuarry;
import buildcraft.factory.network.PacketHandlerFactory;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import bcut.PacketHandler;

@Mod(name = "BuildCraft UsefullThings", version = "0.0.4", useMetadata = false, modid = "UsefullThings")
@NetworkMod(channels = {"bcut"} 
	,versionBounds = "[0.0,)" 
	,clientSideRequired = true 
	,serverSideRequired = false 
	//,packetHandler = PacketHandler.class
	)

public class UsefullThings
{
    public BlockUsefullThings usefullthingsBlock;
    public int usefullthingsblockId;
    public BlockLuminolamp luminolampBlock;
    public int luminolampBlockId;
    public static int rendererId;
    
    @Instance("UsefullThings")
    public static UsefullThings instance;

    @SidedProxy(clientSide = "bcut.ClientProxy", serverSide = "bcut.CommonProxy")
    public static CommonProxy proxy;

    @PreInit
    public void PreInit(FMLPreInitializationEvent evt)
    {
        Configuration cfg = new Configuration(evt.getSuggestedConfigurationFile());

        try {
            cfg.load();
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "UsefullThings config error");
        }

        Property firstblockId = cfg.getOrCreateBlockIdProperty("cobblegen.id", 200);
        Property luminolampId = cfg.getOrCreateBlockIdProperty("luminolamp.id", 201);
        firstblockId.comment = "Block Id of cogglegen machines";
        firstblockId.comment = "Block Id of luminescence lighting";
        usefullthingsblockId = firstblockId.getInt(200);
        luminolampBlockId = luminolampId.getInt(201);
        cfg.save();
    }

    @Init
    public void Init(FMLInitializationEvent evt) {
        usefullthingsBlock = new BlockUsefullThings(usefullthingsblockId);
        luminolampBlock = new BlockLuminolamp(luminolampBlockId);
        
        GameRegistry.registerBlock(usefullthingsBlock, ItemBlockUsefullThing.class);
        GameRegistry.registerBlock(luminolampBlock);
        LanguageRegistry.addName(luminolampBlock, "Luminoforium white lamp");
        
    	GameRegistry.registerTileEntity(TileUsefullThings.class, "CobbleGen");
    	
        for (UsefullThingsType typ : UsefullThingsType.values()) {
            GameRegistry.registerTileEntity(typ.TileClass, typ.name());
        }
        
        
        GameRegistry.addRecipe(new ItemStack(usefullthingsBlock, 1 ,0),
                new Object[] { "opo", "lgw", "opo",
                        'o',	Block.obsidian,
                        'p',	Block.pistonBase,
                        'l',	Item.bucketLava,
                        'w',	Item.bucketWater,
                        'g',	BuildCraftCore.goldGearItem,
        		});
        GameRegistry.addRecipe(new ItemStack(usefullthingsBlock, 1 ,1),
                new Object[] { "#g#", "###", "b#b",
                        'g', BuildCraftCore.goldGearItem,
                        'b', Block.blockSteel,
                        '#', new ItemStack(usefullthingsBlock, 1 ,0)
        		});
        GameRegistry.addRecipe(new ItemStack(usefullthingsBlock, 1 ,2),
                new Object[] { "#g#", "###", "b#b",
                        'g', BuildCraftCore.goldGearItem,
                        'b', Block.blockSteel,
                        '#', new ItemStack(usefullthingsBlock, 1 ,1)
        		});
        GameRegistry.addRecipe(new ItemStack(luminolampBlock, 1),
                new Object[] { " b ", "rrr", " g ",
                        'g', Block.glass,
                        'r', Item.redstone,
                        'b', Block.fenceIron
        		});
    }

    @PostInit
    public void PostInit(FMLPostInitializationEvent evt) {
    }
}
