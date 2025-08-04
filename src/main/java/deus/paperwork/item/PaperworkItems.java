package deus.paperwork.item;

import deus.paperwork.armor.PaperworkArmorMaterial;
import deus.paperwork.block.PaperworkBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerBase;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.block.ItemBlockLayer;
import net.minecraft.core.item.material.ArmorMaterial;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkItems {

	private static int ITEM_ID = 24000;
	public static Item NEWSPRINT;
	public static Item CARDBOARD;

	public static Item ARMOR_BOOTS_CARDBOARD;
	public static Item ARMOR_LEGGINS_CARDBOARD;
	public static Item ARMOR_CHESTPLATE_CARDBOARD;
	public static Item ARMOR_HELMET_CARDBOARD;

	public static void initialize() {
		NEWSPRINT = new CustomLayerItem("newsprint", MOD_ID+":item/newsprint", newItemID(), (Block<? extends BlockLogicLayerBase>) PaperworkBlocks.BLOCK_NEWSPRINT_LAYER);
		CARDBOARD = new CustomLayerItem("cardboard", MOD_ID+":item/cardboard", newItemID(), (Block<? extends BlockLogicLayerBase>) PaperworkBlocks.BLOCK_CARDBOARD_LAYER);

		ARMOR_BOOTS_CARDBOARD = new ItemArmor("armor_boots_cardboard",MOD_ID+":item/armor/cardboard/boots", newItemID(), PaperworkArmorMaterial.CARDBOARD, 0);
		ARMOR_LEGGINS_CARDBOARD = new ItemArmor("armor_leggins_cardboard",MOD_ID+":item/armor/cardboard/leggins", newItemID(), PaperworkArmorMaterial.CARDBOARD, 1);
		ARMOR_CHESTPLATE_CARDBOARD = new ItemArmor("armor_chestplate_cardboard",MOD_ID+":item/armor/cardboard/chestplate", newItemID(), PaperworkArmorMaterial.CARDBOARD, 2);
		ARMOR_HELMET_CARDBOARD = new ItemArmor("armor_helmet_cardboard",MOD_ID+":item/armor/cardboard/helmet", newItemID(), PaperworkArmorMaterial.CARDBOARD, 3);
	}

	public static int newItemID() {
		ITEM_ID = ITEM_ID + 1;
		return ITEM_ID;
	}
}
