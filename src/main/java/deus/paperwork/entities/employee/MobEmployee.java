package deus.paperwork.entities.employee;


import de.bsommerfeld.pathetic.api.pathing.NeighborStrategies;
import deus.brainless.ai.AI;
import deus.brainless.ai.JobQueue;
import deus.brainless.pathfinding.MobPathfinder;
import deus.utils.annotations.RegisterEntity;
import deus.utils.annotations.RegisterEntityRenderer;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;


@RegisterEntityRenderer(renderer = MobEmployeeRenderer.class)
@RegisterEntity(id = "employee", name = "employee")
public class MobEmployee extends MobPathfinder {

	private static final Supplier<AI> AI_FACTORY = AI.factory(
		brain -> {
			brain.inputs()
				.add("hunger",  0.0)
				.add("fatigue", 0.0)
				.add("danger",  0.0)
				.add("work",    0.0);
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
		ai -> {
			JobQueue queue = ai.queue(JobQueue.Mode.HIGHEST_WINS, 0.25, 3);
			queue.register("Eat",   ai.getBrain().getNode("desire_eat"),   () -> System.out.println("eats"));
			queue.register("Sleep", ai.getBrain().getNode("desire_sleep"), () -> System.out.println("sleeps"), () -> System.out.println("sleep interrupted"));
			queue.register("Flee",  ai.getBrain().getNode("desire_flee"),  () -> System.out.println("flees"),  () -> System.out.println("flee interrupted"));
			queue.register("Work",  ai.getBrain().getNode("desire_work"),  () -> System.out.println("works"));
		}
	);

	private final AI brain = AI_FACTORY.get();

	private final Optional<TilePosc> bedCoordinates  = Optional.empty();
	private final Optional<TilePosc> workCoordinates = Optional.empty();
	private final Optional<TilePosc> foodPlace       = Optional.empty();

	public MobEmployee(@NotNull World world) {
		super(world);
		setTarget(new TilePos(0, 7, 0));

	}



	@Override
	protected void updateAI() {
		super.updateAI(); // handles pathThinking + pathMotion
	}


}
