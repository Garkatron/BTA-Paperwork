package deus.paperwork.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemPaintBrush;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.util.helper.DyeColor;

public class ItemColored extends Item {
	public ItemColored(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}

	@Override
	public ItemStack onInventoryInteract(Player player, Slot slot, ItemStack stackInSlot, boolean isItemGrabbed) {
		if (!isItemGrabbed) {
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
		}
		return stackInSlot;
	}

	@Override
	public boolean hasInventoryInteraction() {
		return true;
	}

}
