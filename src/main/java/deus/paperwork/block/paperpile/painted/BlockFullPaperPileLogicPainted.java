package deus.paperwork.block.paperpile.painted;

import deus.paperwork.block.PaperworkMaterial;
import deus.paperwork.block.paperpile.regular.BlockFullPaperPileLogic;
import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockFullPaperPileLogicPainted extends BlockFullPaperPileLogic implements IPainted {
	public BlockFullPaperPileLogicPainted(Block<?> block, String itemnsp) {
		super(block, itemnsp);
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
		return meta & -241;
	}

	@Override
	public void removeDye(World world, int x, int y, int z) {
		int meta = this.stripColorFromMetadata(world.getBlockMetadata(x, y, z));
		world.setBlockAndMetadataWithNotify(x, y, z, Blocks.CHEST_PLANKS_OAK.id(), meta);
	}
}
