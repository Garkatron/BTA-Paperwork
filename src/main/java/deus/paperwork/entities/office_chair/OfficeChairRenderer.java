package deus.paperwork.entities.office_chair;

import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.EntityRenderer;

public class OfficeChairRenderer extends EntityRenderer<Entity> {

	private final StaticEntityModel model;
	public OfficeChairRenderer(StaticEntityModel model) {
		super(0.3f);
		this.model = model;

	}
	@Override
	public void render(@NotNull Tessellator tessellator, @NotNull Entity entity, double x, double y, double z, float yaw, float v4) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glTranslated(x, y, z);
		GL11.glRotatef(-yaw, 0f, 1f, 0f);
		GL11.glScalef(0.0625F, 0.0625F, -0.0625F);
		this.bindTexture("/assets/paperwork/textures/entity/office_chair/office_chair.png");

		this.model.render(tessellator);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

	}
}
