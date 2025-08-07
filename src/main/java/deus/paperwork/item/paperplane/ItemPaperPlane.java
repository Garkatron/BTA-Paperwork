package deus.paperwork.item.paperplane;

import deus.paperwork.entities.paperplane.EntityPaperPlane;
import deus.paperwork.item.ItemColored;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemPaintBrush;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import static deus.paperwork.Paperwork.MOD_ID;

public class ItemPaperPlane extends ItemColored {
	public ItemPaperPlane(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		EntityPaperPlane paperPlane = new EntityPaperPlane(world);
		world.playSoundAtEntity(null, entityplayer, MOD_ID+":material.paper.soft",  1.0F, 1.0f);

		DyeColor newColor = ItemPaintBrush.getColor(itemstack);
		paperPlane.setColor(newColor);
		paperPlane.spawnInit();

		// Throw it
		float yaw = entityplayer.yRot * 0.017453292F;
		float pitch = entityplayer.xRot * 0.017453292F;
		float speed = 0.95F;

		paperPlane.moveTo(entityplayer.x, entityplayer.y, entityplayer.z, entityplayer.yRot, 0);

		paperPlane.xd = -MathHelper.sin(yaw) * MathHelper.cos(pitch) * speed;
		paperPlane.yd = -MathHelper.sin(pitch) * (speed/1.5);
		paperPlane.zd = MathHelper.cos(yaw) * MathHelper.cos(pitch) * speed;

		world.entityJoinedWorld(paperPlane);
		itemstack.consumeItem(entityplayer);
		return super.onUseItem(itemstack, world, entityplayer);
	}


}
