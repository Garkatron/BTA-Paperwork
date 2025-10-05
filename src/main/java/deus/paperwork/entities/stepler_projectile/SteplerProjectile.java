package deus.paperwork.entities.stepler_projectile;

import deus.utils.annotations.RegisterEntity;
import deus.utils.annotations.RegisterEntityRenderer;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.world.World;

import static deus.paperwork.Paperwork.MOD_ID;

@RegisterEntityRenderer(renderer = SteplerProjectileRenderer.class)
@RegisterEntity(modId = MOD_ID, name = "entity_stepler_projectile", id = "entity_stepler_projectile")
public class SteplerProjectile extends Projectile {
	public SteplerProjectile(World world, Mob owner) {
		super(world, owner);
	}
	public SteplerProjectile(World world, double x, double y, double z) {
		super(world, x, y, z);
	}


}
