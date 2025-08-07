package deus.paperwork.item;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;

import java.lang.reflect.InvocationTargetException;

public class ItemCardboardBox extends Item {
	public ItemCardboardBox(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}



	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
		Entity spawnedEntity = new EntityCardboardBox(world);


		CompoundTag tag = itemstack.getData();

		if (tag.containsKey("Items")) {
			CompoundTag loadTag = new CompoundTag();
			loadTag.put("Items", tag.getTag("Items"));
			spawnedEntity.readAdditionalSaveData(loadTag);
		}


		if (player.isSneaking()) {

			float yaw = player.yRot * 0.017453292F;
			float pitch = player.xRot * 0.017453292F;
			float speed = 1.25F;

			spawnedEntity.moveTo(player.x, player.y, player.z, player.yRot, 0);


			spawnedEntity.xd = -MathHelper.sin(yaw) * MathHelper.cos(pitch) * speed;
			spawnedEntity.yd = -MathHelper.sin(pitch) * (speed / 1.5);
			spawnedEntity.zd = MathHelper.cos(yaw) * MathHelper.cos(pitch) * speed;

			world.entityJoinedWorld(spawnedEntity);
			itemstack.consumeItem(player);
		} else {
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
		}

		return super.onUseItem(itemstack, world, player);
	}
}
