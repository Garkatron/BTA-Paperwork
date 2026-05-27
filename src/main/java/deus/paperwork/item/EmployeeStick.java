package deus.paperwork.item;

import deus.brainless.Brainless;
import deus.paperwork.entities.employee.MobEmployee;
import deus.utils.annotations.RegisterItemModel;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@RegisterItemModel(model = ItemModelStandard.class)
public class EmployeeStick extends Item {

	public enum AssignMode { BED, WORK, FOOD }

	private TilePosc pendingPos  = null;
	private AssignMode pendingMode = null;


	public EmployeeStick(@NotNull String translationKey, @NotNull String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}

	@Override
	public boolean onUseOnBlock(@NotNull ItemStack selfStack, @NotNull World world,
	                            @Nullable Player player, @NotNull TilePosc blockPos,
	                            @NotNull Side side, double xHit, double yHit) {
		if (world.isClientSide || player == null) return false;

		int blockId = world.getBlockType(blockPos).id();

		if (blockId == Blocks.BED.id()) {
			pendingPos  = new TilePos().set(blockPos.x(),blockPos.y(),blockPos.z());
			pendingMode = AssignMode.BED;
			Brainless.LOGGER.info("[Stick] Bed position saved: {}", blockPos);
		} else if (blockId == Blocks.WORKBENCH.id()) {
			pendingPos  = new TilePos().set(blockPos.x(),blockPos.y(),blockPos.z());
			pendingMode = AssignMode.WORK;
			Brainless.LOGGER.info("[Stick] Work position saved: {}", blockPos);
		} else if (blockId == Blocks.CHEST_PLANKS_OAK.id()) {
			pendingPos  = new TilePos().set(blockPos.x(),blockPos.y(),blockPos.z());
			pendingMode = AssignMode.FOOD;
			System.out.println(world.getTileEntity(pendingPos));
			Brainless.LOGGER.info("[Stick] Food position saved: {}", blockPos);
		} else {
			Brainless.LOGGER.info("[Stick] Block not recognized for assignment");
			return false;
		}

		return true;
	}

	@Override
	public boolean useOnEntity(@NotNull ItemStack selfStack, @NotNull Player player, @NotNull Mob mob) {
		if (!(mob instanceof MobEmployee employee)) return false;
		if (pendingPos == null || pendingMode == null) {
			Brainless.LOGGER.info("[Stick] No position saved yet — click a block first");
			return false;
		}

		switch (pendingMode) {
			case BED  -> { employee.bed_place = Optional.of(pendingPos); Brainless.LOGGER.info("[Stick] Bed assigned to employee at {}", pendingPos); }
			case WORK -> { employee.work_place = Optional.of(pendingPos); Brainless.LOGGER.info("[Stick] Work assigned to employee at {}", pendingPos); }
			case FOOD -> { employee.food_place = Optional.of(pendingPos); Brainless.LOGGER.info("[Stick] Food assigned to employee at {}", pendingPos); }
		}

		pendingPos  = null;
		pendingMode = null;
		return true;
	}
}
