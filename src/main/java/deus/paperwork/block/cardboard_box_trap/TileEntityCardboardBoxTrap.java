package deus.paperwork.block.cardboard_box_trap;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.world.ICarriable;
import net.minecraft.core.world.World;

public class TileEntityCardboardBoxTrap extends TileEntity {
	private int savedBlockId = 0;
	private int savedBlockMeta = 0;
	private TileEntity tileEntity = null;
	private Entity entity = null;

	public boolean canBeCarried(World world, Entity potentialHolder) {
		return true;
	}

	@Override
	public void writeToNBT(CompoundTag nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.putInt("SavedBlock", savedBlockId);
		nbttagcompound.putInt("SavedBlockMeta", savedBlockMeta);
		if (entity!=null) {
			nbttagcompound.putString("SavedEntityNamespace", String.valueOf(EntityDispatcher.idForClass(entity.getClass())));
		}
	}

	public void setSavedBlockId(int savedBlockId) {
		this.savedBlockId = savedBlockId;
	}

	public void setSavedBlockMeta(int savedBlockMeta) {
		this.savedBlockMeta = savedBlockMeta;
	}

	public int getSavedBlockId() {
		return savedBlockId;
	}

	public int getSavedBlockMeta() {
		return savedBlockMeta;
	}

	public void setTileEntity(TileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	public TileEntity getTileEntity() {
		return tileEntity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
