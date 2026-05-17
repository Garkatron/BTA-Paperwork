package deus.paperwork.block.mailbox;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;

public class BlockLogicMailbox extends BlockLogicTransparent {

	public BlockLogicMailbox(Block<?> block) {
		super(block, Materials.METAL);

	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}
}

