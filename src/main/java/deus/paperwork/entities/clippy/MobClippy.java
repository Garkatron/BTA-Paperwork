package deus.paperwork.entities.clippy;

import deus.paperwork.Paperwork;
import deus.paperwork.entities.MobPet;
import deus.paperwork.item.PaperworkItems;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.world.World;

public class MobClippy extends MobPet {
	public MobClippy(World world) {
		super(world, PaperworkItems.IRON_NUGGET);
	}

}
