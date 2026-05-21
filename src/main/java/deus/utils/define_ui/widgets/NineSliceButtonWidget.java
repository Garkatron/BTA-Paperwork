package deus.utils.define_ui.widgets;

import net.minecraft.client.Minecraft;

public class NineSliceButtonWidget extends NineSlicePanelWidget {
    private final String label;
    private final Runnable onClick;
    private final int vNormal, vHover, vDisabled;
    private final int labelColor;
    private boolean hovered = false;
    private boolean disabled = false;
    private int absX, absY;

    public NineSliceButtonWidget(String texture, int texW, int texH, int border,
                                  int vNormal, int vHover, int vDisabled,
                                  String label, int labelColor, Runnable onClick) {
        super(texture, texW, texH, border);
        this.label = label;
        this.onClick = onClick;
        this.vNormal = vNormal;
        this.vHover = vHover;
        this.vDisabled = vDisabled;
        this.labelColor = labelColor;
    }

    @Override
    public void render(int parentX, int parentY) {
        absX = parentX + x; absY = parentY + y;
        super.render(parentX, parentY);
    }

    @Override
    public void draw(int x, int y) {
        Minecraft.getMinecraft().textureManager.loadTexture(texture).bind();
        int vOffset = disabled ? vDisabled : hovered ? vHover : vNormal;
        drawNineSlice(x, y, width, height, 0, vOffset, texW, texH, border);

        if (label != null && !label.isEmpty()) {
            int lx = x + (width  - font().stringWidth(label)) / 2;
            int ly = y + (height - 8) / 2;
            drawStringNoShadow(font(), label, lx, ly, disabled ? 0xAAAAAA : labelColor);
        }
    }

    public void disable()  { disabled = true; }
    public void enable()   { disabled = false; }

    public boolean mouseClicked(int mx, int my, int button) {
        if (!disabled && button == 0 && hits(mx, my)) { onClick.run(); return true; }
        return false;
    }

    public void mouseMoved(int mx, int my) { hovered = !disabled && hits(mx, my); }

    private boolean hits(int mx, int my) {
        return mx >= absX && mx < absX + width && my >= absY && my < absY + height;
    }
}
