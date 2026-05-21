package deus.paperwork.gui.printer;

import deus.paperwork.block.printer.TileEntityPrinter;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MenuPrinter extends MenuAbstract {

	public TileEntityPrinter printer;
	public MenuPrinter(ContainerInventory inventory, TileEntityPrinter tileEntityPrinter) {


	}


	@Override
	public IntList getMoveSlots(@NotNull InventoryAction inventoryAction, @NotNull Slot slot, int i, Player player) {
		return null;
	}

	@Override
	public IntList getTargetSlots(@NotNull InventoryAction inventoryAction, @NotNull Slot slot, int i, Player player) {
		return null;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}
}
