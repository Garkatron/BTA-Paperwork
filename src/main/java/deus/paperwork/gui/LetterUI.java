package deus.paperwork.gui;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.gui.Screen;
import net.minecraft.core.item.ItemStack;

import java.util.stream.Collectors;

public class LetterUI extends Screen {

	private TextEditor textEditor = new TextEditor();
	private ItemStack itemStack = null;
	private boolean editable = true;

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

		if (existingText != null && !existingText.isEmpty()) {
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
			if (tag == null) {
				tag = new CompoundTag();
			}

			tag.putString("text", text);
			itemStack.setData(tag);
		});
	}

	@Override
	public void render(int mx, int my, float partialTick) {

		textEditor.x = (width-textEditor.width)/2;
		textEditor.y = (height/2)-160;

		if (editable) {
			textEditor.updateMousePos(mx, my);
			textEditor.update();
		}

		textEditor.render();
	}
}
