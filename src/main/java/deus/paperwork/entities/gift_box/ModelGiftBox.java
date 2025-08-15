package deus.paperwork.entities.gift_box;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelGiftBox extends ModelBase {
	private final Cube bb_main;
	private final Cube lid;
	private final Cube ribbon;

	public ModelGiftBox() {
		this.bb_main = new Cube(0, 37, 64, 64);
		this.bb_main.addBox(-7.0F, -9.0F, -7.0F, 14, 9, 14);
		this.bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);

		this.lid = new Cube(0, 0, 64, 64);
		this.lid.addBox(-8.0F, -14.0F, -8.0F, 16, 5, 16);
		this.lid.setRotationPoint(0.0F, 24.0F, 0.0F);


		this.ribbon = new Cube(0, 21, 64, 64);
		this.ribbon.addBox(-8.0F, -15.5F, -13.0F, 16, 0, 16);
		this.ribbon.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.ribbon.xRot = -0.3927F;
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.bb_main.render(scale);
		this.lid.render(scale);
		this.ribbon.render(scale);
	}
}
