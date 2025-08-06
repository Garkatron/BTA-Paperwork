package deus.paperwork.entities.paperplane;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class PaperPlaneRenderer extends EntityRenderer<EntityPaperPlane> {

	ModelPaperPlane modelPaperPlane;

	public PaperPlaneRenderer(ModelPaperPlane model) {
		this.modelPaperPlane = model;
	}

	@Override
	public void render(Tessellator tessellator, EntityPaperPlane entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		float scale = 0.0625F;
		GL11.glTranslatef(0.0F, -24.0F * scale, 0.0F);

		switch (entity.getWetState()) {
			case DRY:
				this.bindTexture("/assets/paperwork/textures/entity/paperplane/big_texture.png");
				break;
			case WET:
				this.bindTexture("/assets/paperwork/textures/entity/paperplane/big_wet_texture.png");
				break;
			case VERY_WET:
				this.bindTexture("/assets/paperwork/textures/entity/paperplane/big_very_wet_texture.png");
				break;
		}

		GL11.glEnable(32826); // GL_RESCALE_NORMAL
		GL11.glEnable(3008);  // GL_ALPHA_TEST

		this.modelPaperPlane.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);

		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}
}
