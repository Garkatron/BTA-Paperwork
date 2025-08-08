package deus.paperwork.gui.cardboard_box;

import deus.paperwork.block.printer.TileEntityPrinter;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import deus.paperwork.gui.printer.MenuPrinter;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import org.lwjgl.opengl.GL11;

public class ScreenCardboardBox extends ScreenContainerAbstract {

	private final EntityCardboardBox entityCardboardBox;

	public ScreenCardboardBox(ContainerInventory inventory, EntityCardboardBox entityCardboardBox) {
		super(new MenuCardboardBox(inventory, entityCardboardBox));
		this.entityCardboardBox = entityCardboardBox;
		this.xSize = 176;
		this.ySize = 226;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks) {
		// Bind the GUI background texture

		switch (entityCardboardBox.getBoxSize()) {
			case SMALL:
				this.mc.textureManager.loadTexture("/assets/paperwork/textures/gui/container/cardboard_box/container0.png").bind(); break;
			case REGULAR:
				this.mc.textureManager.loadTexture("/assets/paperwork/textures/gui/container/cardboard_box/container3.png").bind(); break;

			case MEDIUM: this.mc.textureManager.loadTexture("/assets/paperwork/textures/gui/container/cardboard_box/container1.png").bind(); break;
			case LARGE: this.mc.textureManager.loadTexture("/assets/paperwork/textures/gui/container/cardboard_box/container2.png").bind(); break;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		// Calculate the top-left corner of the GUI
		int xGui = (this.width - this.xSize) / 2;
		int yGui = (this.height - this.ySize) / 2;

		// Draw the background texture
		this.drawTexturedModalRect(xGui, yGui, 0, 0, this.xSize, this.ySize);

		// Calculate number of rows for the cardboard box
		int numberOfRows = entityCardboardBox.getContainerSize() / 9;

		// Draw slots for the cardboard box
		for (int row = 0; row < numberOfRows; row++) {
			for (int col = 0; col < 9; col++) {
				int xSlot = xGui + 7 + col * 18; // 8-pixel offset, 18 pixels per column
				int ySlot = yGui + 17 + row * 18; // 18-pixel offset, 18 pixels per row
				drawGuiIcon(xSlot, ySlot, 18, 18, TextureRegistry.getTexture("paperwork:gui/cardboard_slot"));
			}
		}
	}
}
