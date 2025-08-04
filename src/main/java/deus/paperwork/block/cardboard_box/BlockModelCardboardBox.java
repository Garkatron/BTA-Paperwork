package deus.paperwork.block.cardboard_box;

import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;

public class BlockModelCardboardBox extends BlockModelHorizontalRotation<BlockCardboardBoxLogic> {
	public BlockModelCardboardBox(Block block) {
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
