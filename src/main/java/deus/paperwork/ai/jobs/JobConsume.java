package deus.paperwork.ai.jobs;

import deus.brainless.ai.interfaces.Job;
import deus.paperwork.entities.employee.MobEmployee;

/// Consume items (food)
public class JobConsume<CTX extends MobEmployee> implements Job<CTX> {
	@Override
	public String name() {
		return "";
	}

	@Override
	public void tick(CTX ctx) {

	}

	@Override
	public boolean isDone(CTX ctx) {
		return false;
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
