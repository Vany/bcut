package bcut;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.client.ITextureFX;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;
import buildcraft.core.utils.Utils;

public class BlockUsefullThings extends BlockContainer {
    public static int tmatrix[] = {0, 0, 0, 2, 3, 1}; // minecraft's sides

    protected BlockUsefullThings(int i) {
        super(i, Material.rock);
        setBlockName("UsefullThing");
        setHardness(3.0F);
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getTextureFile() {
        return "/gfx/usefullthings.png";
    }

    @Override
    public TileEntity createNewTileEntity(World w) {
        return null;
    }

    
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return UsefullThingsType.makeTile(metadata);
    }

    
    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        return i + UsefullThingsType.values()[j].textureShift;
    }

    
    @Override
    public int getBlockTexture(IBlockAccess worldAccess, int i, int j, int k, int side) {
        TileEntity te = worldAccess.getBlockTileEntity(i, j, k);
        if (te != null && te instanceof TileUsefullThings) {
            TileUsefullThings tu = (TileUsefullThings)te;
            if (side < 2) {
                return tu.textureShift() + side;
            }
            return (tmatrix[side] - tu.facing  + 4) % 4 + 2 + tu.textureShift();
        }
        return 0;
    }

    
    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
        byte facing = (byte)((int)(Math.atan2(k - entityliving.posZ, i - entityliving.posX) / Math.PI * 2 + 2.5D) % 4);
        TileEntity te = world.getBlockTileEntity(i, j, k);
        if (te != null && te instanceof TileUsefullThings) {
            ((TileUsefullThings) te).setFacing(facing);
            world.markBlockNeedsUpdate(i, j, k);
        }
    }

    
    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        world.markBlockNeedsUpdate(i, j, k);
    }

    
//    @Override
//    public boolean onBlockActivated(World w, int i, int j, int k, EntityPlayer player, int xp, float yp, float zp, float par9) {
//        TileEntity te = w.getBlockTileEntity(i, j, k);
//        int meta = w.getBlockMetadata(i, j, k);
//        if (te != null && te instanceof TileUsefullThings) {
//            TileUsefullThings tu = (TileUsefullThings)te;
//            player.sendChatToPlayer("=:" + tu.facing + " " + i + " " + j + " " + k+ "    w="+te.getClass()+"     "+meta);
//            w.markBlockNeedsUpdate(i, j, k);
//        }
//        return true;
//    }

    
    @Override
    protected int damageDropped(int meta) {
        return meta;
    }

    
    @Override
    @SuppressWarnings( { "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (UsefullThingsType type : UsefullThingsType.values()) {
            par3List.add(new ItemStack(this, 1, type.ordinal()));
        }
    }
}
