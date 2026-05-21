package deus.utils.define_ui.core;

import deus.utils.react.State;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.render.font.FontRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class WidgetNode extends Gui {
    protected final List<WidgetNode> children = new ArrayList<>();
    protected boolean hidden = false;
    public int x, y;
	public int width, height;

	State<Boolean> visibleState = null;
	String tooltip = null;

	public void deferVisible(State<Boolean> s) { this.visibleState = s; }
	public void setTooltip(String text) { this.tooltip = text; }

    public abstract void draw(int x, int y);


    public void render(int parentX, int parentY) {
        if (hidden) return;
        int ax = parentX + x, ay = parentY + y;
        draw(ax, ay);
        children.forEach(c -> c.render(ax, ay));
    }

    public WidgetNode at(int x, int y) { this.x = x; this.y = y; return this; }

    public WidgetNode add(WidgetNode... kids) {
        Collections.addAll(children, kids);
        return this;
    }

    protected FontRenderer font() {
        return Minecraft.getMinecraft().font;
    }
}
