package deus.paperwork.interfaces;

import deus.paperwork.block.printer.TileEntityPrinter;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;

public interface IPaperworkDisplay {
	void paperwork$displayPrinterScreen(TileEntityPrinter printer);
	void paperwork$displayCardboardBoxScreen(EntityCardboardBox cardboardBox);
}
