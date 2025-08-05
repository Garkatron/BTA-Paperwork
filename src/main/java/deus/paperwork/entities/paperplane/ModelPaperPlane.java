package deus.paperwork.entities.paperplane;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelPaperPlane extends ModelBase {
	private final Cube bb_main;
	private final Cube wing;

	public ModelPaperPlane() {

		// Parte principal (bb_main, cuerpo)
		this.bb_main = new Cube(0, 0, 64,64);
		this.bb_main.addBox(-1.0F, -2.0F, -6.0F, 0, 2, 12);
		this.bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);

		// Ala (segundo cubo)
		this.wing = new Cube(-12, 0, 64,64); // Offset de textura corregido para el ala
		this.wing.addBox(-7.0F, -2.0F, -6.0F, 12, 0, 12);
		this.wing.setRotationPoint(0.0F, 24.0F, 0.0F); // Mismo rotationPoint que bb_main
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.bb_main.render(scale);
		this.wing.render(scale);
	}
}
