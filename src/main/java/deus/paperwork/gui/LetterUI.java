package deus.paperwork.gui;

import com.mojang.nbt.tags.CompoundTag;
import deus.paperwork.Paperwork;
import net.minecraft.client.gui.Screen;
import net.minecraft.core.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LetterUI extends Screen {

	private TextEditor textEditor = new TextEditor();
	private ItemStack itemStack = null;
	private boolean editable = true;
	private final TextEditor xInput = coordsInput();
	private final TextEditor yInput = coordsInput();
	private final TextEditor zInput = coordsInput();
	private static final String isNumericRegex = "^-?[0-9]*$";
	private static final Pattern isNumericPattern = Pattern.compile(isNumericRegex);


	private static TextEditor coordsInput() {
		TextEditor textInput = new TextEditor();

		textInput.x = 0;
		textInput.y = 0;
		textInput.width = 50;
		textInput.height = 15;
		textInput.maxLines = 1;

		textInput.backgroundColor = 0xFFFFFFFF;
		textInput.focusBackgroundColor = 0xFFFFFFFF;

		textInput.textColor = 0xFF000000;
		textInput.focusTextColor = 0xFF000000;
		textInput.borderColor = 0xFF444444;
		textInput.focusBorderColor = 0xFF1E90FF;
		textInput.drawExtraCursors = false;
		textInput.drawLineCharCount = false;
		textInput.drawLineCount = false;
		textInput.cursorColor = 0xFF000000;


		List<Character> characters = new ArrayList<>();
		characters.add('0');
		textInput.characters = characters;
		textInput.currentCharPos = 1;

		textInput.$onLostFocus.connect((s, c) -> {
			String str = c.stream().map(String::valueOf).collect(Collectors.joining());
			if (!str.isEmpty() && !isNumericPattern.matcher(str).matches()) {
				List<Character> characters2 = new ArrayList<>();
				characters2.add('0');
				textInput.characters = new ArrayList<>(characters2);
				textInput.currentCharPos = characters2.size();
			}
		});
		return textInput;
	}

	public LetterUI(ItemStack itemStack) {
		this.itemStack = itemStack;

		textEditor.x = 0;
		textEditor.y = 0;
		textEditor.width = 200;
		textEditor.height = 300;

		textEditor.drawBackground = true;

		textEditor.backgroundColor = 0xFFFFFFFF;
		textEditor.focusBackgroundColor = 0xFFFFFFFF;

		textEditor.textColor = 0xFF000000;
		textEditor.focusTextColor = 0xFF000000;

		textEditor.borderColor = 0xFF444444;
		textEditor.focusBorderColor = 0xFF1E90FF;

		textEditor.cursorColor = 0xFF000000;

		textEditor.autoWrap = false;
		textEditor.maxTextLength = 32;
		textEditor.maxLines = 31;

		String existingText = "";
		if (itemStack.getData() != null) {
			existingText = itemStack.getData().getString("text");
		}
		if (existingText != null && !existingText.isEmpty()) {
			textEditor.characters = existingText.chars()
				.mapToObj(ch -> (char) ch)
				.collect(Collectors.toList());
		}
		if (itemStack.getData().containsKey("tox")) {
			String toxValue = String.valueOf(itemStack.getData().getInteger("tox"));
			xInput.characters = toxValue.chars()
				.mapToObj(ch -> (char) ch)
				.collect(Collectors.toList());
		}
		if (itemStack.getData().containsKey("toy")) {
			String toxValue = String.valueOf(itemStack.getData().getInteger("toy"));
			yInput.characters = toxValue.chars()
				.mapToObj(ch -> (char) ch)
				.collect(Collectors.toList());
		}
		if (itemStack.getData().containsKey("toz")) {
			String toxValue = String.valueOf(itemStack.getData().getInteger("toz"));
			zInput.characters = toxValue.chars()
				.mapToObj(ch -> (char) ch)
				.collect(Collectors.toList());
		}

		if (itemStack.getData().containsKey("editable") && !itemStack.getData().getBoolean("editable")) {
			editable = false;
			textEditor.drawCursor = false;
			textEditor.drawExtraCursors = false;
			textEditor.drawLineCharCount = false;
			textEditor.drawLineCount = false;
		}

		textEditor.$onLostFocus.connect((s, characters) -> {
			StringBuilder sb = new StringBuilder();
			for (Character c : characters) {
				sb.append(c);
			}
			String text = sb.toString();

			CompoundTag tag = itemStack.getData();

			tag.putString("text", text);
			itemStack.setData(tag);
		});

		zInput.$onLostFocus.connect((s, c)->{

			String str = c.stream().map(String::valueOf).collect(Collectors.joining());
			if (!str.isEmpty() && isNumericPattern.matcher(str).matches()) {
				CompoundTag tag = itemStack.getData();

				tag.putInt("toz", Integer.parseInt(str));
				itemStack.setData(tag);
			}
		});
		xInput.$onLostFocus.connect((s, c)->{

			String str = c.stream().map(String::valueOf).collect(Collectors.joining());
			if (!str.isEmpty() && isNumericPattern.matcher(str).matches()) {
				CompoundTag tag = itemStack.getData();

				tag.putInt("tox", Integer.parseInt(str));
				itemStack.setData(tag);
			}
		});
		yInput.$onLostFocus.connect((s, c)->{

			String str = c.stream().map(String::valueOf).collect(Collectors.joining());
			if (!str.isEmpty() && isNumericPattern.matcher(str).matches()) {
				CompoundTag tag = itemStack.getData();

				tag.putInt("toy", Integer.parseInt(str));
				itemStack.setData(tag);
			}
		});
	}

	@Override
	public void render(int mx, int my, float partialTick) {
		// Center the main text editor
		textEditor.x = (width - textEditor.width) / 2;
		textEditor.y = (height / 2) - 160;

		// Position input fields and labels in a column to the right of textEditor
		int inputWidth = xInput.width; // All inputs have the same width (50)
		int inputHeight = xInput.height; // All inputs have the same height (15)
		int labelHeight = this.mc.font.getFont().fontHeight(); // Height of the font (approx. 9 pixels)
		int gap = 5; // Vertical gap between label-input pairs
		int labelOffsetY = 2; // Vertical offset between label and input
		int rightEdge = textEditor.x + textEditor.width + 3; // Directly adjacent to textEditor’s right edge

		// Set x positions for all inputs (same column)
		xInput.x = rightEdge;
		yInput.x = rightEdge;
		zInput.x = rightEdge;

		// Set y positions for labels and inputs in a column
		xInput.y = textEditor.y; // Align top of xInput with textEditor
		yInput.y = xInput.y + labelHeight + labelOffsetY + inputHeight + gap; // Below xInput pair
		zInput.y = yInput.y + labelHeight + labelOffsetY + inputHeight + gap; // Below yInput pair

		if (editable) {
			textEditor.updateMousePos(mx, my);
			textEditor.update();

		}

		xInput.updateMousePos(mx, my);
		yInput.updateMousePos(mx, my);
		zInput.updateMousePos(mx, my);
		xInput.update();
		yInput.update();
		zInput.update();

		// Draw labels and inputs in column order
		int labelColor = 0xFF000000; // Black color for labels
		this.drawStringNoShadow(this.mc.font, "X:", xInput.x + 13, xInput.y - labelHeight - labelOffsetY, labelColor);
		xInput.render();

		this.drawStringNoShadow(this.mc.font, "Y:", yInput.x + 13, yInput.y - labelHeight - labelOffsetY, labelColor);
		yInput.render();

		this.drawStringNoShadow(this.mc.font, "Z:", zInput.x + 13, zInput.y - labelHeight - labelOffsetY, labelColor);
		zInput.render();

		textEditor.render();
	}
}
