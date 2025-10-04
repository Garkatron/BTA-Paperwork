package deus.utils;

import org.jetbrains.annotations.NotNull;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.EntityRenderer;

import javax.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterDragonflyModel {
	@NotNull Class<? extends EntityRenderer> renderer() default EntityRenderer.class;
	@NotNull String id();
	double inflation() default 0.0;
	boolean animated() default false;
}
