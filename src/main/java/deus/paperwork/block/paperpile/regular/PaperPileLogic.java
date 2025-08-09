package deus.paperwork.block.paperpile.regular;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.block.PaperworkMaterial;
import deus.paperwork.block.paperpile.layer.painted.PaperLayerLogicPainted;
import deus.paperwork.block.paperpile.painted.PaperPileLogicPainted;
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

public class PaperPileLogic extends BlockLogic implements IPaintable {
	private String itemnsp;
	public PaperPileLogic(Block<?> block, String itemnsp) {
		super(block, PaperworkMaterial.paper);
		setBlockBounds(0.15,0.0,0.10,0.85,1,0.9);
		this.itemnsp = itemnsp;
	}
	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isCubeShaped() {return false;}

	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case SILK_TOUCH:
				return new ItemStack[]{new ItemStack(this, meta + 1)};
			case PICK_BLOCK:
				return new ItemStack[]{new ItemStack(this)};
			case EXPLOSION:
			case PROPER_TOOL:
				try {
					return new ItemStack[]{new ItemStack(Item.itemsMap.getOrDefault(NamespaceID.getTemp(this.itemnsp),Items.PAPER), 8)};
				} catch (HardIllegalArgumentException e) {
					throw new RuntimeException(e);
				}
			default:
				return null;
		}
	}

	@Override
	public void setColor(World world, int x, int y, int z, DyeColor color) {
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockAndMetadataRaw(x, y, z, PaperworkBlocks.BLOCK_PAPER_PILE_PAINTED.id(), meta);
		world.setBlockMetadata(x, y, z, meta);
		((PaperPileLogicPainted) PaperworkBlocks.BLOCK_PAPER_PILE_PAINTED.getLogic()).setColor(world, x, y, z, color);
	}

}
