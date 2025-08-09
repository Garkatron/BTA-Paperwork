package deus.paperwork.block.paperpile.layer;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.block.PaperworkMaterial;
import deus.paperwork.block.paperpile.layer.painted.PaperLayerLogicPainted;
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

public class PaperLayerLogic extends BlockLogicLayerBase implements IPaintable  {
	private final String itemnsp;
	public static final int MASK_HEIGHT = 7; // Lower 3 bits for layer height (0-7)
	public static final int MASK_COLOR = 240; // Upper 4 bits for color (0-15)
	public PaperLayerLogic(Block<?> block, Block<?> fullblock, String itemnsp) {
		super(block, fullblock, PaperworkMaterial.paper);
		setBlockBounds(0.15,0.0,0.10,0.85,1,0.9);
		this.itemnsp = itemnsp;
	}
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isCubeShaped() {return false;}

	@Override
	public AABB getBlockBoundsFromState(WorldSource world, int x, int y, int z) {

		int l = world.getBlockMetadata(x, y, z) & 7;
		float f = (float)(2 * (1 + l)) / 16.0F;
		return AABB.getTemporaryBB(0.15,0.0,0.10,0.85, (double)f, 0.9);
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
					return new ItemStack[]{new ItemStack(Item.itemsMap.getOrDefault(NamespaceID.getTemp(this.itemnsp),Items.PAPER), meta + 1)};
				} catch (HardIllegalArgumentException e) {
					throw new RuntimeException(e);
				}
			default:
				return null;
		}
	}
	public DyeColor fromMetadata(int meta) {
		return DyeColor.colorFromBlockMeta((meta & MASK_COLOR) >> 4); // Extract color ID from upper 4 bits
	}

	public int toMetadata(DyeColor color) {
		return (color.color.value << 4) & MASK_COLOR; // Store color ID in upper 4 bits
	}
	public int stripColorFromMetadata(int meta) {
		return meta & ~MASK_COLOR; // Clear color bits, preserve height
	}
	@Override
	public void setColor(World world, int x, int y, int z, DyeColor color) {
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockAndMetadataRaw(x, y, z, PaperworkBlocks.BLOCK_PAPER_LAYER_PAINTED.id(), meta);
		world.setBlockMetadata(x, y, z, meta);
		((PaperLayerLogicPainted) PaperworkBlocks.BLOCK_PAPER_LAYER_PAINTED.getLogic()).setColor(world, x, y, z, color);
	}
}
