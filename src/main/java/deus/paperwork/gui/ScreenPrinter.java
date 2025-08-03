package deus.paperwork.gui;

import deus.paperwork.block.printer.TileEntityPrinter;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.lwjgl.opengl.GL11;

public class ScreenPrinter extends ScreenContainerAbstract {
	private final TileEntityPrinter inventory;

	public ScreenPrinter(ContainerInventory inventory, TileEntityPrinter tileEntityPrinter) {
		super(new MenuPrinter(inventory, tileEntityPrinter));
		this.inventory = tileEntityPrinter;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		this.mc.textureManager.loadTexture("/assets/paperwork/textures/gui/container/printer/printer.png").bind();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
