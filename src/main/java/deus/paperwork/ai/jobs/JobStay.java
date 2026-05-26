package deus.paperwork.ai.jobs;

import deus.brainless.ai.interfaces.Job;
import deus.paperwork.entities.employee.MobEmployee;

import java.util.function.Supplier;

/// Wait until a higher priority task takes its place.
public class JobStay<CTX extends MobEmployee> implements Job<CTX> {

	private final String name;
	private final Supplier<Double> desireSupplier;
	private final double satisfiedThreshold;
	private String parent = null;

	public JobStay(String name, Supplier<Double> desireSupplier, double satisfiedThreshold) {
		this.name = name + "_stay";
		this.desireSupplier = desireSupplier;
		this.satisfiedThreshold = satisfiedThreshold;
	}

	@Override public String name() { return name; }
	@Override public void setParent(String p) { this.parent = p; }
	@Override public String parentJobName() { return parent; }

	@Override
	public void tick(CTX ctx) {
		System.out.printf("[%s] staying... desire=%.3f threshold=%.3f%n",
			name, desireSupplier.get(), satisfiedThreshold);
	}

	@Override
	public boolean isDone(CTX mob) {
		boolean done = desireSupplier.get() <= satisfiedThreshold;
		if (done) System.out.printf("[%s] satisfied, done%n", name);
		return done;
	}

}
