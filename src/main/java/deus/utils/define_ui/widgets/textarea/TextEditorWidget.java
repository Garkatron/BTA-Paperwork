package deus.utils.define_ui.widgets.textarea;

import deus.utils.define_ui.core.Widget;
import deus.utils.react.React;
import net.minecraft.client.render.renderer.GLRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class TextEditorWidget extends Widget {

    // --- Config ---
    public int maxUndoHistory = 100;
    public int maxTextLength  = 20;
    public int maxLines       = 22;
    public int minTextOffsetX = 12;
    public boolean autoWrap   = true;

    // --- Display flags ---
    public boolean drawBackground    = true;
    public boolean drawBorder        = true;
    public boolean drawLineCharCount = true;
    public boolean drawLineCount     = true;
    public boolean drawExtraCursors  = true;
    public boolean animateCursor     = true;
    public int cursorBlinkInterval   = 500;
	public float scale = 0.5f;

    // --- Colors ---
    public int focusBackgroundColor = 0xFF000000;
    public int focusTextColor       = 0xFFE9C46A;
    public int focusBorderColor     = 0xFFE9C46A;
    public int backgroundColor      = 0xFF000000;
    public int textColor            = 0xFFFFFFFF;
    public int borderColor          = 0xFFFFFFFF;
    public int cursorColor          = 0xFFFFFFFF;
    public int lineCountColor       = 0xb2b3b3;

    // --- Cursor chars ---
    public String cursorCharacter     = "_";
    public String charCursorCharacter = ".";
    public String lineCursorCharacter = "|";

    // --- State ---
    protected List<Character> characters = new ArrayList<>();
    protected final List<List<Character>> undoStack = new ArrayList<>();
    protected final List<List<Character>> redoStack = new ArrayList<>();
	private boolean wasMouseDown = false;

    protected int currentCharPos = 0;

    public int cursorX = 0;
    public int cursorY = 0;

    // selection
    protected boolean isSelecting = false;
    protected int selectStart = 0;
    protected int selectEnd   = 0;

    protected boolean ctrl;
    protected boolean shift;

    private boolean focused = false;

    public boolean drawCursor = true;
    private long lastCursorToggle;

    private final TextEditorRenderer renderer;

    public TextEditorWidget() {
        renderer = new TextEditorRenderer(this);
    }

    @Override
    protected void setup(React react) {
	}


	@Override
	public void draw(int x, int y) {

		GLRenderer.pushFrame();

		GLRenderer.modelM4f()
			.translate(x, y, 0)
			.scale(scale);

		renderer.render(0, 0);

		GLRenderer.popFrame();
	}
    // --- Tick ---

    public void tick() {
        ctrl  = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
        shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        if (!focused) return;

        if (animateCursor) {
            long now = System.currentTimeMillis();
            if (now - lastCursorToggle > cursorBlinkInterval) {
                drawCursor = !drawCursor;
                lastCursorToggle = now;
            }
        }

    }

    // --- Mouse ---

	public void updateMousePos(int mx, int my) {
		boolean hover = isHovered(mx, my);
		boolean down = Mouse.isButtonDown(0);

		if (down && !wasMouseDown) {
			if (hover) {
				if (!focused) {
					focused = true;
					Keyboard.enableRepeatEvents(true);
				}
			} else {
				if (focused) {
					focused = false;
					Keyboard.enableRepeatEvents(false);
				}
			}
		}

		// Update the flag for the next frame
		wasMouseDown = down;
	}

	private boolean isHovered(int mx, int my) {
		return mx >= x && my >= y && mx < x + (width * scale) && my < y + (height * scale);
	}

    // --- Keyboard ---

	public boolean onKeyTyped(char c, int key) {
		if (!focused) return false;

		ctrl  = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

		if (ctrl && key == Keyboard.KEY_Z) { undo(); return true; }
		if (ctrl && key == Keyboard.KEY_Y) { redo(); return true; }

		if (key == Keyboard.KEY_BACK) { deleteChar(); return true; }
		if (key == Keyboard.KEY_RETURN) { insertChar('\n'); return true; }

		if (key == Keyboard.KEY_LEFT) {
			if (currentCharPos > 0) currentCharPos--;
			return true;
		}
		if (key == Keyboard.KEY_RIGHT) {
			if (currentCharPos < characters.size()) currentCharPos++;
			return true;
		}

		if (Character.isDefined(c) && !Character.isISOControl(c)) {
			insertChar(c);
			return true;
		}

		return false;
	}

    // --- Edit ops ---

    private void insertChar(char c) {
        pushUndo();
        characters.add(currentCharPos, c);
        currentCharPos = clampCursor(currentCharPos + 1);
    }

    private void deleteChar() {
        if (currentCharPos <= 0 || characters.isEmpty()) return;
        pushUndo();
        characters.remove(currentCharPos - 1);
        currentCharPos = clampCursor(currentCharPos - 1);
    }

    private int clampCursor(int v) {
        return Math.max(0, Math.min(v, characters.size()));
    }

    // --- Undo/redo ---

    private void pushUndo() {
        undoStack.add(new ArrayList<>(characters));
        if (undoStack.size() > maxUndoHistory) undoStack.remove(0);
        redoStack.clear();
    }

    public void undo() {
        if (undoStack.isEmpty()) return;
        redoStack.add(new ArrayList<>(characters));
        characters = undoStack.remove(undoStack.size() - 1);
        currentCharPos = clampCursor(currentCharPos);
    }

    public void redo() {
        if (redoStack.isEmpty()) return;
        undoStack.add(new ArrayList<>(characters));
        characters = redoStack.remove(redoStack.size() - 1);
        currentCharPos = clampCursor(currentCharPos);
    }

    // --- API ---

    public boolean isFocused() {
        return focused;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public String getContentAsString() {
        StringBuilder sb = new StringBuilder(characters.size());
        for (char c : characters) sb.append(c);
        return sb.toString();
    }

    public void setContent(List<Character> chars) {
        this.characters = new ArrayList<>(chars);
        currentCharPos = clampCursor(currentCharPos);
    }
}
