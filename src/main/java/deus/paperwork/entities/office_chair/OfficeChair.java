package deus.paperwork.entities.office_chair;

import deus.paperwork.entities.EntityChair;
import deus.utils.annotations.RegisterDragonflyModel;
import deus.utils.annotations.RegisterEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import static deus.paperwork.Paperwork.MOD_ID;

@RegisterDragonflyModel(id = "geometry."+MOD_ID+".office_chair", renderer = OfficeChairRenderer.class)
@RegisterEntity(modId = MOD_ID, id = "office_chair", name = "office_chair")
public class OfficeChair extends EntityChair {
	public OfficeChair(@Nullable World world) {
		super(world);
		this.setSize(0.4F, 0.8F);
	}
}
