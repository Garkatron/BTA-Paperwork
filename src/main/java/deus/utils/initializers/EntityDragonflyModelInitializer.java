package deus.utils.initializers;

import deus.paperwork.Paperwork;
import deus.paperwork.mixin.IAEntityDispatcher;
import deus.utils.annotations.RegisterDragonflyModel;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.core.entity.Entity;
import org.reflections.Reflections;
import org.useless.DragonFly;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.EntityRenderer;

import java.lang.reflect.InvocationTargetException;

public class EntityDragonflyModelInitializer {

	@SuppressWarnings("unchecked")
	public static void initialize(String prefix, EntityRenderDispatcher dispatcher) {
		Reflections reflections = new Reflections(prefix);

		for (Class<?> clazz : reflections.getTypesAnnotatedWith(RegisterDragonflyModel.class)) {
			RegisterDragonflyModel annotation = clazz.getAnnotation(RegisterDragonflyModel.class);

			if (annotation != null && Entity.class.isAssignableFrom(clazz)) {
				EntityRenderer<?> rendererInstance;
				StaticEntityModel model = DragonFly.loadEntityModel(annotation.id(), annotation.inflation());


				try {
					rendererInstance = annotation.renderer()
						.getDeclaredConstructor(StaticEntityModel.class)
						.newInstance(model);
				} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new RuntimeException("Failed to instantiate renderer for entity " + clazz.getSimpleName(), e);
				}

				rendererInstance.init(dispatcher);
				((IAEntityDispatcher) dispatcher).getRenderers().put((Class<? extends Entity>) clazz, rendererInstance);

				Paperwork.LOGGER.info("Registered renderer {} for entity {}", rendererInstance.getClass().getSimpleName(), clazz.getSimpleName());
			}
		}
	}


}
