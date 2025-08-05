package deus.paperwork.entities.paperplane;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.item.PaperworkItems;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static deus.paperwork.Paperwork.MOD_ID;

public class EntityPaperPlane extends Entity {

	private int damageTaken;
	private int timeSinceHit;
	public EntityPaperPlane(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
		this.damageTaken = 0;
		this.timeSinceHit = 0;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	public void tick() {
		super.tick();
		if (this.timeSinceHit > 0) {
			this.timeSinceHit--;
		}
		if (this.damageTaken > 0) {
			this.damageTaken--;
		}

		this.xRot += 2;

		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;

		if (this.isInWater()) {
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 0.8;
			this.yd *= 0.8;
			this.zd *= 0.8;
		} else if (this.isInLava()) {
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 0.5;
			this.yd *= 0.5;
			this.zd *= 0.5;
		} else {
			float friction = 1.01F;
			if (this.onGround) {
				friction = 0.5460001F;
				int blockId = this.world.getBlockId(MathHelper.floor(this.x), MathHelper.floor(this.bb.minY) - 1, MathHelper.floor(this.z));
				if (blockId > 0) {
					friction = Blocks.blocksList[blockId].friction * 0.91F;
				}
			}


			this.yd -= 0.008F;

			this.move(this.xd, this.yd, this.zd);

			this.xd *= friction;
			this.yd *= friction;
			this.zd *= friction;

			if (Math.hypot(this.xd, this.zd) > 0.1) {
				int blockX = MathHelper.floor(this.x);
				int blockY = MathHelper.floor(this.y);
				int blockZ = MathHelper.floor(this.z);
				if (this.world.getBlockId(blockX, blockY, blockZ) != 0) {
					this.hurt(null, 5, DamageType.COMBAT);
				}
			}
		}
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {

	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {

	}

	@Override
	public boolean hurt(Entity entity, int damage, DamageType type) {
		if (!this.world.isClientSide && !this.removed) {
			this.timeSinceHit = 10;

			this.markHurt();

			if (entity!=null) {
				world.playSoundAtEntity(null, entity, MOD_ID+":material.paper.jiggle0",  1.5F, 1.5f);
			}

			if (entity instanceof Player && ((Player)entity).getGamemode() == Gamemode.creative) {
				this.remove();
			} else {
				dropOnHurt();
				this.remove();
			}

			return true;
		}
		return true;
	}

	protected void dropOnHurt() {
		this.dropItem(PaperworkItems.PAPERPLANE.id, 1, 0.0F);
	}

	@Override
	protected boolean makeStepSound() {
		return false;
	}
}
