package deus.paperwork.block.paperpile.layer;

import net.minecraft.client.render.block.model.BlockModelLayer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;

public class BlockModelPaperLayer<T extends BlockLogic> extends BlockModelLayer<T> {
	public BlockModelPaperLayer(Block block) {
		super(block);
	}

	@Override
	public boolean shouldSideBeRendered(WorldSource blockAccess, AABB bounds, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(WorldSource blockAccess, AABB bounds, int x, int y, int z, int side, int meta) {
		return true;
	}
}
