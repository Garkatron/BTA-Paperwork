package deus.paperwork.entities.big_paperplane;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class BigPaperPlaneRenderer extends EntityRenderer<EntityBigPaperPlane> {

	ModelBigPaperPlane modelPaperPlane;
	public BigPaperPlaneRenderer(ModelBigPaperPlane model) {
		modelPaperPlane = model;
	}

	@Override
	public void render(Tessellator tessellator, EntityBigPaperPlane entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		float scale = 0.1625F;
		GL11.glTranslatef(0.0F, -24.0F * scale, 0.0F);

		this.bindTexture("/assets/paperwork/textures/entity/paperplane/texture.png");

		GL11.glEnable(32826);
		GL11.glEnable(3008);

		this.modelPaperPlane.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);

		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}
}
