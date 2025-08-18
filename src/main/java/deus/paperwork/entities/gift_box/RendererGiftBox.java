package deus.paperwork.entities.gift_box;

import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import deus.paperwork.entities.cardboard_box.ModelCardboardBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.helper.DyeColor;
import org.lwjgl.opengl.GL11;

public class RendererGiftBox extends EntityRenderer<EntityGiftBox> {

	private final Minecraft mc = Minecraft.getMinecraft();
	ModelGiftBox modelCardboardBox;

	public RendererGiftBox(ModelGiftBox model) {
		this.modelCardboardBox = model;
	}

	@Override
	public void render(Tessellator tessellator, EntityGiftBox entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		float scale = entity.getBoxSize().getScale();

		GL11.glScalef(scale, scale, scale);

		GL11.glTranslatef(0.0F, -24.0F, 0.0F);


		DyeColor color = entity.getColor();
//		if (color == null) {
//			if (entity.halloween) {
//				this.bindTexture("/assets/paperwork/textures/entity/cardboard_box/halloween/pumpkin.png");
//			} else {
//				this.bindTexture("/assets/paperwork/textures/entity/gift_box/red.png");
//			}
//		} else {
//			this.bindTexture("/assets/paperwork/textures/entity/cardboard_box/gift_box/"+color.colorID+".png");
//		}
		this.bindTexture("/assets/paperwork/textures/cardboard_box/entity/gift_box/test.png");
		GL11.glEnable(32826);
		GL11.glEnable(3008);

		this.modelCardboardBox.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);

		GL11.glDisable(32826);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}


}
