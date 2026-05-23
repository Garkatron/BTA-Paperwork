package deus.paperwork.entities.employee;

import de.bsommerfeld.pathetic.api.pathing.NeighborStrategies;
import de.bsommerfeld.pathetic.api.pathing.configuration.PathfinderConfiguration;
import de.bsommerfeld.pathetic.api.pathing.heuristic.HeuristicStrategies;
import de.bsommerfeld.pathetic.api.pathing.heuristic.HeuristicWeights;
import de.bsommerfeld.pathetic.api.pathing.processing.ValidationProcessor;
import deus.brainless.ai.AI;
import deus.brainless.ai.jobs.JobDefinition;
import deus.brainless.ai.jobs.schedulers.PersistentJobScheduler;
import deus.brainless.pathfinding.MobPathfinder;
import deus.utils.annotations.RegisterEntity;
import deus.utils.annotations.RegisterEntityRenderer;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@RegisterEntityRenderer(renderer = MobEmployeeRenderer.class)
@RegisterEntity(id = "employee", name = "employee")
public class MobEmployee extends MobPathfinder {

	private static final double HUNGER_RATE = 0.0002;
	private static final double FATIGUE_RATE = 0.0001;
	private static final double WORK_RATE = 0.00015;

	private final AI<MobEmployee> ai;

	public Optional<TilePosc> bedCoordinates = Optional.empty();
	public Optional<TilePosc> workCoordinates = Optional.empty();
	public Optional<TilePosc> foodPlace = Optional.empty();

	private double hunger = 0.4;
	private double fatigue = 0.3;
	private double work = 0.2;
	private double danger = 0.0;

	public MobEmployee(@NotNull World world) {
		super(world);
		maxIterations = 10_000;
		maxLength = 128;
		strategy = NeighborStrategies.DIAGONAL_3D;
		HeuristicWeights weights = HeuristicWeights.create(
			0.0,  // Manhattan
			1.0,  // Octile — natural diagonal movement
			3.0,  // Height — still avoids climbing but less aggressive
			4.0   // Perpendicular — stronger line-following = hugs walls naturally
		);
		pathFinderConfig = PathfinderConfiguration.builder()
			.async(true)
			.fallback(true)
			.maxIterations(maxIterations)
			.maxLength(maxLength)
			.neighborStrategy(strategy)
			.validationProcessors(buildValidators())
			.provider(provider)
			.heuristicWeights(weights)
			.heuristicStrategy(HeuristicStrategies.LINEAR)
			.build();

		ai = AI.<MobEmployee>factory(
			brain -> {
				brain.inputs()
					.add("hunger", 0.0)
					.add("fatigue", 0.0)
					.add("danger", 0.0)
					.add("work", 0.0);
				brain.layer("desires", layer -> {
					layer.mix("desire_eat")
						.add("hunger", 1.0).sub("danger", 0.7).sigmoid(6);
					layer.mix("desire_sleep")
						.add("fatigue", 1.0).sub("danger", 0.9).sigmoid(6);
					layer.mix("desire_flee")
						.add("danger", 1.0).sigmoid(8);
					layer.mix("desire_work")
						.add("work", 1.0).sub("hunger", 0.5).sub("fatigue", 0.4).sigmoid(5);
				});
			},
			a -> {
				PersistentJobScheduler<MobEmployee> queue = a.persistentQueue();

				queue.register(
					AI.define("Eat", a.getBrain().getNode("desire_eat"), () -> List.of(
						new JobGoto<>(() -> foodPlace, "food", 2.0),
						new JobStay<>("food", () -> hunger, 0.15)
					)).withCategory(0).withThreshold(0.15)
				);


				queue.register(
					AI.define("Sleep", a.getBrain().getNode("desire_sleep"), () -> List.of(
						new JobGoto<>(() -> bedCoordinates, "bed", 2.0),
						new JobStay<>("bed", () -> fatigue, 0.05)
					)).withCategory(0).withThreshold(0.20)
				);

				queue.register(
					AI.define("Work", a.getBrain().getNode("desire_work"), () -> List.of(
						new JobGoto<>(() -> workCoordinates, "work", 2.5),
						new JobStay<>("work", () -> 1.0 - work, 0.15)
					)).withCategory(0).withThreshold(0.10)
				);

				queue.register(
					AI.define("Flee", a.getBrain().getNode("desire_flee"), () -> List.of(
						AI.<MobEmployee>inlineJob("flee", o -> {
						}, o -> danger <= 0.1, o -> 0.0, o -> {
						})
					)).withCategory(2)
				);
			}
		).get();
	}

	@Override
	protected List<ValidationProcessor> buildValidators() {
		return List.of(new EmployeeWalkValidator(this.world));
	}

	@Override
	public void tick() {
		super.tick();

		if (this.world.isClientSide) {
			return;
		}

		hunger = AI.clamp(hunger + HUNGER_RATE);
		fatigue = AI.clamp(fatigue + FATIGUE_RATE);
		work = AI.clamp(work - WORK_RATE);
	}

	@Override
	protected void updateAI() {
		if (this.world.isClientSide) {
			return;
		}

		ai.update(
			input -> input
				.set("hunger", hunger)
				.set("fatigue", fatigue)
				.set("danger", danger)
				.set("work", work),
			this
		);

		if (foodPlace.isPresent() && near(foodPlace.get(), 2.0)) {
			hunger = AI.clamp(hunger - 0.003);
		}
		if (bedCoordinates.isPresent() && near(bedCoordinates.get(), 2.0)) {
			fatigue = AI.clamp(fatigue - 0.004);
		}
		if (workCoordinates.isPresent() && near(workCoordinates.get(), 2.5)) {
			work = AI.clamp(work + 0.003);
		}

		super.updateAI();
	}

	public boolean near(TilePosc pos, double dist) {
		double dx = pos.x() + 0.5 - this.x;
		double dz = pos.z() + 0.5 - this.z;
		return Math.sqrt(dx * dx + dz * dz) <= dist;
	}

	public void setDanger(double danger) { this.danger = danger; }
}
