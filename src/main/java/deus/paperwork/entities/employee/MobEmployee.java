package deus.paperwork.entities.employee;

import de.bsommerfeld.pathetic.api.pathing.NeighborStrategies;
import de.bsommerfeld.pathetic.api.pathing.configuration.PathfinderConfiguration;
import de.bsommerfeld.pathetic.api.pathing.heuristic.HeuristicStrategies;
import de.bsommerfeld.pathetic.api.pathing.heuristic.HeuristicWeights;
import de.bsommerfeld.pathetic.api.pathing.processing.ValidationProcessor;
import deus.brainless.ai.AI;
import deus.brainless.pathfinding.MobPathfinder;
import deus.paperwork.ai.Brains;
import deus.paperwork.ai.pathfinding.EmployeeWalkValidator;
import deus.paperwork.util.RenderUtils;
import deus.utils.annotations.RegisterEntity;
import deus.utils.annotations.RegisterEntityRenderer;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RegisterEntityRenderer(renderer = MobEmployeeRenderer.class)
@RegisterEntity(id = "employee", name = "employee")
public class MobEmployee extends MobPathfinder {

	private static final double HUNGER_RATE = 0.0002;
	private static final double FATIGUE_RATE = 0.0001;
	private static final double WORK_RATE = 0.00015;
	private static final double SOCIAL_RATE = 0.0001;


	public Optional<TilePosc> bed_place = Optional.empty();
	public Optional<TilePosc> work_place = Optional.empty();
	public Optional<TilePosc> food_place = Optional.empty();

	public double hunger = 0.4;
	public double fatigue = 0.3;
	public double work = 0.2;
	public double danger = 0.0;
	public double social = 0.0;

	private final double traitSocial;
	private final double traitBrave;
	private final double traitHardworking;
	private final double traitLazy;

	public EmployeeAlert currentAlert = EmployeeAlert.NO_WORKPLACE;
	public EmployeeStateIcons currentLowState = EmployeeStateIcons.ASLEEP;
	public EmployeeEmotions currentEmotion = EmployeeEmotions.HAPPY;

	private final AI<MobEmployee> ai = Brains.EmployeeAI.get();

	public MobEmployee(@NotNull World world) {
		super(world);

		Random rng = new Random();
		traitSocial      = 0.3 + rng.nextDouble() * 0.7;
		traitBrave       = 0.3 + rng.nextDouble() * 0.7;
		traitHardworking = 0.3 + rng.nextDouble() * 0.7;
		traitLazy        = 0.3 + rng.nextDouble() * 0.7;

		ai.update(
			input -> input
				// Initialize traits
				.set("trait_social", traitSocial)
				.set("trait_brave", traitBrave)
				.set("trait_hardworking", traitHardworking)
				.set("trait_lazy", traitLazy),
			this
		);

		configurePathfinding();

		IconCoordinate ALERT = TextureRegistry.getTexture(EmployeeEmotions.HAPPY.path);
		RenderUtils.setTarget(this, ALERT);

	}

	private void configurePathfinding() {
		maxIterations = 10_000;
		maxLength = 128;
		strategy = NeighborStrategies.DIAGONAL_3D;
		HeuristicWeights weights = HeuristicWeights.create(
			0.0,  // Manhattan
			1.0,  // Octile — natural diagonal movement
			3.0,  // Height — still avoids climbing but less aggressive
			4.0   // Perpendicular — stronger line-following = hugs walls naturallye
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
		social = AI.clamp(social - SOCIAL_RATE);
	}

	@Override
	protected void updateAI() {
		if (this.world.isClientSide) {
			return;
		}


		if (work_place.isEmpty()) {
			currentAlert = EmployeeAlert.NO_WORKPLACE;
		}

		if (food_place.isEmpty()) {
			currentAlert = EmployeeAlert.NO_FOOD_PLACE;
		}

		if (bed_place.isEmpty()) {
			currentAlert = EmployeeAlert.NO_REST_PLACE;
		}

		if (hunger <= 0.4) {

		}

		ai.update(
			input -> input

				// Needs
				.set("social", social)
				.set("danger", danger)
				.set("fatigue", fatigue)
				.set("hunger", hunger)
				.set("work", work),
			this
		);

		if (food_place.isPresent() && near(food_place.get(), 2.0)) {
			hunger = AI.clamp(hunger - 0.003);
		}
		if (bed_place.isPresent() && near(bed_place.get(), 2.0)) {
			fatigue = AI.clamp(fatigue - 0.004);
		}
		if (work_place.isPresent() && near(work_place.get(), 2.5)) {
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
