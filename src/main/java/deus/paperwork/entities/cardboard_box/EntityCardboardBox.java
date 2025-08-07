package deus.paperwork.entities.cardboard_box;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import deus.paperwork.interfaces.IPaperworkDisplay;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventorySorter;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCardboardBox extends Entity implements Container {
	private ItemStack[] chestContents = new ItemStack[36];

	private final double DEFAULT_GRAVITY = 0.1F;
	private final double WEIGHT_PER_ITEM = 0.0001F;
	private double currentGravity = DEFAULT_GRAVITY;

	public EntityCardboardBox(@Nullable World world) {
		super(world);
		this.setSize(0.8F, 0.8F);
	}

	@Override
	protected void defineSynchedData() {

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
		ListTag nbttaglist = compoundTag.getList("Items");
		this.chestContents = new ItemStack[this.getContainerSize()];

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.tagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
			if (j >= 0 && j < this.chestContents.length) {
				this.chestContents[j] = ItemStack.readItemStackFromNbt(nbttagcompound1);
			}
		}

	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		ListTag nbttaglist = new ListTag();

		for(int i = 0; i < this.chestContents.length; ++i) {
			if (this.chestContents[i] != null) {
				CompoundTag nbttagcompound1 = new CompoundTag();
				nbttagcompound1.putByte("Slot", (byte)i);
				this.chestContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.addTag(nbttagcompound1);
			}
		}

		compoundTag.put("Items", nbttaglist);
	}




	private double calcGravity() {
		int items = 0;
		if (chestContents.length == 0) return 0;
		for (ItemStack chestContent : chestContents) {
			if (chestContent != null) {
				items += chestContent.stackSize;
			}
		}
		return WEIGHT_PER_ITEM * items;
	}
	@Override
	public void tick() {
		super.tick();

		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;

		if (this.isInWater()) {
			double gravityEffect = calcGravity();
			double buoyancy = 0.05 - gravityEffect;

			this.yd += buoyancy;

			this.move(this.xd, this.yd, this.zd);

			this.xd *= 0.9;
			this.yd *= 0.9;
			this.zd *= 0.9;

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

			this.yd -= DEFAULT_GRAVITY + calcGravity();

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
	public boolean interact(@NotNull Player player) {

		((IPaperworkDisplay)player).paperwork$displayCardboardBoxScreen(this);

		return true;
	}



	public @Nullable ItemStack getItem(int index) {
		return this.chestContents[index];
	}

	public @Nullable ItemStack removeItem(int index, int takeAmount) {
		if (this.chestContents[index] != null) {
			ItemStack itemstack1;
			if (this.chestContents[index].stackSize <= takeAmount) {
				itemstack1 = this.chestContents[index];
				this.chestContents[index] = null;
				this.setChanged();
				return itemstack1;
			} else {
				itemstack1 = this.chestContents[index].splitStack(takeAmount);
				if (this.chestContents[index].stackSize <= 0) {
					this.chestContents[index] = null;
				}

				this.setChanged();
				return itemstack1;
			}
		} else {
			return null;
		}
	}

	public void setItem(int index, @Nullable ItemStack itemstack) {
		this.chestContents[index] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getMaxStackSize()) {
			itemstack.stackSize = this.getMaxStackSize();
		}

		this.setChanged();
	}

	public String getNameTranslationKey() {
		return "container.chest.name";
	}

	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public void setChanged() {

	}

	public boolean stillValid(Player entityplayer) {
		return true;
	}

	public void sortContainer() {
		InventorySorter.sortInventory(this.chestContents);
	}

	public int getContainerSize() {
		return 27;
	}
}
