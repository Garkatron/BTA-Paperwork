package deus.paperwork.ai;

import deus.brainless.ai.AI;

public record AIHolder<T>(AI<T> ai) {
    @SuppressWarnings("unchecked")
    public <U> AI<U> as() {
        return (AI<U>) ai;
    }
}
