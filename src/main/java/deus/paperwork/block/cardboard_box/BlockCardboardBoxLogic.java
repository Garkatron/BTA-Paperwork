package deus.paperwork.block.cardboard_box;

import deus.paperwork.block.printer.TileEntityPrinter;
import deus.paperwork.interfaces.IPaperworkDisplay;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockCardboardBoxLogic extends BlockLogic {
	public int type = 0;
	public BlockCardboardBoxLogic(Block<?> block, Material material, int type) {
		super(block, material);
		this.type = type;
		block.withEntity(()->new TileEntityCardboardBox(type));

		switch (type) {
			case 1: {
				setBlockBounds(0.15,0.0,0.15,0.85,0.65,0.85); break;
			}
			case 2: {
				setBlockBounds(0.25,0.0,0.25,0.75,0.45,0.75); break;
			}

			default: break;
		}
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
		if (!world.isClientSide) {
			TileEntityCardboardBox tileEntityCardboardBox = (TileEntityCardboardBox)world.getTileEntity(x, y, z);
			((IPaperworkDisplay)player).paperwork$displayCardboardBoxScreen(tileEntityCardboardBox);
		}
		return true;
	}

	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isCubeShaped() {return false;}
}
