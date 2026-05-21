package deus.utils.react;

import deus.paperwork.Paperwork;
import deus.utils.react.interfaces.DeriveFn;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

public final class React {

	public record Dependencies(
		HashSet<State<?>> getters
	) {
		public static Dependencies filled() {
			return new Dependencies(new HashSet<>());
		}

		@Override
		public Dependencies clone() {
			return new Dependencies(new HashSet<>(this.getters()));
		}
	}

	public static class Listener<T> {
		public Dependencies oldDeps;
		public final DeriveFn<T> fn;
		public final State<T> state;

		public Listener(Dependencies oldDeps, DeriveFn<T> fn, State<T> state) {
			this.oldDeps = oldDeps;
			this.fn = fn;
			this.state = state;
		}
	}

	public static class Effect {
		public Dependencies oldDeps;
		public final Runnable fn;

		public Effect(Dependencies oldDeps, Runnable fn) {
			this.oldDeps = oldDeps;
			this.fn = fn;
		}
	}

	private final Deque<Dependencies> execStack = new ArrayDeque<>();

	@SuppressWarnings("unchecked")
	public <T> T runAndCaptureDependencies(
		DeriveFn<T> fn,
		Dependencies deps,
		T arg
	) {
		execStack.push(deps);

		try {
			return fn.run(arg);
		} catch (Exception err) {
			Paperwork.LOGGER.error("[React] Error: ", err);
			return arg;
		} finally {
			execStack.pop();
		}
	}

	public <T> State<T> state(T initVal) {
		return new State<>(this, initVal);
	}


	public Effect effect(Runnable fn) {
		Effect effect = new Effect(Dependencies.filled(), fn);
		Dependencies deps = new Dependencies(new HashSet<>());
		runAndCaptureDependencies(
			ignored -> { effect.fn.run(); return null; },
			deps,
			null
		);
		reconcile(effect, effect.oldDeps, deps);
		return effect;
	}


	public void runEffect(Effect effect, Dependencies deps) {

		runAndCaptureDependencies(
			ignored -> {
				effect.fn.run();
				return null;
			},
			deps,
			null
		);

		reconcile(effect, effect.oldDeps, deps);
	}

	protected void reconcile(
		Effect effect,
		Dependencies oldDeps,
		Dependencies newDeps
	) {
		Dependencies oldD = oldDeps.clone();
		Dependencies newD = newDeps.clone();

		for (State<?> s : oldD.getters()) {
			if (!newD.getters().contains(s)) {
				s.removeEffect(effect);
			}
		}

		for (State<?> s : newD.getters()) {
			if (!oldD.getters().contains(s)) {
				s.addEffect(effect);
			}
		}

		effect.oldDeps = newD;
	}


	public <T> State<T> derive(DeriveFn<T> fn, State<T> state) {

		Dependencies deps = new Dependencies(new HashSet<>());

		Listener<T> listener = new Listener<>(Dependencies.filled(), fn, state);

		T value = this.runAndCaptureDependencies(
			fn,
			deps,
			state.getRawVal()
		);

		reconcile(listener, listener.oldDeps, deps);

		state.setVal(value);

		return state;
	}

	public <T> State<T> derive(DeriveFn<T> fn) {
		return this.derive(fn, state(null));
	}

	protected static void reconcile(
		Listener<?> listener,
		Dependencies oldDeps,
		Dependencies newDeps
	) {
		Dependencies oldD = oldDeps.clone();
		Dependencies newD = newDeps.clone();

		for (State<?> s : oldD.getters()) {
			if (!newD.getters().contains(s)) {
				s.removeListener(listener);
			}
		}

		for (State<?> s : newD.getters()) {
			if (!oldD.getters().contains(s)) {
				s.addListener(listener);
			}
		}

		listener.oldDeps = newD;
	}


	public Dependencies getCurrentDependencies() {
		return execStack.peek();
	}
}
