package deus.paperwork.entities.paperplane;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL11;

public class PaperPlaneRenderer extends EntityRenderer<EntityPaperPlane> {




	private final String DEFAULT_TEXTURE = "/assets/paperwork/textures/entity/paperplane/paperplane.png";

	@Override
	public void render(@NotNull TessellatorGeneral tessellatorGeneral, @NonNull EntityPaperPlane entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		float scale = 0.0625F;
		GL11.glTranslatef(0.0F, -24.0F * scale, 0.0F);

		String currentTexture = DEFAULT_TEXTURE;

		DyeColor color = entity.getColor();
		if (color != null) {
			currentTexture = "/assets/paperwork/textures/entity/paperplane/" + entity.getColor().colorID + ".png";
		}

		this.bindTexture(currentTexture);

		switch (entity.getWetState()) {
			case DRY:
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				break;
			case WET:
				GL11.glColor3f(0.7F, 0.7F, 0.7F);
				break;
			case VERY_WET:
				GL11.glColor3f(0.4F, 0.4F, 0.4F);
				break;
		}

		GL11.glEnable(32826);
		GL11.glEnable(3008);

		// this.modelPaperPlane.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);

		GL11.glDisable(32826);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	}


