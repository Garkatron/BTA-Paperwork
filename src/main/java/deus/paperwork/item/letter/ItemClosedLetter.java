package deus.paperwork.item.letter;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.gui.LetterUI;
import deus.paperwork.item.PaperworkItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemClosedLetter extends Item {
	public ItemClosedLetter(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		if (entityplayer.isSneaking()) {
			CompoundTag tag = itemstack.getData();

			CompoundTag newTag = new CompoundTag();
			newTag.putString("text", tag.getString("text"));

			ItemStack letter = new ItemStack(PaperworkItems.LETTER, 1);
			letter.setData(newTag);

			itemstack.consumeItem(entityplayer);
			return letter;
		}
		return itemstack;
	}

}
