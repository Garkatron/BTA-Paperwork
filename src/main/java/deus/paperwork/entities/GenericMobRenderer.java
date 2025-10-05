package deus.paperwork.entities;

import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

public class GenericMobRenderer extends MobRenderer<Mob> {

	private final StaticEntityModel model;
	public GenericMobRenderer(StaticEntityModel model, float shadowSize) {
		super(shadowSize);
		this.model = model;
	}

	@Override
	public void render(@NotNull Tessellator tessellator, @NotNull Mob mob, double x, double y, double z, float yaw, float partialTick) {
		super.render(tessellator, mob, x, y, z, yaw, partialTick);
	}



	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull Mob mob, float v, float v1, int i) {
		return model;
	}
}
