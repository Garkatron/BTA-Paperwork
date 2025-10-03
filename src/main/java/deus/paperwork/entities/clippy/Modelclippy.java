package deus.paperwork.entities.clippy;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class Modelclippy extends ModelBase {
	private final Cube bb_main;

	public Modelclippy() {
		int texWidth = 64;
		int texHeight = 64;

		bb_main = new Cube(2, 2, texWidth, texHeight);
		bb_main.addBox(-8.0F, -24.0F, 0.0F, 16, 24, 0);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bb_main, 0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.bb_main.render(scale);
	}

	private void setRotationAngle(Cube cube, float x, float y, float z) {
		cube.xRot = x;
		cube.yRot = y;
		cube.zRot = z;
	}
}
