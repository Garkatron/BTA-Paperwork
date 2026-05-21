package deus.utils.define_ui.widgets.textarea;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.font.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class TextEditorRenderer {

	private final TextEditorWidget w;

	public TextEditorRenderer(TextEditorWidget w) {
		this.w = w;
	}

	public void render(int x, int y) {
		if (w.drawBackground) drawBackground(x, y);
		if (w.drawBorder) drawBorder(x, y);

		drawText(x, y);

		if (w.drawCursor) drawCursor(x, y);
		if (w.drawLineCharCount) drawLineCharCount(x, y);
		if (w.drawExtraCursors) drawAlternativeCursors(x, y);
	}

	private void drawBackground(int x, int y) {
		int color = w.isFocused() ? w.focusBackgroundColor : w.backgroundColor;
		w.drawRect(x, y, x + w.width, y + w.height, color);
	}

	private void drawBorder(int x, int y) {
		int color = w.isFocused() ? w.focusBorderColor : w.borderColor;

		w.drawRect(x, y - 1, x + w.width, y, color);
		w.drawRect(x, y + w.height, x + w.width, y + w.height + 1, color);
		w.drawRect(x - 1, y, x, y + w.height, color);
		w.drawRect(x + w.width, y, x + w.width + 1, y + w.height, color);
	}

	private void drawText(int x, int y) {
		FontRenderer font = Minecraft.getMinecraft().font;
		int textColor = w.isFocused() ? w.focusTextColor : w.textColor;

		int lineHeight = font.getFont().fontHeight();

		List<Character> chars = w.characters;

		StringBuilder line = new StringBuilder();
		int drawY = y + 4;

		int pixelX = 0;
		int lineCharCount = 0;
		int lineIndex = 0;

		w.cursorX = 0;
		w.cursorY = 4;

		for (int i = 0; i < chars.size(); i++) {
			char c = chars.get(i);

			if (i == w.currentCharPos) {
				w.cursorX = pixelX;
				w.cursorY = 4 + lineIndex * lineHeight;
			}

			boolean wrap = c == '\n' || lineCharCount >= w.maxTextLength;

			if (wrap) {
				w.drawStringNoShadow(font, line.toString(), x, drawY, textColor);

				drawY += lineHeight;
				line.setLength(0);

				lineIndex++;
				lineCharCount = 0;
				pixelX = 0;

				if (c != '\n') {
					line.append(c);
					pixelX += font.stringWidth(String.valueOf(c));
					lineCharCount++;
				}

			} else {
				line.append(c);
				pixelX += font.stringWidth(String.valueOf(c));
				lineCharCount++;
			}
		}

		if (w.currentCharPos == chars.size()) {
			w.cursorX = pixelX;
			w.cursorY = 4 + lineIndex * lineHeight;
		}

		if (line.length() > 0) {
			w.drawStringNoShadow(font, line.toString(), x, drawY, textColor);
		}

		// --- LINE COUNT RENDER ---
		if (w.drawLineCount) {
			int yOffset = y + 4;

			for (int i = 0; i <= lineIndex; i++) {
				w.drawStringNoShadow(
					font,
					String.valueOf(i + 1),
					x - 10,
					yOffset + i * lineHeight,
					w.lineCountColor
				);
			}
		}
	}

	private void drawCursor(int x, int y) {
		FontRenderer font = Minecraft.getMinecraft().font;

		w.drawStringNoShadow(
			font,
			w.cursorCharacter,
			x + w.cursorX,
			y + w.cursorY,
			w.cursorColor
		);
	}

	private void drawAlternativeCursors(int x, int y) {
		FontRenderer font = Minecraft.getMinecraft().font;

		w.drawStringNoShadow(
			font,
			w.lineCursorCharacter,
			x + w.width - 1,
			y + w.cursorY,
			0xff0000
		);

		w.drawStringNoShadow(
			font,
			w.charCursorCharacter,
			x + w.cursorX,
			y - 7,
			0xff0000
		);
	}

	private void drawLineCharCount(int x, int y) {
		FontRenderer font = Minecraft.getMinecraft().font;

		w.drawStringNoShadow(
			font,
			String.valueOf(w.currentCharPos),
			x + w.cursorX,
			y + w.cursorY + font.getFont().fontHeight(),
			w.lineCountColor
		);
	}

	public static List<String> getLines(List<Character> characters, int maxTextLength, boolean maxLengthSeparator) {
		List<String> lines = new ArrayList<>();
		StringBuilder buf = new StringBuilder();
		int count = 0;
		boolean bang = false;

		for (char c : characters) {
			if (buf.length() == 0 && c == '!') bang = true;

			if (c == '\n') {
				lines.add(buf.toString());
				buf.setLength(0);
				count = 0;
				bang = false;
				continue;
			}

			buf.append(c);
			count++;

			if (maxLengthSeparator && count >= maxTextLength && !bang) {
				lines.add(buf.toString());
				buf.setLength(0);
				count = 0;
			}
		}

		if (buf.length() > 0) lines.add(buf.toString());

		return lines;
	}
}
