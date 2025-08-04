package deus.paperwork.gui.cardboard_box;

import deus.paperwork.block.cardboard_box.TileEntityCardboardBox;
import deus.paperwork.block.printer.TileEntityPrinter;
import deus.paperwork.gui.printer.MenuPrinter;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.lwjgl.opengl.GL11;

public class ScreenCardboardBox extends ScreenContainerAbstract {
	private final TileEntityCardboardBox tileEntityCardboardBox;

	public ScreenCardboardBox(ContainerInventory inventory, TileEntityCardboardBox tileEntityCardboardBox) {
		super(new MenuCardboardBox(inventory, tileEntityCardboardBox));
		this.tileEntityCardboardBox = tileEntityCardboardBox;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		this.mc.textureManager.loadTexture("/assets/paperwork/textures/gui/container/cardboard_box/container"+tileEntityCardboardBox.type +".png").bind();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
