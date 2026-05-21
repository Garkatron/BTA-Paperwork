package deus.utils.react.interfaces;

import java.util.Optional;

@FunctionalInterface
public interface DeriveFn<T> {
	T run(T arg);
}
