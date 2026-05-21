package deus.utils.define_ui.widgets;

import deus.utils.define_ui.core.Widget;
import net.minecraft.client.Minecraft;

public class NineSlicePanelWidget extends Widget {
    protected final String texture;
    protected final int texW, texH;       // full texture size
	protected final int border;           // uniform border thickness

    public NineSlicePanelWidget(String texture, int texW, int texH, int border) {
        this.texture = texture;
        this.texW = texW; this.texH = texH;
        this.border = border;
    }

    @Override
    public void draw(int x, int y) {
        Minecraft.getMinecraft().textureManager.loadTexture(texture).bind();
        drawNineSlice(x, y, width, height, 0, 0, texW, texH, border);
    }

    protected void drawNineSlice(int x, int y, int w, int h,
                                  int u, int v, int tw, int th, int b) {
        int iw = tw - b * 2; // inner width in texture
        int ih = th - b * 2;
        int dw = w - b * 2;  // inner width on screen
        int dh = h - b * 2;

        // corners
        drawTexturedModalRect(x,         y,         u,      v,      b,  b);
        drawTexturedModalRect(x + w - b, y,         u+tw-b, v,      b,  b);
        drawTexturedModalRect(x,         y + h - b, u,      v+th-b, b,  b);
        drawTexturedModalRect(x + w - b, y + h - b, u+tw-b, v+th-b, b,  b);

        // edges — stretched
        drawTexturedModalRect(x + b, y,         u+b, v,      dw, b);
        drawTexturedModalRect(x + b, y + h - b, u+b, v+th-b, dw, b);
        drawTexturedModalRect(x,         y + b, u,      v+b, b,  dh);
        drawTexturedModalRect(x + w - b, y + b, u+tw-b, v+b, b,  dh);

        // center
        drawTexturedModalRect(x + b, y + b, u+b, v+b, dw, dh);
    }
}
