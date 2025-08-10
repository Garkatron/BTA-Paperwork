package deus.paperwork.block.printer;

import deus.paperwork.interfaces.IPaperworkDisplay;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockPrinterLogic extends BlockLogicRotatable {
	public BlockPrinterLogic(Block<?> block, Material material) {
		super(block, material);
		//block.withEntity(TileEntityPrinter::new);

	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
//		if (!world.isClientSide) {
//			TileEntityPrinter tileEntityPrinter = (TileEntityPrinter)world.getTileEntity(x, y, z);
//			((IPaperworkDisplay)player).paperwork$displayPrinterScreen(tileEntityPrinter);
//		}

		return false;
	}

}
