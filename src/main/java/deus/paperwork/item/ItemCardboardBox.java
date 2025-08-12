package deus.paperwork.item;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.block.cardboard_box_trap.TileEntityCardboardBoxTrap;
import deus.paperwork.entities.cardboard_box.BoxSize;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ItemCardboardBox extends Item {

	private final BoxSize boxSize;

	public ItemCardboardBox(String translationKey, String namespaceId, int id, BoxSize boxSize) {
		super(translationKey, namespaceId, id);
		this.boxSize = boxSize;
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {

		if(!player.isSneaking()) {
			EntityCardboardBox spawnedEntity = new EntityCardboardBox(world);

			spawnedEntity.setBoxSize(this.boxSize);
			CompoundTag tag = itemstack.getData();

			if (tag.containsKey("Items")) {
				CompoundTag loadTag = new CompoundTag();
				loadTag.put("Items", tag.getTag("Items"));
				spawnedEntity.readAdditionalSaveData(loadTag);
			}


			float yaw = player.yRot * 0.017453292F;
			float pitch = player.xRot * 0.017453292F;
			float speed = 1.25F;

			spawnedEntity.moveTo(player.x, player.y, player.z, player.yRot, 0);

			spawnedEntity.xd = -MathHelper.sin(yaw) * MathHelper.cos(pitch) * speed;
			spawnedEntity.yd = -MathHelper.sin(pitch) * (speed / 1.5);
			spawnedEntity.zd = MathHelper.cos(yaw) * MathHelper.cos(pitch) * speed;

			world.entityJoinedWorld(spawnedEntity);
			itemstack.consumeItem(player);
		}

		return super.onUseItem(itemstack, world, player);
	}

	@Override
	public boolean onUseItemOnBlock(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
//		if (world.getBlockId(blockX,blockY,blockZ)==PaperworkBlocks.BLOCK_CARDBOARD_BOX_TRAP.id()) return false;
//		if (entityplayer.isSneaking()) {
//			int id = world.getBlockId(blockX, blockY, blockZ);
//			int meta = world.getBlockMetadata(blockX, blockY, blockZ);
//			TileEntity tileEntity = world.getTileEntity(blockX, blockY, blockZ);
//
//			TileEntityCardboardBoxTrap tileEntityCardboardBoxTrap = new TileEntityCardboardBoxTrap();
//			tileEntityCardboardBoxTrap.setSavedBlockId(id);
//			tileEntityCardboardBoxTrap.setSavedBlockMeta(meta);
//			if (tileEntity != null) {
//				try {
//					Constructor<? extends TileEntity> constructor = tileEntity.getClass().getDeclaredConstructor();
//					constructor.setAccessible(true);
//					TileEntity t = constructor.newInstance();
//
//					CompoundTag compoundTag = new CompoundTag();
//					tileEntity.writeToNBT(compoundTag);
//					t.readFromNBT(compoundTag);
//					tileEntityCardboardBoxTrap.setTileEntity(t);
//				} catch (Exception e) {
//					e.printStackTrace();
//					return false;
//				}
//			}
//
//			world.setBlockRaw(blockX, blockY, blockZ, PaperworkBlocks.BLOCK_CARDBOARD_BOX_TRAP.id());
//			world.removeBlockTileEntity(blockX, blockY, blockZ);
//			world.markBlockNeedsUpdate(blockX, blockY, blockZ);
//
//			world.setTileEntity(blockX, blockY, blockZ, tileEntityCardboardBoxTrap);
//			return true;
//		} else {
//			return false;
//		}

		if (entityplayer.isSneaking()) {
			EntityCardboardBox entityCardboardBox = new EntityCardboardBox(world);
			int id = world.getBlockId(blockX, blockY, blockZ);
			int meta = world.getBlockMetadata(blockX, blockY, blockZ);
			entityCardboardBox.setSavedBlockId(id);
			entityCardboardBox.setSavedBlockMeta(meta);
			TileEntity tileEntity = world.getTileEntity(blockX, blockY, blockZ);

			if (tileEntity != null) {
				try {
					Constructor<? extends TileEntity> constructor = tileEntity.getClass().getDeclaredConstructor();
					constructor.setAccessible(true);
					TileEntity t = constructor.newInstance();

					CompoundTag compoundTag = new CompoundTag();
					tileEntity.writeToNBT(compoundTag);
					t.readFromNBT(compoundTag);
					entityCardboardBox.setTileEntity(t);
					world.removeBlockTileEntity(blockX, blockY, blockZ);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			world.setBlockWithNotify(blockX,blockY,blockZ,0);
			entityCardboardBox.moveTo(blockX, blockY, blockZ, 0, 0);
			entityCardboardBox.spawnInit();
			world.entityJoinedWorld(entityCardboardBox);
		}

		return false;
	}

	@Override
	public boolean useItemOnEntity(ItemStack itemstack, Mob mob, Player player) {
		if (player.isSneaking() && this.boxSize.compareTo(BoxSize.REGULAR) > 0) {
			EntityCardboardBox cardboardBox = new EntityCardboardBox(player.world);
			cardboardBox.setBoxSize(this.boxSize);
			cardboardBox.setEntity(mob);
			mob.remove();
			cardboardBox.moveTo(mob.x, mob.y, mob.z, 0, 0);
			cardboardBox.spawnInit();

			player.world.entityJoinedWorld(cardboardBox);
		}

		return super.useItemOnEntity(itemstack, mob, player);
	}

}
