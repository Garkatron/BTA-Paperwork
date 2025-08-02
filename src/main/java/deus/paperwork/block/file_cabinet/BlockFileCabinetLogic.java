package deus.paperwork.block.file_cabinet;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;

public class BlockFileCabinetLogic extends BlockLogicRotatable {
	public BlockFileCabinetLogic(Block<?> block, Material material) {
		super(block, material);
	}
	public boolean isSolidRender() {
		return false;
	}
}
