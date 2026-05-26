package deus.paperwork.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.DrawMode;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorShader;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RenderUtils {

	private static final Map<Entity, IconCoordinate> targets = new ConcurrentHashMap<>();

	public static void setTarget(Entity mob, IconCoordinate icon) {
		targets.put(mob, icon);
	}

	public static void clearTarget(Entity mob) {
		targets.remove(mob);
	}

	public static void clear() {
		targets.clear();
	}

	public static void renderInfo(Minecraft mc, float partialTick, long systemTime) {
		targets.entrySet().removeIf(e -> e.getKey().removed);

		for (Map.Entry<Entity, IconCoordinate> entry : targets.entrySet()) {
			renderIcon(mc, partialTick, entry.getKey(), entry.getValue());
		}
	}

	private static void renderIcon(Minecraft mc, float partialTick, Entity entity, IconCoordinate icon) {
		double scale = 0.4;
		float heightOffset = entity.getHeadHeight() + 0.6F;

		double ex = lerp(entity.xo, entity.x, partialTick);
		double ey = lerp(entity.yo, entity.y, partialTick) + heightOffset;
		double ez = lerp(entity.zo, entity.z, partialTick);

		double cx = mc.activeCamera.getX(partialTick);
		double cy = mc.activeCamera.getY(partialTick);
		double cz = mc.activeCamera.getZ(partialTick);

		float yaw   =  180 - (float) mc.activeCamera.getYRot(partialTick);
		float pitch = -(float) mc.activeCamera.getXRot(partialTick);

		GLRenderer.pushFrame();
		GLRenderer.enableState(State.BLEND);
		GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);

		GLRenderer.modelM4f()
			.translate((float)(ex - cx), (float)(ey - cy), (float)(ez - cz))
			.rotateY((float) Math.toRadians(yaw))
			.rotateX((float) Math.toRadians(pitch))
			.translate((float)(-scale / 2), 0, 0.001f);

		GLRenderer.setColor4f(1f, 1f, 1f, 1f);

		TessellatorShader t = GLRenderer.getTessellator();
		icon.parentAtlas.bind();

		t.startDrawing(DrawMode.QUADS);
		t.setTextureUV(icon.getIconUMin(), icon.getIconVMax());
		t.addVertex(0,     0,     0);
		t.setTextureUV(icon.getIconUMax(), icon.getIconVMax());
		t.addVertex(scale, 0,     0);
		t.setTextureUV(icon.getIconUMax(), icon.getIconVMin());
		t.addVertex(scale, scale, 0);
		t.setTextureUV(icon.getIconUMin(), icon.getIconVMin());
		t.addVertex(0,     scale, 0);
		t.draw();

		GLRenderer.popFrame();
	}

	private static double lerp(double old, double curr, float partial) {
		return old + (curr - old) * partial;
	}
}
