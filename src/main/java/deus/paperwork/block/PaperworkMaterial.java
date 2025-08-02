package deus.paperwork.block;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.MaterialColor;

public class PaperworkMaterial extends Material {

	public static Material paper;

	public PaperworkMaterial(MaterialColor color) {
		super(color);
	}

	static {
		paper = new Material(MaterialColor.quartz);
		paper.isFlammable();
	}
}
