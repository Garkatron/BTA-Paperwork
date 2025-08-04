package deus.paperwork.entry_points;

import deus.paperwork.block.cardboard_box.TileEntityCardboardBox;
import deus.paperwork.block.printer.TileEntityPrinter;
import deus.paperwork.entities.EntityPaperPlane;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.EntityHelper;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkEntities {
	public static void initialize() {
		EntityHelper.createTileEntity(TileEntityPrinter.class, NamespaceID.getPermanent(MOD_ID, "printer"));
		EntityHelper.createTileEntity(TileEntityCardboardBox.class, NamespaceID.getPermanent(MOD_ID, "cardboard_box"));
		EntityHelper.createEntity(EntityPaperPlane.class, NamespaceID.getPermanent(MOD_ID, "entity_paper_plane"), "entity_paper_plane");

	}
}
