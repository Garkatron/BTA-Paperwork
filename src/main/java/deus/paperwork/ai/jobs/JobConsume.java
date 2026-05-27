package deus.paperwork.ai.jobs;

import deus.brainless.ai.interfaces.Job;
import deus.paperwork.Paperwork;
import deus.paperwork.entities.employee.MobEmployee;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

import java.util.Comparator;
import java.util.List;

/// Consume items (food)
public class JobConsume<CTX extends MobEmployee> implements Job<CTX> {

	private boolean ate = false;
	private String parentName = null;

	@Override
	public String name() { return "job_consume"; }

	@Override
	public void tick(CTX ctx) {
		if (ate) return;

		ctx.food_place.ifPresentOrElse(area -> {
			// Find all chests in area
			List<TilePosc> chests = ctx.getBlocksInArea(area, Blocks.CHEST_PLANKS_OAK.id());

			if (chests.isEmpty()) {
				Paperwork.LOGGER.info("[JobConsume] No chests found in food area");
				return;
			}

			// Find nearest chest
			TilePosc nearest = chests.stream()
				.min(Comparator.comparingDouble(p ->
					Math.sqrt(Math.pow(p.x() - ctx.x, 2) + Math.pow(p.z() - ctx.z, 2))
				))
				.orElse(null);

			if (nearest == null) return;

			if (!ctx.near(nearest, 2.5)) {
				Paperwork.LOGGER.info("[JobConsume] Moving to chest at {}", nearest);
				ctx.setTarget(new TilePos(nearest.x(), nearest.y(), nearest.z()));
				return;
			}

			// Close enough — try to eat
			TileEntityChest chest = ctx.getChest(nearest);
			if (chest == null) {
				Paperwork.LOGGER.info("[JobConsume] Chest at {} is null", nearest);
				return;
			}

			boolean success = ctx.eatFoodInChest(chest, Items.FOOD_BREAD.id);
			Paperwork.LOGGER.info("[JobConsume] Eat attempt at {} -> {}", nearest, success ? "OK" : "No food found");

			if (success) {
				ctx.setTarget(null);
				ate = true;
			}

		}, () -> Paperwork.LOGGER.info("[JobConsume] food_place is empty"));
	}

	@Override
	public boolean isDone(CTX ctx) { return ate; }

	@Override
	public void onFinish(CTX ctx) {
		Paperwork.LOGGER.info("[JobConsume] onFinish — resetting");
		ate = false;
		ctx.setTarget(null);
	}

	@Override
	public String parentJobName() { return parentName; }

	@Override
	public boolean hasParent() { return parentName != null; }

	@Override
	public void setParent(String parent) { this.parentName = parent; }
}
