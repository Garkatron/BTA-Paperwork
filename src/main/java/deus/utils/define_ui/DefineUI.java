package deus.utils.define_ui;

import deus.utils.define_ui.core.FlexLayout;
import deus.utils.define_ui.core.Widget;
import deus.utils.define_ui.widgets.*;
import deus.utils.react.State;

public class DefineUI {

	// ─── Factories ────────────────────────────────────────────────────────────

	public static SpriteBuilder sprite(String texture, int u, int v, int w, int h, int texW, int texH) {
		return new SpriteBuilder(texture, u, v, w, h, texW, texH);
	}

	public static LabelBuilder label(String text) {
		return new LabelBuilder(text);
	}

	public static LabelBuilder label(State<String> text) {
		return new LabelBuilder(text);
	}

	public static ButtonBuilder button(String label, Runnable onClick) {
		return new ButtonBuilder(label, onClick);
	}

	public static ProgressBuilder progress(String texture, int u, int v, int maxW, int h, State<Float> state) {
		return new ProgressBuilder(texture, u, v, maxW, h, state);
	}

	public static DividerBuilder divider(int length, boolean vertical) {
		return new DividerBuilder(length, vertical);
	}

	public static ContainerWidget container(Widget... children) {
		ContainerWidget c = new ContainerWidget();
		c.add(children);
		return c;
	}

	public static ContainerWidget row(int gap, Widget... children) {
		ContainerWidget c = new ContainerWidget();
		c.add(children);
		FlexLayout.apply(c, FlexLayout.Direction.ROW, gap);
		return c;
	}

	public static ContainerWidget column(int gap, Widget... children) {
		ContainerWidget c = new ContainerWidget();
		c.add(children);
		FlexLayout.apply(c, FlexLayout.Direction.COLUMN, gap);
		return c;
	}

	// ─── Base Builder ─────────────────────────────────────────────────────────

	public abstract static class WidgetBuilder<B extends WidgetBuilder<B, W>, W extends Widget> {
		protected int x, y, width, height;
		protected State<Boolean> visible = null;
		protected String tooltip = null;

		@SuppressWarnings("unchecked")
		public B at(int x, int y) { this.x = x; this.y = y; return (B) this; }

		@SuppressWarnings("unchecked")
		public B size(int w, int h) { this.width = w; this.height = h; return (B) this; }

		@SuppressWarnings("unchecked")
		public B visible(State<Boolean> s) { this.visible = s; return (B) this; }

		@SuppressWarnings("unchecked")
		public B tooltip(String text) { this.tooltip = text; return (B) this; }

		protected abstract W create();

		public W build() {
			W w = create();
			w.x = x; w.y = y;
			if (width  > 0) w.width  = width;
			if (height > 0) w.height = height;
			if (visible != null) w.deferVisible(visible);
			if (tooltip != null) w.setTooltip(tooltip);
			return w;
		}
	}

	// ─── Sprite Builder ───────────────────────────────────────────────────────

	public static class SpriteBuilder extends WidgetBuilder<SpriteBuilder, SpriteWidget> {
		private final String texture;
		private final int u, v, w, h, texW, texH;

		SpriteBuilder(String texture, int u, int v, int w, int h, int texW, int texH) {
			this.texture = texture;
			this.u = u; this.v = v; this.texH = texH;
			this.w = w; this.h = h; this.texW = texW;
		}

		protected SpriteWidget create() { return new SpriteWidget(texture, u, v, w, h, texW, texH); }
	}

	// ─── Label Builder ────────────────────────────────────────────────────────

	public static class LabelBuilder extends WidgetBuilder<LabelBuilder, LabelWidget> {
		private final String staticText;
		private final State<String> reactiveText;
		private int color = 0x000000;
		private boolean shadow = false;

		LabelBuilder(String text)        { this.staticText = text;   this.reactiveText = null; }
		LabelBuilder(State<String> text) { this.reactiveText = text; this.staticText = null; }

		public LabelBuilder color(int color) { this.color = color; return this; }
		public LabelBuilder shadow()         { this.shadow = true; return this; }

		protected LabelWidget create() {
			return reactiveText != null
				? new LabelWidget(reactiveText, color, shadow)
				: new LabelWidget(staticText, color, shadow);
		}
	}

	// ─── Button Builder ───────────────────────────────────────────────────────

	public static class ButtonBuilder extends WidgetBuilder<ButtonBuilder, ButtonWidget> {
		private final String label;
		private final Runnable onClick;
		private String texture = null;
		private int u, v, uHover, vHover;
		private int labelColor = 0xFFFFFF;

		ButtonBuilder(String label, Runnable onClick) {
			this.label = label;
			this.onClick = onClick;
		}

		public ButtonBuilder texture(String texture, int u, int v, int uHover, int vHover) {
			this.texture = texture;
			this.u = u; this.v = v;
			this.uHover = uHover; this.vHover = vHover;
			return this;
		}

		public ButtonBuilder labelColor(int color) { this.labelColor = color; return this; }

		protected ButtonWidget create() {
			return new ButtonWidget(texture, u, v, uHover, vHover, width, height, label, labelColor, onClick);
		}
	}

	// ─── Progress Builder ─────────────────────────────────────────────────────

	public static class ProgressBuilder extends WidgetBuilder<ProgressBuilder, ProgressBarWidget> {
		private final String texture;
		private final int u, v, maxW, h;
		private final State<Float> state;

		ProgressBuilder(String texture, int u, int v, int maxW, int h, State<Float> state) {
			this.texture = texture;
			this.u = u; this.v = v;
			this.maxW = maxW; this.h = h;
			this.state = state;
		}

		protected ProgressBarWidget create() {
			return new ProgressBarWidget(texture, u, v, maxW, h, state);
		}
	}

	// ─── Divider Builder ──────────────────────────────────────────────────────

	public static class DividerBuilder extends WidgetBuilder<DividerBuilder, DividerWidget> {
		private final int length;
		private final boolean vertical;
		private int color = 0xFF888888;

		DividerBuilder(int length, boolean vertical) {
			this.length = length; this.vertical = vertical;
		}

		public DividerBuilder color(int color) { this.color = color; return this; }

		protected DividerWidget create() { return new DividerWidget(length, vertical, color); }
	}

	public static NineSlicePanelBuilder panel(String texture, int texW, int texH, int border) {
		return new NineSlicePanelBuilder(texture, texW, texH, border);
	}

	public static NineSliceButtonBuilder nineButton(String texture, int texW, int texH, int border,
	                                                int vNormal, int vHover, int vDisabled,
	                                                String label, Runnable onClick) {
		return new NineSliceButtonBuilder(texture, texW, texH, border,
			vNormal, vHover, vDisabled, label, onClick);
	}

	public static class NineSlicePanelBuilder extends WidgetBuilder<NineSlicePanelBuilder, NineSlicePanelWidget> {
		private final String texture;
		private final int texW, texH, border;

		NineSlicePanelBuilder(String texture, int texW, int texH, int border) {
			this.texture = texture; this.texW = texW;
			this.texH = texH; this.border = border;
		}

		protected NineSlicePanelWidget create() {
			return new NineSlicePanelWidget(texture, texW, texH, border);
		}
	}

	public static class NineSliceButtonBuilder extends WidgetBuilder<NineSliceButtonBuilder, NineSliceButtonWidget> {
		private final String texture;
		private final int texW, texH, border, vNormal, vHover, vDisabled;
		private final String label;
		private final Runnable onClick;
		private int labelColor = 0xFFFFFF;

		NineSliceButtonBuilder(String texture, int texW, int texH, int border,
		                       int vNormal, int vHover, int vDisabled,
		                       String label, Runnable onClick) {
			this.texture = texture; this.texW = texW; this.texH = texH;
			this.border = border; this.vNormal = vNormal;
			this.vHover = vHover; this.vDisabled = vDisabled;
			this.label = label; this.onClick = onClick;
		}

		public NineSliceButtonBuilder labelColor(int color) { this.labelColor = color; return this; }

		protected NineSliceButtonWidget create() {
			return new NineSliceButtonWidget(texture, texW, texH, border,
				vNormal, vHover, vDisabled,
				label, labelColor, onClick);
		}
	}
}
