package deus.paperwork.block.paperpile.layer.painted;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.block.PaperworkMaterial;
import deus.paperwork.block.paperpile.layer.BlockPaperLayerLogic;
import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

public class BlockPaperLayerLogicPainted extends BlockPaperLayerLogic implements IPainted {
	public BlockPaperLayerLogicPainted(Block<?> block, Block<?> fullblock, String itemnsp) {
		super(block, fullblock, itemnsp);

	}


	@Override
	public DyeColor fromMetadata(int meta) {
		return DyeColor.colorFromBlockMeta((meta & 240) >> 4);
	}

	@Override
	public int toMetadata(DyeColor color) {
		return color.blockMeta << 4;
	}

	@Override
	public int stripColorFromMetadata(int meta) {
		return meta & -241; // Clear bits 4–7, keep layer height
	}

	@Override
	public void removeDye(World world, int x, int y, int z) {
		int meta = stripColorFromMetadata(world.getBlockMetadata(x, y, z));
		world.setBlockAndMetadataWithNotify(x, y, z, PaperworkBlocks.BLOCK_PAPER_LAYER.id(), meta);
	}
}
