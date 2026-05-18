package deus.utils.initializers;

import deus.paperwork.Paperwork;
import deus.utils.annotations.RegisterEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityDispatcher;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.factories.EntityFactory;
import net.minecraft.core.util.collection.NamespaceID;
import org.reflections.Reflections;

import static deus.paperwork.Paperwork.MOD_ID;

public class EntityInitializer {
	public static void initialize(String prefix) {
		Reflections reflections = new Reflections(prefix);
		for (Class<?> clazz : reflections.getTypesAnnotatedWith(RegisterEntity.class)) {
			RegisterEntity annotation = clazz.getAnnotation(RegisterEntity.class);
			if (annotation != null) {
				if (TileEntity.class.isAssignableFrom(clazz)) {
					TileEntityDispatcher.addMapping((Class<? extends TileEntity>) clazz, NamespaceID.fromPool(MOD_ID, annotation.id()));
				} else if (Entity.class.isAssignableFrom(clazz)) {
					registerEntity((Class<? extends Entity>) clazz, annotation);
				}
				Paperwork.LOGGER.info("@Registered entity -> {}", annotation.name());
			}
		}
	}

	private static <T extends Entity> void registerEntity(Class<T> entityClass, RegisterEntity annotation) {
		EntityFactory<T> factory = (w) -> {
			try {
				return entityClass.getDeclaredConstructor(net.minecraft.core.world.World.class).newInstance(w);
			} catch (Exception e) {
				throw new RuntimeException("Can't create entity: " + entityClass.getName(), e);
			}
		};

		EntityDispatcher.getInstance().addMapping(
			entityClass,
			NamespaceID.fromPool(MOD_ID, annotation.id()),
			factory
		);
	}
}
