package deus.paperwork.entities.pigeon;

import deus.paperwork.entities.paperplane.EntityPaperPlane;
import deus.paperwork.entities.paperplane.ModelPaperPlane;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.animal.MobChicken;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

public class PigeonRenderer extends MobRenderer<EntityPigeon> {

	ModelPigeon modelPigeon;
	private final String DEFAULT_TEXTURE = "/assets/paperwork/textures/entity/pigeon/blue.png";

	public PigeonRenderer(ModelPigeon model) {
		super(model, 0.2f);
		this.modelPigeon = model;
	}

	@Override
	protected float limbSway(EntityPigeon entity, float partialTick) {
		float flap = entity.oFlap + (entity.flap - entity.oFlap) * partialTick;
		float flapSpeed = entity.oFlapSpeed + (entity.flapSpeed - entity.oFlapSpeed) * partialTick;
		return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
	}

	@Override
	public void render(Tessellator tessellator, EntityPigeon entity, double x, double y, double z, float yaw, float partialTick) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		float scale = 0.0625F;
		GL11.glTranslatef(0.0F, -24.0F * scale, 0.0F);




		this.bindTexture(DEFAULT_TEXTURE);


		GL11.glEnable(32826);
		GL11.glEnable(3008);


		this.modelPigeon.render(0.0F, 0.0F, limbSway(entity, partialTick), 0.0F, 0.0F, scale);

		GL11.glDisable(32826);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
}
