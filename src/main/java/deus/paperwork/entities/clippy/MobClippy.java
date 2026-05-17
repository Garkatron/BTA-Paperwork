package deus.paperwork.entities.clippy;

import deus.paperwork.entities.base.MobPet;
import deus.paperwork.item.PaperworkItems;
import deus.utils.annotations.RegisterEntity;
import net.minecraft.core.world.World;

import static deus.paperwork.Paperwork.MOD_ID;

@RegisterEntity(modId = MOD_ID, id = "clippy", name = "clippy")
public class MobClippy extends MobPet {
	public MobClippy(World world) {
		super(world, PaperworkItems.IRON_NUGGET);
	}
}
