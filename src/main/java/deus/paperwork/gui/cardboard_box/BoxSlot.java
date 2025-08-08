package deus.paperwork.gui.cardboard_box;

import deus.paperwork.entities.cardboard_box.BoxSize;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import deus.paperwork.item.PaperworkItems;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;

public class BoxSlot extends Slot {
	public BoxSlot(EntityCardboardBox container, int index, int x, int y) {
		super(container, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack itemstack) {
		if (itemstack == null) {
			return false;
		}

		EntityCardboardBox box = (EntityCardboardBox) container;

		if (box.slotsLocked) {
			return false;
		}

		BoxSize containerSize = box.getBoxSize();

		if (itemstack.itemID == PaperworkItems.CARDBOARD_BOX_SMALL.id) {
			return containerSize == BoxSize.LARGE;
		} else if (itemstack.itemID == PaperworkItems.CARDBOARD_BOX_LARGE.id) {
			return containerSize != BoxSize.SMALL;
		} else if (itemstack.itemID == PaperworkItems.CARDBOARD_BOX_REGULAR.id) {
			return containerSize == BoxSize.LARGE;
		}

		return true;
	}
}
