package deus.utils.define_ui.widgets;

import deus.utils.define_ui.core.Widget;
import deus.utils.react.State;
import net.minecraft.client.Minecraft;

public class ProgressBarWidget extends Widget {
	private final String texture;
	private final int u, v, maxW, h;
	private final State<Float> progress; // 0.0 - 1.0

	public ProgressBarWidget(String texture, int u, int v, int maxW, int h, State<Float> progress) {
		this.texture = texture;
		this.u = u;
		this.v = v;
		this.maxW = maxW;
		this.h = h;
		this.width = maxW;
		this.height = h;
		this.progress = progress;
	}

	@Override
	public void draw(int x, int y) {
		float p = progress.getRawVal() != null ? progress.getRawVal() : 0f;
		int fillW = (int) (maxW * Math.max(0f, Math.min(1f, p)));
		if (fillW <= 0) return;
		Minecraft.getMinecraft().textureManager.loadTexture(texture).bind();
		drawTexturedModalRect(x, y, u, v, fillW, h);
	}
}
