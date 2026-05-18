package deus.paperwork.entities.clippy;

import deus.paperwork.entities.base.MobPet;
import deus.paperwork.entities.singledonut.EntityDonutRenderer;
import deus.paperwork.item.PaperworkItems;
import deus.utils.annotations.RegisterEntity;
import deus.utils.annotations.RegisterEntityRenderer;
import net.minecraft.core.world.World;

import static deus.paperwork.Paperwork.MOD_ID;

@RegisterEntityRenderer(renderer = ClippyRenderer.class)
@RegisterEntity(id = "clippy", name = "clippy")
public class MobClippy extends MobPet {
	public MobClippy(World world) {
		super(world, PaperworkItems.IRON_NUGGET);
	}
}
