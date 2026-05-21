package deus.utils.define_ui.widgets;

import deus.utils.define_ui.core.Widget;
import net.minecraft.client.Minecraft;

public class ButtonWidget extends Widget {
	private final String texture;
	private final int u, v, w, h;
	private final int uHover, vHover;
	private final String label;
	private final Runnable onClick;
	private boolean hovered = false;
	private int absX, absY; // updated each render for hit-test
	private int color = 0;

	public ButtonWidget(String texture, int u, int v, int uHover, int vHover,
	                    int w, int h, String label, int color, Runnable onClick) {
		this.texture = texture;
		this.u = u;
		this.v = v;
		this.uHover = uHover;
		this.vHover = vHover;
		this.width = w;
		this.height = h;
		this.w = w;
		this.h = h;
		this.color = color;
		this.label = label;
		this.onClick = onClick;
	}

	@Override
	public void render(int parentX, int parentY) {
		absX = parentX + x;
		absY = parentY + y;
		super.render(parentX, parentY);
	}

	@Override
	public void draw(int x, int y) {
		if (texture != null) {
			Minecraft.getMinecraft().textureManager.loadTexture(texture).bind();
			int ru = hovered ? uHover : u;
			int rv = hovered ? vHover : v;
			drawTexturedModalRect(x, y, ru, rv, w, h);
		}
		if (label != null && !label.isEmpty()) {
			int lx = x + (w - font().stringWidth(label)) / 2;
			int ly = y + (h - 8) / 2;
			drawStringNoShadow(font(), label, lx, ly, color);
		}
	}

	public boolean mouseClicked(int mx, int my, int button) {
		if (button == 0 && hits(mx, my)) {
			onClick.run();
			return true;
		}
		return false;
	}

	public void mouseMoved(int mx, int my) {
		hovered = hits(mx, my);
	}

	private boolean hits(int mx, int my) {
		return mx >= absX && mx < absX + w && my >= absY && my < absY + h;
	}
}
