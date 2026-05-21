package deus.utils.define_ui.widgets;

import deus.utils.define_ui.core.Widget;
import net.minecraft.client.Minecraft;
public class SpriteWidget extends Widget {

	private final String texture;

	private final int u;
	private final int v;

	private final int regionW;
	private final int regionH;

	private final int texW;
	private final int texH;

	public SpriteWidget(
		String texture,
		int u,
		int v,
		int regionW,
		int regionH,
		int texW,
		int texH
	) {
		this.texture = texture;

		this.u = u;
		this.v = v;

		this.regionW = regionW;
		this.regionH = regionH;

		this.texW = texW;
		this.texH = texH;

		this.width = regionW;
		this.height = regionH;
	}

	@Override
	public void draw(int x, int y) {

		Minecraft.getMinecraft()
			.textureManager
			.loadTexture(texture)
			.bind();

		drawTexturedModalRect(
			x,
			y,
			u,
			v,
			regionW,
			regionH,
			1.0 / texW,
			1.0 / texH
		);
	}
}
