package deus.utils.annotations;

import net.minecraft.client.render.item.model.ItemModel;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterItemModel {
	@NotNull Class<? extends ItemModel> model();
}
