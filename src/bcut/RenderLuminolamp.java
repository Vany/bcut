package bcut;

import org.lwjgl.opengl.GL11;

import buildcraft.core.DefaultProps;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderLuminolamp extends TileEntitySpecialRenderer {
	
	private ModelBase model = new ModelBase() {};
	private ModelRenderer box;
	private String baseTexture;

	public RenderLuminolamp() {
		box = new ModelRenderer(model, 0, 0);
		box.addBox(-8F, 1F, -8F, 16, 7, 16);
		box.rotationPointX = 8F;
		box.rotationPointY = 8F;
		box.rotationPointZ = 8F;
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x, y, z);
		ForgeHooksClient.bindTexture("/gfx/luminolamp.png", 0);
		box.render((float) (1.0 / 16.0));

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();		
	}

}
