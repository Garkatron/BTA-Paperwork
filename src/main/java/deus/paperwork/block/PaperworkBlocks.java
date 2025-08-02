package deus.paperwork.block;


import deus.paperwork.block.cartonbox.BlockCartonBoxLogic;
import deus.paperwork.block.chalkboard.BlockChalkboardLogic;
import deus.paperwork.block.corckboard.BlockCorckboardLogic;
import deus.paperwork.block.file_cabinet.BlockFileCabinetLogic;
import deus.paperwork.block.paperpile.BlockFullPaperPileLogic;
import deus.paperwork.block.paperpile.BlockPaperLayerLogic;
import deus.paperwork.block.photocopier.BlockPhotocopierLogic;
import deus.paperwork.block.printer.BlockPrinterLogic;
import deus.paperwork.item.PaperworkItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkBlocks {

	public static Block<? extends BlockLogic> BLOCK_PRINTER;
	public static Block<? extends BlockLogic> BLOCK_PHOTOCOPIER;
	public static Block<? extends BlockLogic> BLOCK_CARTON_BOX;
	public static Block<? extends BlockLogic> BLOCK_CORCKBOARD;

	public static Block<? extends BlockLogic> BLOCK_PAPER_PILE;
	public static Block<? extends BlockLogic> BLOCK_PAPER_LAYER;

	public static Block<? extends BlockLogic> BLOCK_NEWSPRINT_PILE;
	public static Block<? extends BlockLogic> BLOCK_NEWSPRINT_LAYER;

	public static Block<? extends BlockLogic> BLOCK_CHALKBOARD;
	public static Block<? extends BlockLogic> BLOCK_FILE_CABINET_BROWN_PLANKS;
	public static Block<? extends BlockLogic> BLOCK_FILE_CABINET_IRON;

	private static int BLOCK_ID = 12000;

	static BlockBuilder genericBlockBuilder = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		;


	static BlockBuilder paperBlockBuilder = new BlockBuilder(MOD_ID)
		.setBlockSound(new BlockSound("paperwork:material.paper.put", "paperwork:material.paper.put", 1.0f, 1.0f))
		.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.INSTANT_PICKUP);



	public static void initialize() {
		BLOCK_PRINTER = genericBlockBuilder.build("printer", "printer", newBlockID(), (b) -> new BlockPrinterLogic(b, Material.steel));
		BLOCK_PHOTOCOPIER = genericBlockBuilder.build("photocopier", "photocopier", newBlockID(), (b) -> new BlockPhotocopierLogic(b, Material.steel));

		BLOCK_CARTON_BOX = genericBlockBuilder.build("cartonbox", "cartonbox", newBlockID(), (b) -> new BlockCartonBoxLogic(b, Material.wood));

		BLOCK_PAPER_PILE = paperBlockBuilder.build("paper_pile", "paper_pile", newBlockID(), (b) -> new BlockFullPaperPileLogic(b, "minecraft:item/paper"));
		BLOCK_PAPER_LAYER = paperBlockBuilder.build("paper_layer", "paper_layer", newBlockID(), (b) -> new BlockPaperLayerLogic(b, "minecraft:item/paper"));

		BLOCK_NEWSPRINT_PILE = paperBlockBuilder.build("newsprint_pile", "newsprint_pile", newBlockID(), (b) -> new BlockFullPaperPileLogic(b, MOD_ID+":item/newsprint"));
		BLOCK_NEWSPRINT_LAYER = paperBlockBuilder.build("newsprint_layer", "newsprint_layer", newBlockID(), (b) -> new BlockPaperLayerLogic(b, MOD_ID+":item/newsprint"));

		BLOCK_CORCKBOARD = genericBlockBuilder.build("corckboard", "corckboard", newBlockID(), (b) -> new BlockCorckboardLogic(b, Material.granite));
		BLOCK_CHALKBOARD = genericBlockBuilder.build("chalkboard", "chalkboard", newBlockID(), (b) -> new BlockChalkboardLogic(b, Material.granite));

		BLOCK_FILE_CABINET_BROWN_PLANKS = genericBlockBuilder.build("file_cabinet_browk_planks", "file_cabinet_browk_planks", newBlockID(), (b) -> new BlockFileCabinetLogic(b, Material.steel));
		BLOCK_FILE_CABINET_IRON = genericBlockBuilder.build("file_cabinet_iron", "file_cabinet_iron", newBlockID(), (b) -> new BlockFileCabinetLogic(b, Material.steel));
	}

	public static int newBlockID() {
		BLOCK_ID = BLOCK_ID + 1;
		return BLOCK_ID;
	}
}
