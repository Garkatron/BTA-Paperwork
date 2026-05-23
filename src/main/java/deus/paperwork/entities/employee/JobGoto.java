package deus.paperwork.entities.employee;

import deus.brainless.ai.interfaces.Job;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

import java.util.Optional;
import java.util.function.Supplier;

public class JobGoto<CTX extends MobEmployee> implements Job<CTX> {

	private final String name;
	private final Supplier<Optional<TilePosc>> targetSupplier;
	private final double interactionRadius;
	private int pathfindCooldown = 0;
	private boolean arrived = false;
	private String parent = null;

	public JobGoto(Supplier<Optional<TilePosc>> targetSupplier, String name, double interactionRadius) {
		this.targetSupplier = targetSupplier;
		this.name = name + "_goto";
		this.interactionRadius = interactionRadius;
	}

	@Override public String name() { return name; }
	@Override public void setParent(String p) { this.parent = p; }
	@Override public String parentJobName() { return parent; }

	@Override
	public void tick(CTX mob) {
		Optional<TilePosc> targetOpt = targetSupplier.get();
		if (targetOpt.isEmpty()) {
			System.out.printf("[%s] target empty, clearing path%n", name);
			mob.setTarget(null);
			return;
		}
		TilePosc target = targetOpt.get();
		if (mob.near(target, interactionRadius)) {
			System.out.printf("[%s] arrived at %s%n", name, target);
			mob.setTarget(null);
			this.arrived = true;
			return;
		}
		if (pathfindCooldown-- <= 0) {
			pathfindCooldown = 20;
			System.out.printf("[%s] setting path target -> %s (dist=%.2f)%n",
				name, target, mob.distanceTo(target.x() + 0.5, target.y(), target.z() + 0.5));
			mob.setTarget(new TilePos(target.x(), target.y(), target.z()));
		}
	}

	@Override
	public boolean isDone(CTX mob) {
		return arrived;
	}
	@Override
	public void onFinish(CTX mob) {
		System.out.printf("[%s] onFinish, clearing target%n", name);
		mob.setTarget(null);
	}


}
