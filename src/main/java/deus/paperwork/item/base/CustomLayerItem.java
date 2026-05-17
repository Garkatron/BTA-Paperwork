package deus.paperwork.item.base;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerBase;
import net.minecraft.core.item.Item;

public class CustomLayerItem extends Item {
	private final Block<? extends BlockLogicLayerBase> block;

	public CustomLayerItem(String translationKey, String namespaceId, int id, Block<? extends BlockLogicLayerBase> block) {
		super(translationKey, namespaceId, id);
		this.block = block;
	}

//	@Override
//	public boolean onUseOnBlock(@NotNull ItemStack selfStack, @NotNull World world, @Nullable Player player, @NotNull TilePosc blockPos, @NotNull Side side, double xHit, double yHit) {
//		Block<?> targetBlock = world.getBlock(blockPos.x(), blockPos.y(), blockPos.z());
//		int meta = world.getBlockMetadata(blockPos.x(), blockPos.y(), blockPos.z());
//		if (targetBlock != this.block && targetBlock != null && targetBlock.hasTag(BlockTags.PLACE_OVERWRITES)) {
//			targetBlock = null;
//			meta = 0;
//		}
//
//		if (selfStack.stackSize <= 0) {
//			return false;
//		} else if (blockPos.y() == world.getHeightBlocks() - 1 && this.block.getMaterial().isSolid()) {
//			return false;
//		} else {
//			int newMeta;
//			AABB bbBox;
//			BlockLogicLayerBase blockLayer;
//			if (targetBlock == this.block && side == Side.TOP) {
//				blockLayer = (BlockLogicLayerBase)this.block.getLogic();
//				newMeta = meta + 1;
//				bbBox = AABB.getTemporaryBB(blockPos.x(), blockPos.y(), blockPos.z(), ((float)blockPos.x() + 1.0F), ((float)blockPos.y() + (float)(2 * (newMeta + 1)) / 16.0F), ((float)blockPos.z() + 1.0F));
//				if (!world.checkIfAABBIsClear(bbBox.asJomlAABB())) {
//					return false;
//				} else {
//					if (newMeta < 7) {
//						world.setBlockAndMetadataWithNotify(blockPos.x(), blockPos.y(), blockPos.z(), this.block.id(), newMeta);
//					} else if (blockLayer.fullBlock != null) {
//						world.setBlockAndMetadataWithNotify(blockPos.x(), blockPos.y(), blockPos.z(), blockLayer.fullBlock.id(), 0);
//					} else {
//						world.setBlockAndMetadataWithNotify(blockPos.x(), blockPos.y() + 1, blockPos.z(), this.block.id(), 0);
//					}
//
//					world.playBlockSoundEffect(player, ((float)blockPos.x() + 0.5F), ((float)blockPos.y() + 0.5F), ((float)blockPos.z() + 0.5F), this.block, EnumBlockSoundEffectType.PLACE);
//					selfStack.consumeItem(player);
//					return true;
//				}
//			} else {
//				if (targetBlock != null) {
//					blockX += side.offsetX();
//					blockY += side.offsetY();
//					blockZ += side.offsetZ();
//					targetBlock = world.getBlock(blockX, blockY, blockZ);
//					meta = world.getBlockMetadata(blockX, blockY, blockZ);
//				}
//
//				if (targetBlock == this.block) {
//					blockLayer = (BlockLogicLayerBase)this.block.getLogic();
//					newMeta = meta + 1;
//					bbBox = AABB.getTemporaryBB((double)blockX, (double)blockY, (double)blockZ, (double)((float)blockX + 1.0F), (double)((float)blockY + (float)(2 * (newMeta + 1)) / 16.0F), (double)((float)blockZ + 1.0F));
//					if (!world.checkIfAABBIsClear(bbBox)) {
//						return false;
//					} else {
//						if (newMeta < 7) {
//							world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.block.id(), newMeta);
//						} else if (blockLayer.fullBlock != null) {
//							world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, blockLayer.fullBlock.id(), 0);
//						} else {
//							world.setBlockAndMetadataWithNotify(blockX, blockY + 1, blockZ, this.block.id(), 0);
//						}
//
//						world.playBlockSoundEffect(player, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), this.block, EnumBlockSoundEffectType.PLACE);
//						itemstack.consumeItem(player);
//						return true;
//					}
//				} else {
//					if (world.canBlockBePlacedAt(this.block.id(), blockX, blockY, blockZ, false, side)) {
//						int placeMeta = this.block.getPlacedBlockMetadata(player, itemstack, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
//						if (world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.block.id(), placeMeta)) {
//							if (player == null) {
//								this.block.onBlockPlacedOnSide(world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
//							} else {
//								this.block.onBlockPlacedByMob(world, blockX, blockY, blockZ, Side.NONE, player, xPlaced, yPlaced);
//							}
//
//							world.playBlockSoundEffect(player, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), this.block, EnumBlockSoundEffectType.PLACE);
//							itemstack.consumeItem(player);
//							return true;
//						}
//					}
//
//					return false;
//				}
//			}
//		}
//	}
}
