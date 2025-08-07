package deus.paperwork.entities.cardboard_box;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelCardboardBox extends ModelBase {
	private final Cube bb_main;

	public ModelCardboardBox() {
		this.bb_main = new Cube(0, 0, 64, 64);
		this.bb_main.addBox(-7.0F, -14.0F, -7.0F, 14, 14, 14);
		this.bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.bb_main.render(scale);
	}
}
