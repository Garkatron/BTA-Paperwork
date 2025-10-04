package deus.paperwork.block.mailbox;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.material.Material;

public class BlockLogicMailbox extends BlockLogicTransparent {

	public BlockLogicMailbox(Block<?> block) {
		super(block, Material.metal);

	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}
}

