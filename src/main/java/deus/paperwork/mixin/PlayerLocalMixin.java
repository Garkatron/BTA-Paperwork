package deus.paperwork.mixin;

import deus.paperwork.block.printer.TileEntityPrinter;
import deus.paperwork.gui.ScreenPrinter;
import deus.paperwork.interfaces.IPaperworkDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PlayerLocal.class)
public class PlayerLocalMixin implements IPaperworkDisplay {

	@Override
	public void paperwork$displayPrinterScreen(TileEntityPrinter printer) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayScreen(new ScreenPrinter(mc.thePlayer.inventory, printer));
	}
}
