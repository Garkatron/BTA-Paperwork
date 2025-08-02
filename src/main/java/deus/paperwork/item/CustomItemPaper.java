package deus.paperwork.item;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.block.paperpile.BlockPaperLayerLogic;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLayerBase;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomItemPaper extends CustomLayerItem {

	public CustomItemPaper(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id, (Block<BlockPaperLayerLogic>) PaperworkBlocks.BLOCK_PAPER_LAYER);
	}
}
