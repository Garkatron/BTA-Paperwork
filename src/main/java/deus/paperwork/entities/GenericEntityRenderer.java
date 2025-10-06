package deus.paperwork.entities;

import net.minecraft.client.render.tessellator.Tessellator;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.EntityRenderer;

public class GenericEntityRenderer extends EntityRenderer<TexturedEntity> {

	private final StaticEntityModel model;
	public GenericEntityRenderer(StaticEntityModel staticEntityModel) {
		super(0.2f);
		this.model = staticEntityModel;
	}

	@Override
	public void render(@NotNull Tessellator tessellator, @NotNull TexturedEntity texturedEntity, double x, double y, double z, float yaw, float v4) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glTranslated(x, y, z);
		GL11.glRotatef(-yaw, 0f, 1f, 0f);
		GL11.glScalef(0.0625F, 0.0625F, -0.0625F);


		this.bindTexture(texturedEntity.getEntityTexture());

		this.model.render(tessellator);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

	}
}
