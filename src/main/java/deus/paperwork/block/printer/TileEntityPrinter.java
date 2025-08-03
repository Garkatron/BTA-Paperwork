package deus.paperwork.block.printer;

import deus.paperwork.block.PaperworkBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFurnace;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class TileEntityPrinter extends TileEntity implements Container {

	protected ItemStack[] itemStacks = new ItemStack[5];
	public int maxPrintTime = 200;
	public int currentPrintTime = maxPrintTime;
	boolean activated = true;

	public TileEntityPrinter() {
		this.itemStacks = new ItemStack[6];
		this.maxPrintTime = 200;
		this.currentPrintTime = 200;
		this.activated = true;
	}

	public void tick() {
		if (this.currentPrintTime > 0) {
			--this.currentPrintTime;
		} else if (activated) {
			print();
		}
	}

	public boolean removeLayer(World world, Block<?> block, int blockX, int blockY, int blockZ) {
		Block<?> targetBlock = world.getBlock(blockX, blockY, blockZ);
		int meta = world.getBlockMetadata(blockX, blockY, blockZ);

		if (targetBlock == block && meta > 0) {
			world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, block.id(), meta - 1);
			//world.playBlockSoundEffect(player, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), block, EnumBlockSoundEffectType.BREAK);
			return true;
		} else if (targetBlock == block && meta == 0) {
			world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, 0, 0);
			//world.playBlockSoundEffect(player, (double)((float)blockX + 0.5F), (double)((float)blockY + 0.5F), (double)((float)blockZ + 0.5F), block, EnumBlockSoundEffectType.BREAK);
			return true;
		}

		return false;
	}

	protected void print() {
		if (worldObj.getBlockId(x, y, z) == PaperworkBlocks.BLOCK_PAPER_PILE.id()) {
			removeLayer(worldObj, PaperworkBlocks.BLOCK_PAPER_PILE, x, y, z);
		}
		activated = false;
	}


	@Override public int getContainerSize() {
		return this.itemStacks.length;
	}

	@Override public @Nullable ItemStack getItem(int index) {
		return this.itemStacks[index];
	}

	@Override public @Nullable ItemStack removeItem(int index, int takeAmount) {
		if (this.itemStacks[index] != null) {
			ItemStack itemstack1;
			if (this.itemStacks[index].stackSize <= takeAmount) {
				itemstack1 = this.itemStacks[index];
				this.itemStacks[index] = null;
				if (this.worldObj != null && index == 2) {
					this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
				}

				return itemstack1;
			} else {
				itemstack1 = this.itemStacks[index].splitStack(takeAmount);
				if (this.itemStacks[index].stackSize <= 0) {
					this.itemStacks[index] = null;
					if (this.worldObj != null && index == 2) {
						this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
					}
				}

				return itemstack1;
			}
		} else {
			return null;
		}
	}

	@Override public void setItem(int index, @Nullable ItemStack itemstack) {
		this.itemStacks[index] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getMaxStackSize()) {
			itemstack.stackSize = this.getMaxStackSize();
		}

		if (this.worldObj != null && index == 2 && itemstack == null) {
			this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
		}

	}

	@Override public String getNameTranslationKey() {
		return "container.furnace.name";
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	public boolean stillValid(Player entityplayer) {
		if (this.worldObj != null && this.worldObj.getTileEntity(this.x, this.y, this.z) == this) {
			return entityplayer.distanceToSqr((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) <= 64.0;
		} else {
			return false;
		}
	}

	@Override public void sortContainer() {
	}
}
