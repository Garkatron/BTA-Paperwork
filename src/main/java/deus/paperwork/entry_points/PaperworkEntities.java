package deus.paperwork.entry_points;

import deus.paperwork.block.file_cabinet.TileEntityFileCabinet;
import deus.paperwork.block.printer.TileEntityPrinter;
import deus.paperwork.entities.big_paperplane.EntityBigPaperPlane;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import deus.paperwork.entities.paperplane.EntityPaperPlane;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.EntityHelper;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkEntities {
	public static void initialize() {
		EntityHelper.createTileEntity(TileEntityPrinter.class, NamespaceID.getPermanent(MOD_ID, "printer"));
		EntityHelper.createTileEntity(TileEntityFileCabinet.class, NamespaceID.getPermanent(MOD_ID, "entity_file_cabinet"));
		EntityHelper.createEntity(EntityPaperPlane.class, NamespaceID.getPermanent(MOD_ID, "entity_paper_plane"), "entity_paper_plane");
		EntityHelper.createEntity(EntityBigPaperPlane.class, NamespaceID.getPermanent(MOD_ID, "entity_big_paper_plane"), "entity_big_paper_plane");
		EntityHelper.createEntity(EntityCardboardBox.class, NamespaceID.getPermanent(MOD_ID, "entity_cardboard_box"), "entity_cardboard_box");

	}
}
