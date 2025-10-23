package deus.paperwork.entities.pigeon;

import deus.paperwork.Paperwork;
import net.minecraft.client.render.tessellator.Tessellator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.util.vector.Vector3f;
import org.useless.dragonfly.animation.Animation;
import org.useless.dragonfly.animation.AnimationData;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.models.entity.mojang.StaticEntityModelMojang;
import org.useless.dragonfly.renderer.MobRenderer;
import org.useless.util.AnimationHelper;

import java.util.List;

public class PigeonRenderer extends MobRenderer<EntityPigeon> {

	private final StaticEntityModelMojang modelPigeon;
	private final List<Animation> animations;
	private final Animation walk;

	public PigeonRenderer(StaticEntityModel  model, List<Animation> animations) {
		super(0.2f);
		this.modelPigeon = (StaticEntityModelMojang) model;
		this.animations = animations;
		this.walk = animations.get(0);
	}
	@Override
	public void render(@NotNull Tessellator tessellator, @NotNull EntityPigeon entity,
					   double x, double y, double z, float yaw, float partialTick) {
		super.render(tessellator, entity, x, y, z, yaw, partialTick);;
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(
		@NotNull EntityPigeon entityPigeon, float brightness, float partialTick, int layer) {

		modelPigeon.resetBones();

		float limbSwing = getLimbSwing(entityPigeon, partialTick);
		float limbYaw = getLimbYaw(entityPigeon, partialTick);

		if (!animations.isEmpty()) {

			// animateWalk(modelPigeon, walk, limbSwing, limbYaw, 2.0f, 2.5f);
		}
		return modelPigeon;
	}


	protected void animateWalk(StaticEntityModelMojang staticEntityModel, Animation animationData, float limbSwing, float limbYaw, float speed, float scale) {
		long time = (long) (limbSwing * 50.0f * speed);
		float clampedScale = Math.min(limbYaw * scale, 1.0f);
		AnimationHelper.animate(staticEntityModel, animationData, time, clampedScale, new Vector3f(0, 0, 0));
	}
}
