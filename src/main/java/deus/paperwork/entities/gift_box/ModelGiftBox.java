package deus.paperwork.entities.gift_box;



import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
public class ModelGiftBox extends ModelBase {
	private final Cube bb_main;
	private final Cube cover;
	private final Cube gift_cow_r1;

	public ModelGiftBox() {

		int texWidth = 64;
		int texHeight = 64;

		bb_main = new Cube(0, 37, texWidth, texHeight);
		bb_main.addBox(-7.0F, -9.0F, -7.0F, 14, 9, 14);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bb_main, 0.0F, 0.0F, 0.0F);

		cover = new Cube(0, 0, texWidth, texHeight);
		cover.addBox(-8.0F, -14.0F, -8.0F, 16, 5, 16);
		cover.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(cover, 0.0F, 0.0F, 0.0F);

		gift_cow_r1 = new Cube(0, 21, texWidth, texHeight);
		gift_cow_r1.addBox(-8.0F, 0.0F, -8.0F, 16, 0, 16);
		gift_cow_r1.setRotationPoint(0.0F, 9.0F, 0.0F);
		setRotationAngle(gift_cow_r1, -0.2182F, 0.0F, 0.0F);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.bb_main.render(scale);
		this.cover.render(scale);
		this.gift_cow_r1.render(scale);
	}

	private void setRotationAngle(Cube cube, float x, float y, float z) {
		cube.xRot = x;
		cube.yRot = y;
		cube.zRot = z;
	}
}
