package deus.paperwork.entities.clippy;

import deus.paperwork.entities.MobPet;
import deus.paperwork.item.PaperworkItems;
import deus.utils.RegisterEntity;
import deus.utils.RegisterEntityRenderer;
import net.minecraft.core.world.World;

import static deus.paperwork.Paperwork.MOD_ID;

@RegisterEntityRenderer(model = Modelclippy.class, renderer = ClippyRenderer.class)
@RegisterEntity(modId = MOD_ID, id = "clippy", name = "clippy")
public class MobClippy extends MobPet {
	public MobClippy(World world) {
		super(world, PaperworkItems.IRON_NUGGET);
	}

}
