package deus.paperwork.entities.cardboard_box;

import deus.paperwork.Paperwork;
import deus.paperwork.entities.gift_box.ModelGiftBox;
import deus.paperwork.entities.paperplane.ModelPaperPlane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Constructor;

public class RendererCardboardBox extends EntityRenderer<EntityCardboardBox> {

	private final Minecraft mc = Minecraft.getMinecraft();
	ModelCardboardBox modelCardboardBox = new ModelCardboardBox();
	ModelGiftBox modelGiftBox = new ModelGiftBox();

	public RendererCardboardBox() {
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


		DyeColor color = entity.getColor();
		if (color == null) {
			if (entity.halloween) {
				this.bindTexture("/assets/paperwork/textures/entity/cardboard_box/halloween/pumpkin.png");
			} else {
				this.bindTexture("/assets/paperwork/textures/entity/cardboard_box/texture.png");
			}
		} else {
			this.bindTexture("/assets/paperwork/textures/entity/cardboard_box/gift_box/"+color.colorID+".png");
		}

		GL11.glEnable(32826);
		GL11.glEnable(3008);

		if (color == null) {
			this.modelCardboardBox.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
		} else {
			this.modelGiftBox.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
		}

		GL11.glDisable(32826);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}


}
