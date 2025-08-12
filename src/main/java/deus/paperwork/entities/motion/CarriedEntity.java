package deus.paperwork.entities.motion;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.Paperwork;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.ICarriable;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;

public class CarriedEntity implements ICarriable {

	private @Nullable Entity carried;
	private @NotNull Entity holder;
	private @NotNull World world;

	public CarriedEntity(@NotNull Entity holder, @NotNull Entity carried) {
		this.holder = holder;
		this.carried = carried;
		this.world = holder.world;
		assert this.world != null : "World reference must not be null!";
	}

	protected CarriedEntity(@NotNull Entity holder) {
		this.holder = holder;
		this.world = holder.world;
		assert this.world != null : "World reference must not be null!";
	}

	@Override
	public void heldTick(World world, Entity holder) {
		if (this.carried != null) {
			// this.carried.tick();
		}
	}

	@Override
	public boolean tryPlace(World world, Entity holder, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		if (world.isClientSide) return false;

		Entity newEntity = recreateEntity();
		if (newEntity != null) {
			double px = blockX + side.getOffsetX() + 0.5;
			double py = blockY + side.getOffsetY();
			double pz = blockZ + side.getOffsetZ() + 0.5;

			newEntity.moveTo(px, py, pz, holder.yRot, holder.xRot);
			world.entityJoinedWorld(newEntity);
			return true;
		}
		return false;
	}

	public @Nullable Entity getCarried() {
		return carried;
	}

	@Override
	public void drop(World world, Entity holder) {
		int baseX = MathHelper.floor(holder.x);
		int baseY = MathHelper.floor(holder.y);
		int baseZ = MathHelper.floor(holder.z);

		// Intentar colocar cerca del portador
		for (int y = baseY - 1; y <= baseY + 1; y++) {
			for (int x = baseX - 1; x <= baseX + 1; x++) {
				for (int z = baseZ - 1; z <= baseZ + 1; z++) {
					if (tryPlace(world, holder, x, y, z, Side.TOP, 0.0, 0.0)) {
						return;
					}
				}
			}
		}

		Paperwork.LOGGER.warn("[{}] Can't spawn the entity.", getClass().getSimpleName());
		Entity newEntity = recreateEntity();
		if (newEntity != null) {
			newEntity.moveTo(holder.x, holder.y, holder.z, 0, 0);
			world.entityJoinedWorld(newEntity);
		}
	}

	@Override
	public boolean canBeCarried(World world, Entity potentialHolder) {
		return true;
	}

	@Override
	public ICarriable pickup(World world, Entity holder) {
		return this;
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		if (this.carried != null) {
			CompoundTag entityTag = new CompoundTag();
			this.carried.addAdditionalSaveData(entityTag);
			tag.put("entity", entityTag);
		}
		tag.putString("type", "entity");
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		if (tag.containsKey("entity")) {
			this.carried = EntityDispatcher.createEntityFromNBT(tag.getCompound("entity"), world);
		}
	}

	public static CarriedEntity createAndLoad(@NotNull Entity holder, CompoundTag tag) {
		CarriedEntity carriedEntity = new CarriedEntity(holder);
		carriedEntity.readFromNBT(tag);
		return carriedEntity;
	}


	private Entity recreateEntity() {
		if (this.carried == null) return null;
		try {
			Constructor<? extends Entity> constructor = carried.getClass().getDeclaredConstructor(World.class);
			constructor.setAccessible(true);
			Entity newEntity = constructor.newInstance(world);

			CompoundTag data = new CompoundTag();
			carried.addAdditionalSaveData(data);
			newEntity.readAdditionalSaveData(data);

			newEntity.spawnInit();
			return newEntity;
		} catch (ReflectiveOperationException e) {
			Paperwork.LOGGER.error("[{}] Error instancing the entity: {}", getClass().getSimpleName(), carried.getClass().getName(), e);
			return null;
		}
	}
}
