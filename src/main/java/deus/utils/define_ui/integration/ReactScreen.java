package deus.utils.define_ui.integration;

import deus.utils.define_ui.core.Widget;
import deus.utils.react.React;
import deus.utils.react.State;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.client.gui.container.ScreenContainerAbstract;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ReactScreen extends ScreenContainerAbstract {
	protected final React react = new React();
	private final List<Runnable> syncs = new ArrayList<>();
	private Widget root;
	private int mouseX, mouseY = 0;

	public ReactScreen(MenuAbstract menu) {
		super(menu);

	}

	protected abstract Widget build();

	// Screen-level state synced from world each tick
	protected <T> State<T> sync(T init, Supplier<T> source) {
		State<T> s = react.state(init);
		syncs.add(() -> s.setVal(source.get()));
		return s;
	}

	@Override
	public void init() {
		super.init();
		syncs.clear();
		root = build();
		root.mount(react);
		syncs.addAll(root.getSyncBindings()); // collect @Sync from whole tree
	}

	@Override
	public void tick() {
		super.tick();
		syncs.forEach(Runnable::run);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float pt) {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		root.render(x, y);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		// override in subclass if needed
	}

	@Override
	public void render(int mx, int my, float partialTick) {
		super.render(mx, my, partialTick);
		this.mouseX = mx;
		this.mouseY = my;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getMouseX() {
		return mouseX;
	}
}
