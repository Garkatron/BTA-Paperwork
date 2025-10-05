package deus.paperwork.item;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import deus.paperwork.interfaces.IPlaceable;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.lang.reflect.InvocationTargetException;


public class PlaceableFood extends ItemFood implements IPlaceable {
	protected int amount = 1;
	protected Class<? extends Entity> entity = null;
	protected float speed = 0.55f;
	public PlaceableFood(String name, String namespaceId, int id, int healAmount, int ticksPerHeal, boolean favouriteWolfMeat, int maxStackSize) {
		super(name, namespaceId, id, healAmount, ticksPerHeal, favouriteWolfMeat, maxStackSize);
	}

	public PlaceableFood withEntity(Class<? extends Entity>  entity) {
		this.entity = entity;
		return this;
	}

	public PlaceableFood placeWithAmount(int amount){
		this.amount = amount;
		return this;
	}

	public PlaceableFood withSpeed(int speed) {
		this.speed = speed;
		return this;
	}

	@Override
	public boolean onUseItemOnBlock(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		return placeAt(world, itemstack, entityplayer, blockX, blockY, blockZ);
	}

	@Override
	public boolean placeAt(World world, ItemStack itemStack, Player player, double x, double y, double z) {

		Entity spawnedEntity = null;
		try {
			spawnedEntity = entity
				.getDeclaredConstructor(World.class)
				.newInstance(world);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

		float yaw = player.yRot * 0.017453292F;
		float pitch = player.xRot * 0.017453292F;
		float speed = this.speed;

		spawnedEntity.moveTo(player.x, player.y, player.z, player.yRot, 0);

		spawnedEntity.xd = -MathHelper.sin(yaw) * MathHelper.cos(pitch) * speed;
		spawnedEntity.yd = -MathHelper.sin(pitch) * (speed / 1.5);
		spawnedEntity.zd = MathHelper.cos(yaw) * MathHelper.cos(pitch) * speed;

		world.entityJoinedWorld(spawnedEntity);
		itemStack.consumeItem(player);
		return true;
	}
}
