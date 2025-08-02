package deus.paperwork.item;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.block.paperpile.BlockPaperLayerLogic;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerBase;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomLayerItem extends Item {
	private final Block<? extends BlockLogicLayerBase> block;

	public CustomLayerItem(String translationKey, String namespaceId, int id, Block<? extends BlockLogicLayerBase> block) {
		super(translationKey, namespaceId, id);
		this.block = block;
	}

	public boolean onUseItemOnBlock(ItemStack itemstack, @Nullable Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		Block<?> targetBlock = world.getBlock(blockX, blockY, blockZ);
		int meta = world.getBlockMetadata(blockX, blockY, blockZ);
		if (targetBlock != this.block && targetBlock != null && targetBlock.hasTag(BlockTags.PLACE_OVERWRITES)) {
			targetBlock = null;
			meta = 0;
		}

		if (itemstack.stackSize <= 0) {
			return false;
		} else if (blockY == world.getHeightBlocks() - 1 && this.block.getMaterial().isSolid()) {
			return false;
		} else {
			int newMeta;
			AABB bbBox;
			BlockLogicLayerBase blockLayer;
			if (targetBlock == this.block && side == Side.TOP) {
				blockLayer = (BlockLogicLayerBase)this.block.getLogic();
				newMeta = meta + 1;
				bbBox = AABB.getTemporaryBB((double)blockX, (double)blockY, (double)blockZ, (double)((float)blockX + 1.0F), (double)((float)blockY + (float)(2 * (newMeta + 1)) / 16.0F), (double)((float)blockZ + 1.0F));
				if (!world.checkIfAABBIsClear(bbBox)) {
					return false;
				} else {
					if (newMeta < 7) {
						world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.block.id(), newMeta);
					} else if (blockLayer.fullBlock != null) {
						world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, blockLayer.fullBlock.id(), 0);
					} else {
						world.setBlockAndMetadataWithNotify(blockX, blockY + 1, blockZ, this.block.id(), 0);
					}

					world.playBlockSoundEffect(player, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), this.block, EnumBlockSoundEffectType.PLACE);
					itemstack.consumeItem(player);
					return true;
				}
			} else {
				if (targetBlock != null) {
					blockX += side.getOffsetX();
					blockY += side.getOffsetY();
					blockZ += side.getOffsetZ();
					targetBlock = world.getBlock(blockX, blockY, blockZ);
					meta = world.getBlockMetadata(blockX, blockY, blockZ);
				}

				if (targetBlock == this.block) {
					blockLayer = (BlockLogicLayerBase)this.block.getLogic();
					newMeta = meta + 1;
					bbBox = AABB.getTemporaryBB((double)blockX, (double)blockY, (double)blockZ, (double)((float)blockX + 1.0F), (double)((float)blockY + (float)(2 * (newMeta + 1)) / 16.0F), (double)((float)blockZ + 1.0F));
					if (!world.checkIfAABBIsClear(bbBox)) {
						return false;
					} else {
						if (newMeta < 7) {
							world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.block.id(), newMeta);
						} else if (blockLayer.fullBlock != null) {
							world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, blockLayer.fullBlock.id(), 0);
						} else {
							world.setBlockAndMetadataWithNotify(blockX, blockY + 1, blockZ, this.block.id(), 0);
						}

						world.playBlockSoundEffect(player, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), this.block, EnumBlockSoundEffectType.PLACE);
						itemstack.consumeItem(player);
						return true;
					}
				} else {
					if (world.canBlockBePlacedAt(this.block.id(), blockX, blockY, blockZ, false, side)) {
						int placeMeta = this.block.getPlacedBlockMetadata(player, itemstack, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
						if (world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.block.id(), placeMeta)) {
							if (player == null) {
								this.block.onBlockPlacedOnSide(world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
							} else {
								this.block.onBlockPlacedByMob(world, blockX, blockY, blockZ, Side.NONE, player, xPlaced, yPlaced);
							}

							world.playBlockSoundEffect(player, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), this.block, EnumBlockSoundEffectType.PLACE);
							itemstack.consumeItem(player);
							return true;
						}
					}

					return false;
				}
			}
		}
	}
}
