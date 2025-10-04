package deus.paperwork.item;

import deus.paperwork.entities.clippy.MobClippy;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemPencil extends ItemToolSword {

	public ItemPencil(String name, String namespaceId, int id) {
		super(name, namespaceId, id, ToolMaterial.wood);
		setMaxDamage(0);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
		if (itemRand.nextInt(9) < 1) {
			setMaxDamage(20);
		} else {
			setMaxDamage(0);
		}
		return super.hitEntity(itemstack, target, attacker);
	}
}
