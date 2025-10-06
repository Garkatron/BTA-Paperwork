package deus.paperwork.entities.singledonut;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.entities.EntityFood;
import deus.paperwork.entities.GenericEntityRenderer;
import deus.utils.annotations.RegisterDragonflyModel;
import deus.utils.annotations.RegisterEntity;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static deus.paperwork.Paperwork.MOD_ID;

@RegisterDragonflyModel(id = "geometry."+MOD_ID+".donut", renderer = GenericEntityRenderer.class)
@RegisterEntity(modId = MOD_ID, id = "donut", name = "donut")
public class EntityDonut extends EntityFood {
	public EntityDonut(@Nullable World world) {
		super(world);
		textureIdentifier = NamespaceID.getPermanent(MOD_ID, "donut");
		sound = MOD_ID + ":entity.donut.eaten";
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
