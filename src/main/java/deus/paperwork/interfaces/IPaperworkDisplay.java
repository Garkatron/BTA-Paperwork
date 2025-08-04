package deus.paperwork.interfaces;

import deus.paperwork.block.cardboard_box.TileEntityCardboardBox;
import deus.paperwork.block.printer.TileEntityPrinter;

public interface IPaperworkDisplay {
	void paperwork$displayPrinterScreen(TileEntityPrinter printer);
	void paperwork$displayCardboardBoxScreen(TileEntityCardboardBox printer);
}
