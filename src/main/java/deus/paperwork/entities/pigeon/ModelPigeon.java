package deus.paperwork.entities.pigeon;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelPigeon extends ModelBase {
	private final Cube body;
	private final Cube head;
	private final Cube left_leg;
	private final Cube right_leg;
	private final Cube wing1;
	private final Cube wing2;
	private final Cube tail;
	private final Cube right_foot;
	private final Cube left_foot;
	private final Cube beak;
	private final Cube beak2;

	public ModelPigeon() {
		// Cuerpo (body + body_r1)
		this.body = new Cube(0, 0, 32, 32);
		this.body.addBox(-1.0F, -4.0F, 0.0F, 3, 3, 5);
		this.body.setRotationPoint(-0.5F, 20.25F, -5.0F); // -1 + 0.5, 27.25-7, -2-3
		this.body.xRot = -((float)Math.PI / 4F); // -0.7854 rad

		// Cabeza
		this.head = new Cube(16, 0, 32, 32);
		this.head.addBox(0.0F, -11.5F, -1.0F, 2, 3, 2);
		this.head.setRotationPoint(-1.0F, 27.25F, -2.0F);

		// Pierna izquierda
		this.left_leg = new Cube(16, 11, 32, 32);
		this.left_leg.addBox(-0.5F, -4.25F, 1.25F, 1, 1, 0);
		this.left_leg.setRotationPoint(-1.0F, 27.25F, -2.0F);

		// Pierna derecha
		this.right_leg = new Cube(16, 12, 32, 32);
		this.right_leg.addBox(1.5F, -4.25F, 1.25F, 1, 1, 0);
		this.right_leg.setRotationPoint(-1.0F, 27.25F, -2.0F);

		// Ala izquierda (wing1 + wing1_r1)
		this.wing1 = new Cube(0, 8, 32, 32);
		this.wing1.addBox(-1.0F, -5.0F, 0.0F, 1, 5, 3);
		this.wing1.setRotationPoint(-1.0F, 22.25F, 1.25F); // -1+0, 26.25-4, -1+2.25
		this.wing1.xRot = (3F * (float)Math.PI) / 8F; // 1.1781 rad

		// Ala derecha (wing2 + wing2_r1)
		this.wing2 = new Cube(8, 8, 32, 32);
		this.wing2.addBox(-1.0F, -5.0F, 0.0F, 1, 5, 3);
		this.wing2.setRotationPoint(2.0F, 22.25F, 1.25F); // -1+3, 27.25-5, -1.75+3
		this.wing2.xRot = (3F * (float)Math.PI) / 8F;

		// Cola
		this.tail = new Cube(0, 16, 32, 32);
		this.tail.addBox(0.0F, -6.5F, 3.0F, 2, 1, 3);
		this.tail.setRotationPoint(-1.0F, 27.25F, -2.0F);

		// Pie derecho
		this.right_foot = new Cube(10, 16, 32, 32);
		this.right_foot.addBox(1.5F, -2.25F, 0.25F, 1, 0, 1);
		this.right_foot.setRotationPoint(-1.0F, 26.25F, -2.0F);

		// Pie izquierdo
		this.left_foot = new Cube(16, 10, 32, 32);
		this.left_foot.addBox(-0.5F, -3.25F, 0.25F, 1, 0, 1);
		this.left_foot.setRotationPoint(-1.0F, 27.25F, -2.0F);

		// Pico base
		this.beak = new Cube(16, 5, 32, 32);
		this.beak.addBox(0.5F, -11.0F, -1.5F, 1, 2, 1);
		this.beak.setRotationPoint(-1.0F, 27.25F, -2.0F);

		// Pico punta
		this.beak2 = new Cube(16, 8, 32, 32);
		this.beak2.addBox(0.5F, -10.25F, -2.5F, 1, 1, 1);
		this.beak2.setRotationPoint(-1.0F, 27.25F, -2.0F);
	}

	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.wing1.zRot = limbPitch;
		this.wing2.zRot = -limbPitch;
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {

		this.body.render(scale);
		this.head.render(scale);
		this.left_leg.render(scale);
		this.right_leg.render(scale);
		this.wing1.render(scale);
		this.wing2.render(scale);
		this.tail.render(scale);
		this.right_foot.render(scale);
		this.left_foot.render(scale);
		this.beak.render(scale);
		this.beak2.render(scale);
	}
}
