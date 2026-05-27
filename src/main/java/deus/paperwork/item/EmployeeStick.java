package deus.paperwork.item;

import deus.brainless.Brainless;
import deus.paperwork.entities.employee.MobEmployee;
import deus.paperwork.util.PoscArea;
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

	public enum AssignMode { BED, FOOD }

	private TilePosc pendingBed  = null;
	private TilePosc foodPointA  = null;
	private TilePosc foodPointB  = null;

	public EmployeeStick(@NotNull String translationKey, @NotNull String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}

	@Override
	public boolean onUseOnBlock(@NotNull ItemStack selfStack, @NotNull World world,
	                            @Nullable Player player, @NotNull TilePosc blockPos,
	                            @NotNull Side side, double xHit, double yHit) {
		if (world.isClientSide || player == null) return false;

		int blockId = world.getBlockType(blockPos).id();
		TilePosc pos = new TilePos(blockPos.x(), blockPos.y(), blockPos.z());

		if (blockId == Blocks.BED.id()) {
			pendingBed = pos;
			Brainless.LOGGER.info("[Stick] Bed position saved: {}", pos);

		} else if (blockId == Blocks.LOG_OAK.id()) {
			if (player.isSneaking()) {
				foodPointB = pos;
				Brainless.LOGGER.info("[Stick] Food point B set: {}", pos);
				if (foodPointA != null) {
					Brainless.LOGGER.info("[Stick] Food area ready: {} -> {}", foodPointA, foodPointB);
				}
			} else {
				foodPointA = pos;
				foodPointB = null;
				Brainless.LOGGER.info("[Stick] Food point A set: {}", pos);
			}
		} else {
			Brainless.LOGGER.info("[Stick] Block not recognized for assignment");
			return false;
		}

		return true;
	}

	@Override
	public boolean useOnEntity(@NotNull ItemStack selfStack, @NotNull Player player, @NotNull Mob mob) {
		if (!(mob instanceof MobEmployee employee)) return false;

		boolean assigned = false;

		if (pendingBed != null) {
			employee.bed_position = Optional.of(pendingBed);
			Brainless.LOGGER.info("[Stick] Bed assigned to employee at {}", pendingBed);
			pendingBed = null;
			assigned = true;
		}

		if (foodPointA != null && foodPointB != null) {
			employee.food_place = Optional.of(new PoscArea.Area2D(foodPointA, foodPointB));
			Brainless.LOGGER.info("[Stick] Food area assigned to employee: {} -> {}", foodPointA, foodPointB);
			foodPointA = null;
			foodPointB = null;
			assigned = true;
		} else if (foodPointA != null) {
			Brainless.LOGGER.info("[Stick] Food area incomplete — shift+click second chest for point B");
		}

		if (!assigned) {
			Brainless.LOGGER.info("[Stick] Nothing to assign — save a position first");
		}

		return assigned;
	}
}
