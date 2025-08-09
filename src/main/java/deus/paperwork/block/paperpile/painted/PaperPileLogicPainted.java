package deus.paperwork.block.paperpile.painted;

import deus.paperwork.block.PaperworkMaterial;
import deus.paperwork.block.paperpile.regular.PaperPileLogic;
import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class PaperPileLogicPainted extends BlockLogic implements IPainted {
	private String itemnsp;

	public PaperPileLogicPainted(Block<?> block, String itemnsp) {
		super(block, PaperworkMaterial.paper);
		setBlockBounds(0.15,0.0,0.10,0.85,1,0.9);
		this.itemnsp = itemnsp;
	}
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case SILK_TOUCH:
				return new ItemStack[]{new ItemStack(this, meta + 1)};
			case PICK_BLOCK:
				return new ItemStack[]{new ItemStack(this)};
			case EXPLOSION:
			case PROPER_TOOL:
				try {
					return new ItemStack[]{new ItemStack(Item.itemsMap.getOrDefault(NamespaceID.getTemp(this.itemnsp), Items.PAPER), meta + 1)};
				} catch (HardIllegalArgumentException e) {
					throw new RuntimeException(e);
				}
			default:
				return null;
		}
	}
	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isCubeShaped() {return false;}


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
