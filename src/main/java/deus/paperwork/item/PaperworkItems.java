package deus.paperwork.item;

import deus.paperwork.armor.PaperworkArmorMaterial;
import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.entities.cardboard_box.BoxSize;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import deus.paperwork.item.big_paperplane.ItemBigPaperPlane;
import deus.paperwork.item.paperplane.ItemPaperPlane;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerBase;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkItems {

	private static int ITEM_ID = 24000;
	public static Item NEWSPRINT;
	public static Item CARDBOARD;

	public static Item ARMOR_BOOTS_CARDBOARD;
	public static Item ARMOR_LEGGINS_CARDBOARD;
	public static Item ARMOR_CHESTPLATE_CARDBOARD;
	public static Item ARMOR_HELMET_CARDBOARD;

	public static Item PAPERPLANE;
	public static Item BIG_PAPERPLANE;

	public static Item CARDBOARD_BOX_REGULAR;
	public static Item CARDBOARD_BOX_SMALL;
	public static Item CARDBOARD_BOX_MEDIUM;
	public static Item CARDBOARD_BOX_LARGE;

	public static Item CARDBOARD_TUBE;


	public static void initialize() {
		NEWSPRINT = new CustomLayerItem("newsprint", MOD_ID+":item/newsprint", newItemID(), (Block<? extends BlockLogicLayerBase>) PaperworkBlocks.BLOCK_NEWSPRINT_LAYER);
		CARDBOARD = new CustomLayerItem("cardboard", MOD_ID+":item/cardboard", newItemID(), (Block<? extends BlockLogicLayerBase>) PaperworkBlocks.BLOCK_CARDBOARD_LAYER);

		ARMOR_BOOTS_CARDBOARD = new ItemArmor("armor_boots_cardboard",MOD_ID+":item/armor/cardboard/boots", newItemID(), PaperworkArmorMaterial.CARDBOARD, 0);
		ARMOR_LEGGINS_CARDBOARD = new ItemArmor("armor_leggins_cardboard",MOD_ID+":item/armor/cardboard/leggins", newItemID(), PaperworkArmorMaterial.CARDBOARD, 1);
		ARMOR_CHESTPLATE_CARDBOARD = new ItemArmor("armor_chestplate_cardboard",MOD_ID+":item/armor/cardboard/chestplate", newItemID(), PaperworkArmorMaterial.CARDBOARD, 2);
		ARMOR_HELMET_CARDBOARD = new ItemArmor("armor_helmet_cardboard",MOD_ID+":item/armor/cardboard/helmet", newItemID(), PaperworkArmorMaterial.CARDBOARD, 3);

		PAPERPLANE = new ItemPaperPlane("paperplane", MOD_ID+":item/paperplane/paperplane", newItemID());
		BIG_PAPERPLANE = new ItemBigPaperPlane("big_paperplane", MOD_ID+":item/big_paperplane", newItemID());

		CARDBOARD_BOX_REGULAR = new ItemCardboardBox("cardboard_box_regular", MOD_ID+":item/cardboard_box_regular", newItemID(), BoxSize.REGULAR);
		CARDBOARD_BOX_SMALL = new ItemCardboardBox("cardboard_box_small", MOD_ID+":item/cardboard_box_small", newItemID(), BoxSize.SMALL);
		CARDBOARD_BOX_MEDIUM = new ItemCardboardBox("cardboard_box_medium", MOD_ID+":item/cardboard_box_medium", newItemID(), BoxSize.MEDIUM);
		CARDBOARD_BOX_LARGE = new ItemCardboardBox("cardboard_box_large", MOD_ID+":item/cardboard_box_large", newItemID(), BoxSize.LARGE);
		CARDBOARD_TUBE = new ItemToolSword("cardboard_tube", MOD_ID+":item/cardboard_tube", newItemID(), PaperworkToolMaterial.CARDBOARD);
	}

	public static int newItemID() {
		ITEM_ID = ITEM_ID + 1;
		return ITEM_ID;
	}
}
