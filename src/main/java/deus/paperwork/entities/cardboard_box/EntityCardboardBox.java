package deus.paperwork.entities.cardboard_box;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import deus.paperwork.entities.motion.CarriedEntity;
import deus.paperwork.interfaces.IPaperworkDisplay;
import deus.paperwork.interfaces.IItemWeight;
import deus.paperwork.item.PaperworkItems;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventorySorter;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.ICarriable;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCardboardBox extends Entity implements Container {
	private ItemStack[] chestContents = new ItemStack[36];

	private BoxSize boxSize = BoxSize.SMALL;
	private final double DEFAULT_GRAVITY = 0.1F;
	public boolean slotsLocked = false;

	public EntityCardboardBox(@Nullable World world) {
		super(world);
		this.setSize(0.8F, 0.8F);
	}

	public BoxSize getBoxSize() {
		return boxSize;
	}

	public void setBoxSize(BoxSize boxSize) {
		this.boxSize = boxSize;
		this.setSize(boxSize.getSize(),boxSize.getSize());
		ItemStack[] newContents = new ItemStack[getContainerSize()];
		for (int i = 0; i < Math.min(newContents.length, chestContents.length); i++) {
			newContents[i] = chestContents[i];
		}
		chestContents = newContents;
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

		if (compoundTag.containsKey("BoxSize")) {
			this.setBoxSize(BoxSize.values()[compoundTag.getInteger("BoxSize")]);
		}

	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		compoundTag.put("Items", save());
		compoundTag.putInt("BoxSize", this.getBoxSize().ordinal());
	}


	private ListTag save() {
		ListTag nbttaglist = new ListTag();
		for(int i = 0; i < this.chestContents.length; ++i) {
			if (this.chestContents[i] != null) {
				CompoundTag nbttagcompound1 = new CompoundTag();
				nbttagcompound1.putByte("Slot", (byte)i);
				this.chestContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.addTag(nbttagcompound1);
			}
		}
		return nbttaglist;
	}


	private void updateWeightStatus() {
		// double gravity = calcGravity();
		// slotsLocked = gravity < boxSize.getMaxWeight();
	}


	private double calcGravity() {
		double totalWeight = 0;
		if (chestContents == null) return 0;

		int limit = Math.min(chestContents.length, getContainerSize());

		for (int i = 0; i < limit; i++) {
			ItemStack stack = chestContents[i];
			if (stack != null && stack.getItem() instanceof IItemWeight) {
				totalWeight += stack.stackSize *
					((IItemWeight) stack.getItem()).paperwork$getWeight();
			}
		}
		return totalWeight;
	}


	@Override
	public void tick() {
		super.tick();

		updateWeightStatus();

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

		if (player.isSneaking()) {
			player.setHeldObject(new CarriedEntity(player,this));
			remove();
			return false;
		} else {
			((IPaperworkDisplay)player).paperwork$displayCardboardBoxScreen(this);
		}

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

	@Override
	public boolean hurt(Entity attacker, int baseDamage, DamageType type) {

		if (attacker instanceof Player || type == DamageType.DROWN || type == DamageType.FIRE) {
			ItemStack stack = null;

			switch (boxSize) {
				case SMALL: stack =  new ItemStack(PaperworkItems.CARDBOARD_BOX_SMALL); break;
				case MEDIUM: stack =  new ItemStack(PaperworkItems.CARDBOARD_BOX_MEDIUM); break;
				case REGULAR: stack =  new ItemStack(PaperworkItems.CARDBOARD_BOX_REGULAR); break;
				case LARGE: stack =  new ItemStack(PaperworkItems.CARDBOARD_BOX_LARGE); break;
			}
//
//			CompoundTag tag = new CompoundTag();
//			tag.put("Items", save());
//			stack.setData(tag);



			dropItem(stack, 0);
			dropContents(world, (int) x, (int) y, (int) z);
			this.remove();
			return super.hurt(attacker, baseDamage, type);
		}

		return false;
	}

	public void dropContents(World world, int x, int y, int z) {

		for(int i = 0; i < this.getContainerSize(); ++i) {
			ItemStack itemStack = this.getItem(i);
			if (itemStack != null) {
				EntityItem item = world.dropItem(x, y, z, itemStack);
				item.xd *= 0.5;
				item.yd *= 0.5;
				item.zd *= 0.5;
				item.pickupDelay = 0;
			}
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
		updateWeightStatus();
	}

	public boolean stillValid(Player entityplayer) {
		return true;
	}

	public void sortContainer() {
		InventorySorter.sortInventory(this.chestContents);
	}

	public int getContainerSize() {
		return boxSize.getSlots();
	}

	public double getMaxWeight() {
		return boxSize.getMaxWeight();
	}

//	@Override
//	public void heldTick(World world, Entity entity) {
//
//	}
//
//	@Override
//	public boolean tryPlace(World world, Entity holder, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
//		if (world.isClientSide) return false;
//
//		EntityCardboardBox newBox = new EntityCardboardBox(world);
//		CompoundTag data = new CompoundTag();
//		this.addAdditionalSaveData(data);
//		newBox.readAdditionalSaveData(data);
//
//		double px = blockX + side.getOffsetX() + 0.5;
//		double py = blockY + side.getOffsetY();
//		double pz = blockZ + side.getOffsetZ() + 0.5;
//
//		newBox.moveTo(px, py, pz, holder.yRot, holder.xRot);
//		world.entityJoinedWorld(newBox);
//
//		return true;
//	}
//
//	@Override
//	public void drop(World world, Entity holder) {
//		int baseX = MathHelper.floor(holder.x);
//		int baseY = MathHelper.floor(holder.y);
//		int baseZ = MathHelper.floor(holder.z);
//
//		for (int y = baseY - 1; y <= baseY + 1; y++) {
//			for (int x = baseX - 1; x <= baseX + 1; x++) {
//				for (int z = baseZ - 1; z <= baseZ + 1; z++) {
//					if (tryPlace(world, holder, x, y, z, Side.TOP, 0.0, 0.0)) {
//						return;
//					}
//				}
//			}
//		}
//
//		EntityCardboardBox newBox = new EntityCardboardBox(world);
//		CompoundTag data = new CompoundTag();
//		this.addAdditionalSaveData(data);
//		newBox.readAdditionalSaveData(data);
//		newBox.moveTo(holder.x, holder.y, holder.z, 0, 0);
//		world.entityJoinedWorld(newBox);
//	}
//
//	@Override
//	public boolean canBeCarried(World world, Entity entity) {
//		return calcGravity() <= this.getMaxWeight();
//	}
//
//	@Override
//	public ICarriable pickup(World world, Entity entity) {
//		return this;
//	}
//
//	@Override
//	public void writeToNBT(CompoundTag tag) {
//		tag.put("Items", save());
//		tag.putInt("BoxSize", this.boxSize.ordinal());
//		tag.putString("type", "cardboard_box");
//	}
//
//	@Override
//	public void readFromNBT(CompoundTag tag) {
//		if (tag.containsKey("Items")) {
//			ListTag nbttaglist = tag.getList("Items");
//			this.chestContents = new ItemStack[this.getContainerSize()];
//
//			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
//				CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.tagAt(i);
//				int slot = nbttagcompound1.getByte("Slot") & 255;
//				if (slot >= 0 && slot < this.chestContents.length) {
//					this.chestContents[slot] = ItemStack.readItemStackFromNbt(nbttagcompound1);
//				}
//			}
//		}
//
//		if (tag.containsKey("BoxSize")) {
//			this.setBoxSize(BoxSize.values()[tag.getInteger("BoxSize")]);
//		}
//	}

}
