package deus.paperwork.entities.clippy;

import deus.paperwork.Paperwork;
import deus.paperwork.entities.MobPet;
import deus.paperwork.item.PaperworkItems;
import deus.utils.RegisterEntity;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.world.World;

import static deus.paperwork.Paperwork.MOD_ID;

@RegisterEntity(modId = MOD_ID, id = "entity_clippy", name = "entity_clippy")
public class MobClippy extends MobPet {
	public MobClippy(World world) {
		super(world, PaperworkItems.IRON_NUGGET);
	}

}
