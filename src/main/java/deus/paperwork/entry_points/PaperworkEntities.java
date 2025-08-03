package deus.paperwork.entry_points;

import deus.paperwork.block.printer.TileEntityPrinter;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.EntityHelper;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkEntities {
	public static void initialize() {
		EntityHelper.createTileEntity(TileEntityPrinter.class, NamespaceID.getPermanent(MOD_ID, "printer"));

	}
}
