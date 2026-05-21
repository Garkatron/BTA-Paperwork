package deus.utils.define_ui.core;

import deus.utils.react.State;

public class PlainState<T> extends State<T> {
    public PlainState(T val) { super(null, val); }
}
