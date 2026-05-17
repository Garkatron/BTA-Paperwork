package deus.utils.initializers;

import deus.paperwork.Paperwork;
import deus.paperwork.mixin.IAEntityDispatcher;
import deus.utils.annotations.RegisterEntityRenderer;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.core.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.useless.dragonfly.models.entity.StaticEntityModel;

public class EntityModelInitializer {

	@SuppressWarnings("unchecked")
	public static void initialize(String prefix, EntityRendererDispatcher dispatcher) {
		Reflections reflections = new Reflections(prefix);

		for (Class<?> clazz : reflections.getTypesAnnotatedWith(RegisterEntityRenderer.class)) {
			RegisterEntityRenderer annotation = clazz.getAnnotation(RegisterEntityRenderer.class);

			if (annotation != null && Entity.class.isAssignableFrom(clazz)) {
				try {
					EntityRenderer<?> rendererInstance;

					if (annotation.model() != StaticEntityModel.class) {
						StaticEntityModel modelInstance = annotation.model().getDeclaredConstructor().newInstance();
						rendererInstance = annotation.renderer()
							.getDeclaredConstructor(annotation.model())
							.newInstance(modelInstance);
					} else {
						rendererInstance = annotation.renderer()
							.getDeclaredConstructor()
							.newInstance();
					}
					addEntityModel(dispatcher, (Class<? extends Entity>) clazz, rendererInstance);

					Paperwork.LOGGER.info("@Registered Renderer & Model for entity -> {}", clazz.getName());
				} catch (Exception e) {
					Paperwork.LOGGER.error("Failed to register Renderer & Model for entity -> {}", clazz.getSimpleName(), e);
				}
			}
		}
	}

	public static void addEntityModel(EntityRendererDispatcher dispatcher, @NotNull Class<? extends Entity> clazz, EntityRenderer<?> renderer) {
//		renderer.init(dispatcher);
//		((IAEntityDispatcher) dispatcher).getRenderers().put(clazz, renderer);
	}
}
