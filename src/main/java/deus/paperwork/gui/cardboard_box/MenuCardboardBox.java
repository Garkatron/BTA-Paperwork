package deus.paperwork.gui.cardboard_box;

import deus.paperwork.block.cardboard_box.TileEntityCardboardBox;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.menu.MenuContainer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class MenuCardboardBox extends MenuAbstract {

	public TileEntityCardboardBox entity;
	private Container container;
	private int numberOfRows;
	private int numberOfSlots = 9;

	public MenuCardboardBox(Container playerContainer, Container container) {
		this.container = container;
		this.numberOfRows = container.getContainerSize() / 9;
		int i = (this.numberOfRows - 4) * 18;

		int l;
		int j1;
		for(l = 0; l < this.numberOfRows; ++l) {
			for(j1 = 0; j1 < numberOfSlots; ++j1) {
				this.addSlot(new Slot(container, j1 + l * 9, 8 + j1 * 18, 18 + l * 18));
			}
		}

		for(l = 0; l < 3; ++l) {
			for(j1 = 0; j1 < 9; ++j1) {
				this.addSlot(new Slot(playerContainer, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
			}
		}

		for(l = 0; l < 9; ++l) {
			this.addSlot(new Slot(playerContainer, l, 8 + l * 18, 161 + i));
		}

	}

	public boolean stillValid(Player entityplayer) {
		return this.container.stillValid(entityplayer);
	}

	public List<Integer> getMoveSlots(InventoryAction action, Slot slot, int target, Player player) {
		int chestSize = this.numberOfRows * 9;
		if (slot.index >= 0 && slot.index < chestSize) {
			return this.getSlots(0, chestSize, false);
		} else {
			if (action == InventoryAction.MOVE_ALL) {
				if (slot.index >= chestSize && slot.index < chestSize + 27) {
					return this.getSlots(chestSize, 27, false);
				}

				if (slot.index >= chestSize + 27 && slot.index < chestSize + 36) {
					return this.getSlots(chestSize + 27, 9, false);
				}
			} else if (slot.index >= chestSize && slot.index < chestSize + 36) {
				return this.getSlots(chestSize, 36, false);
			}

			return null;
		}
	}

	public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int target, Player player) {
		int chestSize = this.numberOfRows * 9;
		return slot.index < chestSize ? this.getSlots(chestSize, 36, true) : this.getSlots(0, chestSize, false);
	}
}
