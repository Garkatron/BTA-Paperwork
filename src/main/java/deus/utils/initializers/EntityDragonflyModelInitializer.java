package deus.utils.initializers;

import deus.paperwork.Paperwork;
import deus.paperwork.mixin.IAEntityDispatcher;
import deus.utils.annotations.RegisterDragonflyModel;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.core.entity.Entity;
import org.reflections.Reflections;
import org.useless.DragonFly;
import org.useless.dragonfly.animation.Animation;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.EntityRenderer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static deus.paperwork.Paperwork.MOD_ID;

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
					List<Animation> loadedAnimations = new ArrayList<>();
					for (String animName : annotation.animations()) {
						Animation anim = DragonFly.loadEntityAnimations(MOD_ID, animName);
						loadedAnimations.add(anim);
					}

					if (loadedAnimations.isEmpty()) {
						rendererInstance = annotation.renderer()
							.getDeclaredConstructor(StaticEntityModel.class)
							.newInstance(model);
					} else {
						rendererInstance = annotation.renderer()
							.getDeclaredConstructor(StaticEntityModel.class, List.class)
							.newInstance(model, loadedAnimations);
						Paperwork.LOGGER.info("Loaded animations: {}", loadedAnimations);
					}

				} catch (InstantiationException | IllegalAccessException |
						 InvocationTargetException | NoSuchMethodException e) {
					throw new RuntimeException("Failed to instantiate renderer for entity " + clazz.getSimpleName(), e);
				}


				rendererInstance.init(dispatcher);
				((IAEntityDispatcher) dispatcher).getRenderers().put((Class<? extends Entity>) clazz, rendererInstance);

				Paperwork.LOGGER.info("Registered renderer {} for entity {}", rendererInstance.getClass().getSimpleName(), clazz.getSimpleName());
			}
		}
	}


}
