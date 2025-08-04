package deus.paperwork.item;

import deus.paperwork.block.PaperworkBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerBase;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.block.ItemBlockLayer;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkItems {

	private static int ITEM_ID = 24000;
	public static Item NEWSPRINT;
	public static Item CARDBOARD;

	public static void initialize() {
		NEWSPRINT = new CustomLayerItem("newsprint", MOD_ID+":item/newsprint", newItemID(), (Block<? extends BlockLogicLayerBase>) PaperworkBlocks.BLOCK_NEWSPRINT_LAYER);
		CARDBOARD = new CustomLayerItem("cardboard", MOD_ID+":item/cardboard", newItemID(), (Block<? extends BlockLogicLayerBase>) PaperworkBlocks.BLOCK_CARDBOARD_LAYER);

	}

	public static int newItemID() {
		ITEM_ID = ITEM_ID + 1;
		return ITEM_ID;
	}
}
