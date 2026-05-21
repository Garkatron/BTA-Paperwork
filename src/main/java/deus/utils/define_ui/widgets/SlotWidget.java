package deus.utils.define_ui.widgets;

import deus.utils.define_ui.core.Widget;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;

public class SlotWidget extends Widget {
	private static final int SIZE = 18;
	private final int slotIndex;
	private final ContainerInventory inventory;

	public SlotWidget(ContainerInventory inventory, int slotIndex) {
		this.inventory = inventory;
		this.slotIndex = slotIndex;
		this.width = SIZE;
		this.height = SIZE;
	}

	@Override
	public void draw(int x, int y) {
		// slot background — assumes standard container texture already bound
		drawTexturedModalRect(x, y, 0, 166, SIZE, SIZE);
		ItemStack stack = inventory.getItem(slotIndex);
		if (stack != null) {
			// item render handled by vanilla slot system — this is display-only overlay
		}
	}
}
