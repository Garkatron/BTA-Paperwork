package deus.utils.define_ui.core;

public class FlexLayout {
    public enum Direction { ROW, COLUMN }

    public static void apply(WidgetNode parent, Direction dir, int gap) {
        int cursor = 0;
        for (WidgetNode child : parent.children) {
            if (dir == Direction.ROW) {
                child.x = cursor;
                cursor += child.width + gap;
            } else {
                child.y = cursor;
                cursor += child.height + gap;
            }
        }
    }
}
