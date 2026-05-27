package deus.paperwork.entities.employee;

import net.minecraft.client.render.entity.MobRendererBiped;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

public class MobEmployeeFarmerRenderer extends MobRendererBiped<MobEmployee> {
	public MobEmployeeFarmerRenderer() {
		super(0.3f);
	}

	@Override
	protected @Nullable StaticEntityModel getActiveModel(@NonNull MobEmployee mobEmployee) {
		return this.getModel("main");
	}

	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobEmployee entity, float brightness, float partialTick, int layer) {
		this.bindTexture("/assets/paperwork/textures/entity/employee_farmer/" + entity.getTextureReference() + ".png");

		return super.getAndSetupModelForLayer(entity, brightness, partialTick, layer);
	}


}
