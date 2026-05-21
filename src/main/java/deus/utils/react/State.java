package deus.utils.react;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class State<T> {

	private T rawVal;
	private T oldVal;

	private final React ctx;
	List<React.Effect> effects = new ArrayList<>();

	protected final List<React.Listener<?>> listeners =
		new ArrayList<>();

	public State(React ctx, T initVal) {
		this.ctx = ctx;
		this.rawVal = initVal;
		this.oldVal = initVal;
	}

	public T getVal() {
		React.Dependencies exec = ctx.getCurrentDependencies();

		if (exec != null) {
			exec.getters().add(this);
		}

		return rawVal;
	}

	public T setVal(T value) {
		if (Objects.equals(value, rawVal)) return rawVal;

		rawVal = value;

		for (React.Listener<?> l : new ArrayList<>(listeners)) {
			updateListener(l);
		}

		for (React.Effect e : effects) {
			ctx.runEffect(e, new React.Dependencies(new HashSet<>()));
		}

		return rawVal;
	}

	void removeEffect(React.Effect f) {
		this.effects.remove(f);
	}

	void addEffect(React.Effect f) {
		this.effects.add(f);
	}

	private void updateListener(React.Listener<?> rawListener) {

		React.Listener<T> listener = (React.Listener<T>) rawListener;

		React.Dependencies deps = new React.Dependencies(new HashSet<>());

		T nextValue = ctx.runAndCaptureDependencies(
			listener.fn,
			deps,
			listener.state.getRawVal()
		);

		React.reconcile(listener, listener.oldDeps, deps);

		listener.state.setVal(nextValue);
	}

	public T getOldVal() {
		React.Dependencies exec = ctx.getCurrentDependencies();

		if (exec != null) {
			exec.getters().add(this);
		}

		return oldVal;
	}

	public T getRawVal() {
		return rawVal;
	}

	void addListener(React.Listener<?> listener) {
		listeners.add(listener);
	}

	void removeListener(React.Listener<?> listener) {
		listeners.remove(listener);
	}

	public List<React.Listener<?>> getListeners() {
		return listeners;
	}

	public void commit() {
		oldVal = rawVal;
	}
}
