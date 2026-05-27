package deus.paperwork.ai.jobs;

import deus.brainless.ai.interfaces.Job;
import deus.paperwork.Paperwork;
import deus.paperwork.entities.employee.MobEmployee;
import deus.paperwork.entities.employee.farmer.MobEmployeeFarmer;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

import java.util.Comparator;
import java.util.List;
public class JobFarm<CTX extends MobEmployeeFarmer> implements Job<CTX> {

	private TilePos currentTarget = null;
	private boolean isHarvestTask = false;
	private String parentName = null;

	@Override
	public String name() { return "job_farm"; }

	@Override
	public void tick(CTX ctx) {
		ctx.farm_area.ifPresent(area2D -> {

			// Already has a target — keep moving toward it
			if (currentTarget != null) {
				ctx.setTarget(currentTarget);

				if (ctx.near(currentTarget, 1.2)) {
					if (isHarvestTask) {
						TilePos crop = new TilePos(currentTarget.x(), currentTarget.y(), currentTarget.z());
						boolean harvested = ctx.harvest(crop);
						Paperwork.LOGGER.info("[JobFarm] Harvest at {} -> {}", crop, harvested ? "OK" : "FAILED");
					} else {
						TilePos soil = new TilePos(currentTarget.x(), currentTarget.y(), currentTarget.z());
						boolean seeded = ctx.seed(soil);
						Paperwork.LOGGER.info("[JobFarm] Seed at {} -> {}", soil, seeded ? "OK" : "FAILED");
					}
					currentTarget = null;
					Paperwork.LOGGER.info("[JobFarm] Target cleared, next tick will scan");
				}
				return;
			}

			// Scan harvestable
			List<TilePosc> harvestable = ctx.getHarvestableBlocksInArea(area2D).stream()
				.sorted(Comparator.comparingInt(TilePosc::z).thenComparingInt(TilePosc::x))
				.toList();

			if (!harvestable.isEmpty()) {
				TilePosc ground = harvestable.get(0);
				currentTarget = new TilePos(ground.x(), ground.y() + 1, ground.z());
				isHarvestTask = true;
				ctx.setTarget(currentTarget);
				Paperwork.LOGGER.info("[JobFarm] Harvest target set: {} ({} crops ready)", currentTarget, harvestable.size());
				return;
			}

			// Scan sowable
			List<TilePosc> sowable = ctx.getSowableBlocksInArea(area2D).stream()
				.sorted(Comparator.comparingInt(TilePosc::z).thenComparingInt(TilePosc::x))
				.toList();

			if (!sowable.isEmpty()) {
				TilePosc ground = sowable.get(0);
				currentTarget = new TilePos(ground.x(), ground.y() + 1, ground.z());
				isHarvestTask = false;
				ctx.setTarget(currentTarget);
				Paperwork.LOGGER.info("[JobFarm] Sow target set: {} ({} tiles available)", currentTarget, sowable.size());
				return;
			}

			// Nothing to do
			Paperwork.LOGGER.info("[JobFarm] No harvestable or sowable tiles found in area");
			ctx.setTarget(null);
		});

		if (ctx.farm_area.isEmpty()) {
			Paperwork.LOGGER.info("[JobFarm] farm_area is empty, nothing to do");
		}
	}

	@Override
	public boolean isDone(CTX ctx) {
		// Farm job runs indefinitely until interrupted
		return false;
	}

	@Override
	public double progress(CTX ctx) {
		if (ctx.farm_area.isEmpty()) return 0;
		int total     = ctx.getBlocksInArea(ctx.farm_area.get()).size();
		int remaining = ctx.getHarvestableBlocksInArea(ctx.farm_area.get()).size()
			+ ctx.getSowableBlocksInArea(ctx.farm_area.get()).size();
		if (total == 0) return 1.0;
		return 1.0 - ((double) remaining / total);
	}

	@Override
	public void onFinish(CTX ctx) {
		Paperwork.LOGGER.info("[JobFarm] onFinish — clearing target");
		currentTarget = null;
		ctx.setTarget(null);
	}

	@Override
	public String parentJobName() { return parentName; }

	@Override
	public boolean hasParent() { return parentName != null; }

	@Override
	public void setParent(String parent) { this.parentName = parent; }
}
