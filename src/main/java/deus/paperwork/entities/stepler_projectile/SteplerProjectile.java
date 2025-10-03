package deus.paperwork.entities.stepler_projectile;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;

public class SteplerProjectile extends Projectile {


	public SteplerProjectile(World world, Mob owner) {
		super(world, owner);
	}
	public SteplerProjectile(World world, double x, double y, double z) {
		super(world, x, y, z);
	}


}
