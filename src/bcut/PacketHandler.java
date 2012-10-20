package bcut;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;


public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
//		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
//		int x = dat.readInt();
//		int y = dat.readInt();
//		int z = dat.readInt();
//		byte facing = dat.readByte();
//		World w = UsefullThings.proxy.getClientWorld();
//		TileEntity te = w.getBlockTileEntity(x, y, z);
//		if (te instanceof TileUsefullThings) {
//			((TileUsefullThings) te).handlePacketData(facing);
//		}
	}

	
}
