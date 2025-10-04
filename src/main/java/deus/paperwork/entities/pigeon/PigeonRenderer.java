package deus.paperwork.entities.pigeon;

import net.minecraft.client.render.tessellator.Tessellator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

public class PigeonRenderer extends MobRenderer<EntityPigeon> {

	StaticEntityModel modelPigeon;

	public PigeonRenderer(StaticEntityModel model) {
		super(0.2f);
		this.modelPigeon = model;

	}


	@Override
	public void render(@NotNull Tessellator tessellator, @NotNull EntityPigeon entity, double x, double y, double z, float yaw, float partialTick) {
		super.render(tessellator, entity, x, y, z, yaw, partialTick);
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull EntityPigeon entityPigeon, float v, float v1, int i) {

		return modelPigeon;
	}
}
