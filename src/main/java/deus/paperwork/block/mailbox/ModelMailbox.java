package deus.paperwork.block.mailbox;

import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.model.Cube;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;

public class ModelMailbox <T extends BlockLogic> extends BlockModelHorizontalRotation<T> {


	public ModelMailbox(Block<T> block) {
		super(block);

	}



	private void setRotationAngle(Cube cube, float x, float y, float z) {
		cube.xRot = x;
		cube.yRot = y;
		cube.zRot = z;
	}
}
