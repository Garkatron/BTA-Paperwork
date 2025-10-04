package deus.paperwork.entities.clippy;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.helper.DyeColor;
import org.lwjgl.opengl.GL11;

public class ClippyRenderer extends EntityRenderer<MobClippy> {

	private Modelclippy modelclippy = null;

	public ClippyRenderer(Modelclippy modelclippy) {
		this.modelclippy = modelclippy;
	}
	@Override
	public void render(Tessellator tessellator, MobClippy entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		float scale = 0.0625F;
		GL11.glTranslatef(0.0F, -24.0F * scale, 0.0F);


		if (!entity.isSitting()) {
			this.bindTexture("/assets/paperwork/textures/entity/clippy/clippy.png");

		} else {

			this.bindTexture("/assets/paperwork/textures/entity/clippy/sit_clippy.png");
		}



		GL11.glEnable(32826);
		GL11.glEnable(3008);

		this.modelclippy.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);

		GL11.glDisable(32826);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

}
