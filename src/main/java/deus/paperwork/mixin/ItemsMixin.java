package deus.paperwork.mixin;


import deus.paperwork.item.CustomItemPaper;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Items.class, remap = false)
public class ItemsMixin {
	@Inject(method = "setupItems", at = @At("TAIL"))
	private static void replaceLeatherArmor(CallbackInfo ci) {
		Item.itemsList[Items.PAPER.id] = null;
		Item.itemsMap.remove(Items.PAPER.namespaceID);
		Items.PAPER =new CustomItemPaper("paper","minecraft:item/paper", 16467);

	}
}
