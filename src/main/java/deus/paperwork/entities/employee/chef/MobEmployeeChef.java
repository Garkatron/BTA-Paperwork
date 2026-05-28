package deus.paperwork.entities.employee.chef;

import deus.brainless.ai.AI;
import deus.paperwork.ai.AIHolder;
import deus.paperwork.entities.employee.MobEmployee;
import deus.paperwork.entities.employee.MobEmployeeRenderer;
import deus.paperwork.entities.employee.farmer.MobEmployeeFarmer;
import deus.paperwork.entities.employee.farmer.MobEmployeeFarmerRenderer;
import deus.paperwork.util.PoscArea;
import deus.utils.annotations.RegisterEntity;
import deus.utils.annotations.RegisterEntityRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFurnace;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RegisterEntityRenderer(renderer = MobEmployeeRenderer.class)
@RegisterEntity(id = "employee_chef", name = "employee_chef")
public class MobEmployeeChef extends MobEmployee {
	public MobEmployeeChef(@NotNull World world) {
		super(world);
	}

	protected MobEmployeeChef(@NotNull World world, AIHolder<?> aiHolder) {
		super(world, aiHolder);
	}

	@Override
	@SuppressWarnings("unchecked")
	public AI<MobEmployeeChef> ai() {
		return (AI<MobEmployeeChef>) aiHolder.ai();
	}


	// TODO: fix empty condition
	public List<TilePosc> getFurnaceBlocksInArea(PoscArea.Area2D blockArea, boolean empty) {
		return getBlocksInArea(blockArea).stream()
			.filter(p -> {
				Block<?> b = world.getBlockType(p);
				int id = b.id();
				boolean isEmpty = true;
				if (empty && b.getLogic() instanceof BlockLogicFurnace) {
					TileEntityFurnace furnace = (TileEntityFurnace) world.getTileEntity(p);
					isEmpty = !furnace.isBurning();
				}

				return isEmpty && (id == Blocks.FURNACE_STONE_IDLE.id() || id == Blocks.FURNACE_STONE_ACTIVE.id());
			})
			.toList();
	}
}
