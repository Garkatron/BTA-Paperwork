package deus.utils.initializers;

import deus.paperwork.Paperwork;
import deus.utils.annotations.RegisterItemModel;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.core.item.Item;
import org.reflections.Reflections;

public class ItemModelInitializer {
	public static void initialize(String prefix, net.minecraft.client.render.item.model.ItemModelDispatcher dispatcher) {
		Reflections reflections = new Reflections(prefix);
		for (Class<?> clazz : reflections.getTypesAnnotatedWith(RegisterItemModel.class)) {
			RegisterItemModel annotation = clazz.getAnnotation(RegisterItemModel.class);
			if (annotation != null && Item.class.isAssignableFrom(clazz)) {
				try {
					Item item = findItemInstance(clazz);
					if (item == null) {
						Paperwork.LOGGER.warn("No instance found for item -> {}", clazz.getSimpleName());
						continue;
					}
					ItemModel model = annotation.model()
						.getDeclaredConstructor(Item.class)
						.newInstance(item);
					dispatcher.addDispatch(model);
					Paperwork.LOGGER.info("@Registered ItemModel for item -> {}", clazz.getName());
				} catch (Exception e) {
					Paperwork.LOGGER.error("Failed to register ItemModel for item -> {}", clazz.getSimpleName(), e);
				}
			}
		}
	}

	private static Item findItemInstance(Class<?> clazz) {
		for (Item item : Item.itemsList) {
			if (item != null && item.getClass() == clazz) return item;
		}
		return null;
	}
}
