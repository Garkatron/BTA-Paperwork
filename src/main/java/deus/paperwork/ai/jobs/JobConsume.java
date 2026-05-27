package deus.paperwork.ai.jobs;

import deus.brainless.ai.interfaces.Job;
import deus.paperwork.entities.employee.MobEmployee;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.Items;

/// Consume items (food)
public class JobConsume<CTX extends MobEmployee> implements Job<CTX> {

	boolean ate = false;

	@Override
	public String name() {
		return "job_consume";
	}

	@Override
	public void tick(CTX ctx) {
		if (!ate) {
			ctx.food_place.ifPresent((pos)->{
				if (ctx.near(pos, 3)) {
					TileEntityChest tc = ctx.getChest(pos);
					if (tc == null) {
						System.out.println("TC");
						return;
					}
					ctx.eatFoodInChest(tc, Items.FOOD_BREAD.id);
					ate = true;
				}
			});
		}

	}

	@Override
	public boolean isDone(CTX ctx) {
		return ate;
	}

	@Override
	public double progress(CTX ctx) {
		return Job.super.progress(ctx);
	}

	@Override
	public void onFinish(CTX ctx) {
		Job.super.onFinish(ctx);
	}

	@Override
	public String parentJobName() {
		return Job.super.parentJobName();
	}

	@Override
	public boolean hasParent() {
		return Job.super.hasParent();
	}

	@Override
	public void setParent(String parent) {

	}
}
