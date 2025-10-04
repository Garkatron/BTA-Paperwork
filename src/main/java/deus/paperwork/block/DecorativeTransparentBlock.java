package deus.paperwork.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;

public class DecorativeTransparentBlock extends BlockLogic {
	public DecorativeTransparentBlock(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}
}
