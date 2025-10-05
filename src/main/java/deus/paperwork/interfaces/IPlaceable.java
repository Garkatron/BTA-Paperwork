package deus.paperwork.interfaces;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public interface IPlaceable {
	boolean placeAt(World world, ItemStack itemStack, Player player, double x, double y, double z);
}
