package deus.paperwork.entities.pigeon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobPigeonRenderer extends MobRenderer<MobPigeon> {

	public MobPigeonRenderer(float shadowSize) {
		super(shadowSize);
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull MobPigeon entity, float brightness, float partialTick, int layer) {
		StaticEntityModel model = this.getModel("main");
		model.resetBones();

		float limbSwing = this.getLimbSwing(entity, partialTick);
		float limbYaw = this.getLimbYaw(entity, partialTick);
		float limbPitch = this.getLimbPitch(entity, partialTick);

		BoneTransform leg0 = model.getTransform("leg0");
		BoneTransform leg1 = model.getTransform("leg1");
		BoneTransform wing0 = model.getTransform("wing0");
		BoneTransform wing1 = model.getTransform("wing1");

		if (leg0 != null) {
			leg0.rotX = (double)(MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);
		}
		if (leg1 != null) {
			leg1.rotX = (double)(MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbYaw);
		}

		if (wing0 != null) {
			wing0.rotZ = (double)limbPitch;
		}
		if (wing1 != null) {
			wing1.rotZ = (double)(-limbPitch);
		}

		return model;
	}

	protected float getLimbPitch(@NotNull MobPigeon entity, float partialTick) {
		float flap = MathHelper.lerp(entity.oFlap, entity.flap, partialTick);
		float flapSpeed = MathHelper.lerp(entity.oFlapSpeed, entity.flapSpeed, partialTick);
		return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
	}
}
