package deus.paperwork.block.file_cabinet;

import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;

import static deus.paperwork.Paperwork.MOD_ID;


public class BlockModelFileCabinet <T extends BlockLogic> extends BlockModelHorizontalRotation<T> {
	public BlockModelFileCabinet(Block<T> block) {
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
