package deus.paperwork.item.paperplane;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPaintBrush;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class ItemPaperplaneModel extends ItemModelStandard {

	public static IconCoordinate emptyIcon = TextureRegistry.getTexture("paperwork:item/paperplane/paperplane");
	public static IconCoordinate[] paintbrushIcons = new IconCoordinate[16];


	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		DyeColor color = ItemPaintBrush.getColor(itemStack);
		return color == null ? emptyIcon : paintbrushIcons[color.itemMeta];
	}

	static {
		DyeColor c;
		for(Iterator var0 = DyeColor.itemOrderedColors().iterator(); var0.hasNext(); paintbrushIcons[c.itemMeta] = TextureRegistry.getTexture("paperwork:item/paperplane/" + c.colorID)) {
			c = (DyeColor)var0.next();
		}

	}

	public ItemPaperplaneModel(Item item, String namespace) {
		super(item, namespace);
	}
}
