package deus.paperwork.ai;

import deus.brainless.ai.AI;
import deus.brainless.ai.jobs.schedulers.PersistentJobScheduler;
import deus.paperwork.ai.jobs.JobGoto;
import deus.paperwork.ai.jobs.JobStay;
import deus.paperwork.entities.employee.MobEmployee;

import java.util.List;
import java.util.function.Supplier;

public class Brains {


	public static Supplier<AI<MobEmployee>> EmployeeAI = AI.factory(
		brain -> {
			brain.inputs()
				.add("trait_social", 0.5)
				.add("trait_brave", 0.5)
				.add("trait_hardworking", 0.5)
				.add("trait_lazy", 0.5)

				.add("social", 0.0)
				.add("danger", 0.0)
				.add("fatigue", 0.0)
				.add("hunger", 0.0)
				.add("work", 0.0);
			brain.layer("desires", layer -> {
				layer.mix("desire_talk")
					.add("social", 1.0)
					.add("trait_social", 0.8)
					.sub("danger", 0.5)
					.sub("hunger", 0.3)
					.sigmoid(6);

				layer.mix("desire_scape")
					.add("danger", 1.0)
					.sub("trait_brave", 0.6)
					.sigmoid(8);

				layer.mix("desire_eat")
					.add("hunger", 1.0)
					.sub("danger", 0.5)
					.sub("fatigue", 0.2)
					.sigmoid(6);

				layer.mix("desire_rest")
					.add("fatigue", 1.0)
					.add("trait_lazy", 0.4)
					.sub("hunger", 0.3)
					.sub("danger", 0.5)
					.sigmoid(6);

				layer.mix("desire_work")
					.add("work", 1.0)
					.add("trait_hardworking", 0.6)
					.sub("hunger", 0.5)
					.sub("fatigue", 0.4)
					.sub("danger", 0.7)
					.sub("social", 0.2)
					.sigmoid(5);
			});
		},
		a -> {
			PersistentJobScheduler<MobEmployee> queue = a.persistentQueue();

			queue.register(
				AI.<MobEmployee>define("Eat", a.getBrain().getNode("desire_eat"), ctx -> List.of(
					new JobGoto<>(() -> ctx.food_place, "food", 2.0),
					new JobStay<>("food", () -> ctx.hunger, 0.15)
				)).withCategory(0).withThreshold(0.15)
			);


			queue.register(
				AI.<MobEmployee>define("Sleep", a.getBrain().getNode("desire_rest"), (ctx) -> List.of(
					new JobGoto<>(() -> ctx.bed_place, "bed", 2.0),
					new JobStay<>("bed", () -> ctx.fatigue, 0.05)
				)).withCategory(0).withThreshold(0.20)
			);

			queue.register(
				AI.<MobEmployee>define("Work", a.getBrain().getNode("desire_work"), (ctx) -> List.of(
					new JobGoto<>(() -> ctx.work_place, "work", 2.5),
					new JobStay<>("work", () -> 1.0 - ctx.work, 0.15)
				)).withCategory(0).withThreshold(0.10)
			);



		}
	);

}
