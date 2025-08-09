package deus.paperwork.block.paperpile.layer.painted;

import deus.paperwork.block.paperpile.layer.ModelPaperLayer;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicTrapDoor;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;

import java.util.Iterator;

public class ModelPaperLayerPainted<T extends BlockLogic> extends ModelPaperLayer<T> {

	public static final IconCoordinate[] topTextures = new IconCoordinate[16];
	public static final IconCoordinate[] sideTextures = new IconCoordinate[16];

	static {
		DyeColor c;
		for (Iterator var0 = DyeColor.blockOrderedColors().iterator(); var0.hasNext(); sideTextures[c.blockMeta] = TextureRegistry.getTexture("paperwork:block/paperpile/" + c.colorID + "/sides")) {
			c = (DyeColor) var0.next();
			topTextures[c.blockMeta] = TextureRegistry.getTexture("paperwork:block/paperpile/" + c.colorID + "/topbottom");
		}

	}


	@Override
	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		int color = data >> 4 & 15;
		int orientation = data & 3;
		if (BlockLogicTrapDoor.isTrapdoorOpen(data)) {
			int index = Sides.orientationLookUpTrapdoorOpen[6 * orientation + side.getId()];
			return index < 2 ? topTextures[color] : sideTextures[color];
		} else {
			return side.getAxis() == Axis.Y ? topTextures[color] : sideTextures[color];
		}
	}

	public ModelPaperLayerPainted(Block block) {
		super(block);
	}
}
