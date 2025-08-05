package deus.paperwork.entities.paperplane;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class PaperPlaneRenderer extends EntityRenderer<EntityPaperPlane> {

	ModelPaperPlane modelPaperPlane;
	public PaperPlaneRenderer(ModelPaperPlane model) {
		modelPaperPlane = model;
	}

	@Override
	public void render(Tessellator tessellator, EntityPaperPlane entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		// Posicionar el modelo
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F); // Rotar según yaw
		GL11.glScalef(-1.0F, -1.0F, 1.0F); // Escala para orientación
		float scale = 0.0625F; // Escala estándar de Minecraft
		GL11.glTranslatef(0.0F, -24.0F * scale, 0.0F); // Ajuste vertical

		// Cargar textura (ajusta la ruta según tu mod)
		this.bindTexture("/assets/paperwork/textures/entity/paperplane/texture.png");

		// Habilitar configuraciones de renderizado
		GL11.glEnable(32826); // GL_RESCALE_NORMAL
		GL11.glEnable(3008);  // GL_ALPHA_TEST

		// Renderizar el modelo
		this.modelPaperPlane.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);

		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}
}
