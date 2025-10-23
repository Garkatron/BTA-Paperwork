package deus.utils.annotations;

import org.jetbrains.annotations.NotNull;
import org.useless.dragonfly.renderer.EntityRenderer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterDragonflyModel {
	@NotNull Class<? extends EntityRenderer> renderer() default EntityRenderer.class;
	@NotNull String id();
	double inflation() default 0.0;
	@NotNull String[] animations() default {};
}
