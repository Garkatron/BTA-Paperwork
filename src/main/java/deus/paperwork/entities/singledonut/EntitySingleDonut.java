package deus.paperwork.entities.singledonut;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.entities.EntityFood;
import deus.paperwork.entities.GenericEntityRenderer;
import deus.utils.annotations.RegisterDragonflyModel;
import deus.utils.annotations.RegisterEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static deus.paperwork.Paperwork.MOD_ID;

@RegisterDragonflyModel(id = "geometry."+MOD_ID+".singledonut", renderer = GenericEntityRenderer.class)
@RegisterEntity(modId = MOD_ID, id = "singledonut", name = "singledonut")
public class EntitySingleDonut extends EntityFood {
	public EntitySingleDonut(@Nullable World world) {
		super(world);
		texturePath = "/assets/paperwork/textures/entity/singledonut/singledonut.png";
		setSize(0.3f, 0.3f);
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {

	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {

	}
}
