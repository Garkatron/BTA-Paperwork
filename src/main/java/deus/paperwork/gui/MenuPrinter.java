package deus.paperwork.gui;

import deus.paperwork.block.printer.TileEntityPrinter;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class MenuPrinter extends MenuAbstract {

	public TileEntityPrinter printer;
	public MenuPrinter(ContainerInventory inventory, TileEntityPrinter tileEntityPrinter) {
		this.printer = tileEntityPrinter;

		this.addSlot(new Slot(tileEntityPrinter, 0, 17, 17));
		this.addSlot(new Slot(tileEntityPrinter, 1, 36, 17));
		this.addSlot(new Slot(tileEntityPrinter, 2, 55, 17));

		this.addSlot(new Slot(tileEntityPrinter, 3, 36, 53));

		this.addSlot(new Slot(tileEntityPrinter, 4, 116, 34));
		this.addSlot(new Slot(tileEntityPrinter, 5, 150, 31));


		int j;
		for(j = 0; j < 3; ++j) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
			}
		}

		for(j = 0; j < 9; ++j) {
			this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
		}

	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, Player player) {
		return new ArrayList<>();
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, Player player) {
		return new ArrayList<>();
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}
}
