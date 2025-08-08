package deus.paperwork.entities.cardboard_box;

import deus.paperwork.entities.paperplane.ModelPaperPlane;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.helper.DyeColor;
import org.lwjgl.opengl.GL11;

public class RendererCardboardBox extends EntityRenderer<EntityCardboardBox> {

	ModelCardboardBox modelCardboardBox;

	public RendererCardboardBox(ModelCardboardBox model) {
		this.modelCardboardBox = model;
	}

	@Override
	public void render(Tessellator tessellator, EntityCardboardBox entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		float scale = entity.getBoxSize().getScale();

		GL11.glScalef(scale, scale, scale);

		GL11.glTranslatef(0.0F, -24.0F, 0.0F);

		this.bindTexture("/assets/paperwork/textures/entity/cardboard_box/texture.png");

		GL11.glEnable(32826);
		GL11.glEnable(3008);

		this.modelCardboardBox.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);

		GL11.glDisable(32826);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
}
