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

public class MenuCardboardBox extends MenuContainer {

	public TileEntityCardboardBox entity;


	public MenuCardboardBox(Container playerContainer, Container container) {
		super(playerContainer, container);
	}
}
