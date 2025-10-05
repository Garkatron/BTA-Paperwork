package deus.paperwork.entities;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class TexturedEntity extends Entity {
	public String texturePath;

	public TexturedEntity(@Nullable World world) {
		super(world);
	}

}
