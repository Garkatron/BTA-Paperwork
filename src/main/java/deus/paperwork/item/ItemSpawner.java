package deus.paperwork.item;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

import java.lang.reflect.InvocationTargetException;

public class ItemSpawner extends Item {

	protected Class<? extends net.minecraft.core.entity.Entity> entityClass;

	public ItemSpawner(String translationKey, String namespaceId, int id, Class<? extends net.minecraft.core.entity.Entity> entityClass) {
		super(translationKey, namespaceId, id);
		this.entityClass = entityClass;
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
		try {
			Entity spawnedEntity =
				entityClass.getConstructor(World.class).newInstance(world);




				double reachDistance = (double)player.getGamemode().getBlockReachDistance();
				HitResult rayTraceResult = player.rayTrace(reachDistance, 1.0F, false, false);
				if (rayTraceResult != null) {
					if (!world.isClientSide) {
						spawnedEntity.spawnInit();
						spawnedEntity.moveTo(rayTraceResult.location.x, rayTraceResult.location.y, rayTraceResult.location.z,0, 0);
						world.entityJoinedWorld(spawnedEntity);
						itemstack.consumeItem(player);
					}

					return itemstack;
				}



		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

		return super.onUseItem(itemstack, world, player);
	}

	public void spawnEntity(ItemStack itemStack, World world, double x, double y, double z) {
		Entity entity = EntityDispatcher.createEntityInWorld(this.getEntityId(itemStack), world);
		if (entity != null) {
			entity.setPos(x, y, z);
			entity.spawnInit();
			if (itemStack.hasCustomName() && entity instanceof Mob) {
				if (itemStack.hasCustomColor()) {
					((Mob)entity).chatColor = itemStack.getCustomColor();
				}

				((Mob)entity).setNickname(itemStack.getCustomName());
			}

			world.entityJoinedWorld(entity);
		}

	}

	private String getEntityId(ItemStack itemStack) {
		String id = itemStack.getData().getString("monster");
		if (id == null || id.isEmpty()) {
			id = "Pig";
		}

		return id;
	}
}
