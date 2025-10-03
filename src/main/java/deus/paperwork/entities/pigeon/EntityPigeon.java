package deus.paperwork.entities.pigeon;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityPigeon extends MobAnimal {
	public EntityPigeon(@Nullable World world) {
		super(world);
		this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));

	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!this.onGround && this.yd < 0.0) {
			this.yd *= 0.6;
		}
	}

	@Override
	protected void defineSynchedData() {

	}
	protected void causeFallDamage(float distance) {
	}
	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {

	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {

	}
}
