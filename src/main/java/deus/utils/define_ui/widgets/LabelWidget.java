package deus.utils.define_ui.widgets;

import deus.utils.define_ui.core.PlainState;
import deus.utils.define_ui.core.Widget;
import deus.utils.react.State;

public class LabelWidget extends Widget {
	private final State<String> text;
	private final int color;
	private final boolean shadow;

	public LabelWidget(String text, int color, boolean shadow) {
		this.text  = new PlainState<>(text);
		this.color = color;
		this.shadow = shadow;
	}

	public LabelWidget(State<String> text, int color, boolean shadow) {
		this.text  = text;
		this.color = color;
		this.shadow = shadow;
	}
	public LabelWidget(State<String> text, int color) {
		this.text = text;
		this.color = color;
		this.shadow = false;
	}

	@Override
	public void draw(int x, int y) {
		String val = text.getRawVal();
		if (val == null) return;
		if (shadow) drawStringShadow(font(), val, x, y, color);
		else drawStringNoShadow(font(), val, x, y, color);
	}
}
