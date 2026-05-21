package deus.utils.define_ui.widgets;

import deus.utils.define_ui.core.Widget;

public class DividerWidget extends Widget {
	private final int length;
	private final boolean vertical;
	private final int color;

	public DividerWidget(int length, boolean vertical, int color) {
		this.length = length;
		this.vertical = vertical;
		this.color = color;
		if (vertical) {
			this.width = 1;
			this.height = length;
		} else {
			this.width = length;
			this.height = 1;
		}
	}

	@Override
	public void draw(int x, int y) {
		if (vertical) drawRect(x, y, x + 1, y + length, color);
		else drawRect(x, y, x + length, y + 1, color);
	}
}
