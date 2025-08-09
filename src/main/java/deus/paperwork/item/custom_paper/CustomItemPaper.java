package deus.paperwork.item.custom_paper;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.block.paperpile.layer.PaperLayerLogic;
import deus.paperwork.item.CustomLayerItem;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemPaintBrush;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.util.helper.DyeColor;

public class CustomItemPaper extends CustomLayerItem {

	public CustomItemPaper(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id, (Block<PaperLayerLogic>) PaperworkBlocks.BLOCK_PAPER_LAYER);
	}


//	@Override
//	public boolean onUseItemOnBlock(ItemStack itemstack, @Nullable Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
//		boolean result =  super.onUseItemOnBlock(itemstack, player, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
//		if (result) {
//			Block<?> targetBlock = world.getBlock(blockX, blockY, blockZ);
//			if (Block.hasLogicClass(targetBlock, IPaintable.class)) {
//				DyeColor color;
//
//				IPaintable paintable = (IPaintable)targetBlock.getLogic();
//				if (!paintable.canBePainted()) {
//					return false;
//				}
//
//				color = ItemPaintBrush.getColor(itemstack);
//				if (color != null) {
//					if (paintable instanceof IPainted && ((IPainted)paintable).getColor(world, blockX, blockY, blockZ) == color) {
//						return false;
//					}
//
//					paintable.setColor(world, blockX, blockY, blockZ, color);
//					return true;
//				}
//			}
//		}
//		return result;
//	}

	@Override
	public ItemStack onInventoryInteract(Player player, Slot slot, ItemStack stackInSlot, boolean isItemGrabbed) {
		if (isItemGrabbed) {
			return stackInSlot;
		} else {
			DyeColor currentColor = ItemPaintBrush.getColor(stackInSlot);
			ItemStack grabbedItem = player.inventory.getHeldItemStack();
			if (grabbedItem != null && grabbedItem.getItem() instanceof ItemDye) {
				DyeColor newColor = DyeColor.colorFromItemMeta(grabbedItem.getMetadata());
				if (currentColor != newColor) {
					ItemPaintBrush.setColor(stackInSlot, newColor);
					--grabbedItem.stackSize;
					if (grabbedItem.stackSize <= 0) {
						player.inventory.setHeldItemStack(null);
					}
				}
			}
			return stackInSlot;
		}
	}

	@Override
	public boolean hasInventoryInteraction() {
		return true;
	}

}
