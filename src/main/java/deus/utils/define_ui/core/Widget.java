package deus.utils.define_ui.core;

import deus.utils.react.React;
import deus.utils.react.State;

import java.util.ArrayList;
import java.util.List;

public abstract class Widget extends WidgetNode {
    private final WidgetContext ctx = new WidgetContext(this);

    public final void mount(React react) {
        ctx.mount(react);
        children.forEach(w -> ((Widget) w).mount(react));
    }

    public List<Runnable> getSyncBindings() {
        List<Runnable> all = new ArrayList<>(ctx.getSyncBindings());
        children.stream()
            .filter(c -> c instanceof Widget)
            .forEach(c -> all.addAll(((Widget) c).getSyncBindings()));
        return all;
    }

    // Subclass hooks
    protected void setup(React react) {}

    protected <T> State<T> state(T init) { return ctx.state(init); }

    protected React getReact() { return ctx.getReact(); }

    protected void registerSync(Runnable r) { ctx.registerSync(r); }

    protected void watchVisible(State<Boolean> s) {
        ctx.getReact().effect(() -> hidden = !s.getVal());
    }
}
