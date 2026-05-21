package deus.utils.define_ui.core;

import deus.utils.define_ui.interfaces.Bind;
import deus.utils.define_ui.interfaces.Sync;
import deus.utils.react.React;
import deus.utils.react.State;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WidgetContext {
    private final Widget owner;
    private final List<Runnable> syncBindings = new ArrayList<>();
    private React react;

    public WidgetContext(Widget owner) {
        this.owner = owner;
    }

	public void mount(React react) {
		this.react = react;
		if (owner.visibleState != null) {
			react.effect(() -> owner.hidden = !owner.visibleState.getVal());
		}
		wire();
		syncBindings.forEach(Runnable::run);
	}



	@SuppressWarnings("unchecked")
	private static <T> void setVal(State<T> state, Object value) {
		state.setVal((T) value);
	}

	private void wire() {
        Class<?> cls = owner.getClass();

        for (Field f : cls.getDeclaredFields()) {
            if (!f.isAnnotationPresent(Sync.class)) continue;
            if (!State.class.isAssignableFrom(f.getType())) continue;
            f.setAccessible(true);

            Sync ann = f.getAnnotation(Sync.class);
            String methodName = ann.method().isEmpty()
                ? "sync" + capitalize(f.getName())
                : ann.method();

            try {
                Method supplier = cls.getDeclaredMethod(methodName);
                supplier.setAccessible(true);

                State<?> state = (State<?>) f.get(owner);
                if (state == null) {
                    state = react.state(null);
                    f.set(owner, state);
                }
                final State<?> s = state;
                react.effect(() -> { s.getVal(); });
				syncBindings.add(() -> {
					try { setVal(s, supplier.invoke(owner)); }
					catch (Exception e) { /* log */ }
				});
            } catch (Exception e) {
                throw new RuntimeException("@Sync wire failed: " + f.getName(), e);
            }
        }

        for (Field f : cls.getDeclaredFields()) {
            if (!f.isAnnotationPresent(Bind.class)) continue;
            if (!State.class.isAssignableFrom(f.getType())) continue;
            f.setAccessible(true);

            for (String dep : f.getAnnotation(Bind.class).value()) {
                try {
                    Field depField = findField(cls, dep);
                    if (depField == null) continue;
                    depField.setAccessible(true);
                    State<?> depState = (State<?>) depField.get(owner);
                    if (depState == null) continue;
                    react.effect(() -> { depState.getVal(); });
                } catch (Exception e) {
                    throw new RuntimeException("@Bind wire failed: " + dep, e);
                }
            }
        }

        owner.setup(react);
    }

    public void registerSync(Runnable r) { syncBindings.add(r); }

    public List<Runnable> getSyncBindings() { return syncBindings; }

    public React getReact() { return react; }

    public <T> State<T> state(T init) { return react.state(init); }

    private Field findField(Class<?> cls, String name) {
        try { return cls.getDeclaredField(name); }
        catch (NoSuchFieldException e) {
            if (cls.getSuperclass() != null) return findField(cls.getSuperclass(), name);
            return null;
        }
    }

    private static String capitalize(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
