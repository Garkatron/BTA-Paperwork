package deus.paperwork.block;


import deus.paperwork.ConfigManager;
import deus.paperwork.block.chalkboard.BlockChalkboardLogic;
import deus.paperwork.block.confetti.ConfettiLogic;
import deus.paperwork.block.corckboard.BlockCorckboardLogic;
import deus.paperwork.block.file_cabinet.BlockLogicFileCabinet;
import deus.paperwork.block.mailbox.BlockLogicMailbox;
import deus.paperwork.block.photocopier.BlockPhotocopierLogic;
import deus.paperwork.block.printer.BlockPrinterLogic;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkBlocks {

	public static Block<? extends BlockLogic> BLOCK_PRINTER;
	public static Block<? extends BlockLogic> BLOCK_PHOTOCOPIER;


	public static Block<? extends BlockLogic> BLOCK_CORCKBOARD;

	// public static Block<? extends BlockLogic> BLOCK_PAPER_PILE;
	// public static Block<? extends BlockLogic> BLOCK_PAPER_PILE_PAINTED;

	// public static Block<? extends BlockLogic> BLOCK_PAPER_LAYER;
	// public static Block<? extends BlockLogic> BLOCK_PAPER_LAYER_PAINTED;

	public static Block<? extends BlockLogic> BLOCK_NEWSPRINT_PILE;
	public static Block<? extends BlockLogic> BLOCK_NEWSPRINT_LAYER;

	public static Block<? extends BlockLogic> BLOCK_CARDBOARD_PILE;
	public static Block<? extends BlockLogic> BLOCK_CARDBOARD_LAYER;

	public static Block<? extends BlockLogic> BLOCK_CONFETTI;

	public static Block<? extends BlockLogic> BLOCK_CHALKBOARD;
	public static Block<? extends BlockLogic> BLOCK_FILE_CABINET_BROWN_PLANKS;
	public static Block<? extends BlockLogic> BLOCK_FILE_CABINET_IRON;
	public static Block<? extends BlockLogic> BLOCK_MAILBOX;
	public static Block<? extends BlockLogic> BLOCK_ORIGAMI_FLOWER_00;
	public static Block<? extends BlockLogic> BLOCK_BIN;



	static BlockBuilder GENERIC_BLOCK_BUILDER = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		;

	static BlockBuilder PAPER_BLOCK_BUILDER = new BlockBuilder(MOD_ID)
		.setBlockSound(new BlockSound("paperwork:material.paper.put", "paperwork:material.paper.put", 1.0f, 1.0f))
		.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.INSTANT_PICKUP);


	public static void initialize() {

		BLOCK_PRINTER = GENERIC_BLOCK_BUILDER.build(
			"printer",
			"printer",
			ConfigManager.blockGoc("BLOCK_PRINTER"),
			(b) -> new BlockPrinterLogic(b, Materials.METAL)
		);

		BLOCK_PHOTOCOPIER = GENERIC_BLOCK_BUILDER.build(
			"photocopier",
			"photocopier",
			ConfigManager.blockGoc("BLOCK_PHOTOCOPIER"),
			(b) -> new BlockPhotocopierLogic(b, Materials.METAL)
		);


		BLOCK_CONFETTI = PAPER_BLOCK_BUILDER.build(
			"confetti",
			"confetti",
			ConfigManager.blockGoc("BLOCK_CONFETTI"),
			(b) -> new ConfettiLogic(b)
		);

		BLOCK_CORCKBOARD = GENERIC_BLOCK_BUILDER.build(
			"corckboard",
			"corckboard",
			ConfigManager.blockGoc("BLOCK_CORCKBOARD"),
			(b) -> new BlockCorckboardLogic(b, Materials.GRANITE)
		);

		BLOCK_CHALKBOARD = GENERIC_BLOCK_BUILDER.build(
			"chalkboard",
			"chalkboard",
			ConfigManager.blockGoc("BLOCK_CHALKBOARD"),
			(b) -> new BlockChalkboardLogic(b, Materials.GRANITE)
		);

		BLOCK_FILE_CABINET_BROWN_PLANKS = GENERIC_BLOCK_BUILDER.build(
			"file_cabinet_brown_planks",
			"file_cabinet_brown_planks",
			ConfigManager.blockGoc("BLOCK_FILE_CABINET_BROWN_PLANKS"),
			(b) -> new BlockLogicFileCabinet(b, Materials.METAL)
		);

		BLOCK_FILE_CABINET_IRON = GENERIC_BLOCK_BUILDER.build(
			"file_cabinet_iron",
			"file_cabinet_iron",
			ConfigManager.blockGoc("BLOCK_FILE_CABINET_IRON"),
			(b) -> new BlockLogicFileCabinet(b, Materials.METAL)
		);

		BLOCK_MAILBOX = GENERIC_BLOCK_BUILDER.build(
			"mailbox",
			"mailbox",
			ConfigManager.blockGoc("BLOCK_MAILBOX"),
			(b) -> new BlockLogicMailbox(b)
		);

		BLOCK_ORIGAMI_FLOWER_00 = PAPER_BLOCK_BUILDER.build(
			"origami_flower_00",
			"origami_flower_00",
			ConfigManager.blockGoc("BLOCK_ORIGAMI_FLOWER_00"),
			(b) -> new PaperBlock(b)
		);

		BLOCK_BIN = GENERIC_BLOCK_BUILDER.build(
			"bin",
			"bin",
			ConfigManager.blockGoc("BLOCK_BIN"),
			(b) -> new DecorativeTransparentBlock(b, Materials.METAL)
		);
	}



}
