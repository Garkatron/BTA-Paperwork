package deus.paperwork.entities.base;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerSimple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContainerMob implements Container {

	private final ContainerSimple container;

	public ContainerMob(@NotNull String name, int size) {
		this.container = new ContainerSimple(name, size);
	}

	public int insertItem(@NotNull ItemStack stackToAdd) {
		return insertInto(container, stackToAdd);
	}

	private int insertInto(ContainerSimple inv, ItemStack stackToAdd) {
		int original = stackToAdd.stackSize;

		for (int i = 0; i < inv.getContainerSize(); i++) {

			ItemStack stackInSlot = inv.getItem(i);

			if (stackInSlot != null && stackInSlot.canStackWith(stackToAdd)) {

				int transfer = Math.min(
					stackToAdd.stackSize,
					stackInSlot.getMaxStackSize() - stackInSlot.stackSize
				);

				transfer = Math.min(
					transfer,
					stackToAdd.getMaxStackSize()
				);

				if (transfer <= 0) continue;

				stackInSlot.stackSize += transfer;
				stackToAdd.stackSize -= transfer;

				if (stackToAdd.stackSize <= 0) {
					return original;
				}
			}
		}

		for (int i = 0; i < inv.getContainerSize(); i++) {

			if (inv.getItem(i) == null) {

				int transfer = Math.min(
					stackToAdd.stackSize,
					stackToAdd.getMaxStackSize()
				);

				ItemStack copy = stackToAdd.copy();
				copy.stackSize = transfer;

				inv.setItem(i, copy);

				stackToAdd.stackSize -= transfer;

				if (stackToAdd.stackSize <= 0) {
					return original;
				}
			}
		}

		return original - stackToAdd.stackSize;
	}


	@Override
	public int getContainerSize() {
		return container.getContainerSize();
	}

	@Override
	public @Nullable ItemStack getItem(int i) {
		return container.getItem(i);
	}

	@Override
	public @Nullable ItemStack removeItem(int i, int i1) {
		return container.removeItem(i, i1);
	}

	@Override
	public void setItem(int i, @Nullable ItemStack itemStack) {
		container.setItem(i, itemStack);
	}

	@Override
	public @NotNull String getNameTranslationKey() {
		return container.getNameTranslationKey();
	}

	@Override
	public int getMaxStackSize() {
		return container.getMaxStackSize();
	}

	@Override
	public void setChanged() {
		container.setChanged();
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return container.stillValid(player);
	}

	@Override
	public void sort() {
		container.sort();
	}

	@Override
	public boolean locked(int slot) {
		return container.locked(slot);
	}
}
