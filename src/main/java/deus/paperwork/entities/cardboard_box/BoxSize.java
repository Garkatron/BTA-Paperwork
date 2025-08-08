package deus.paperwork.entities.cardboard_box;

public enum BoxSize {
	SMALL(0.0064F*9F, 9, 64, 0.0425F, 0.25F),
	REGULAR( 0.0064F*27F, 27,64, 0.0625F, 0.8F),
	MEDIUM(0.0064F*36F, 36,64, 0.0825F, 1.4F),
	LARGE(0.0064F*48F, 48,64, 0.1125F, 2F);

	private final double maxWeight;
	private final int slots;
	private final int stackSize;
	private final float scale;
	private final float size;


	BoxSize(double maxWeight, int slots, int stackSize, float scale, float size) {
		this.maxWeight = maxWeight;
		this.slots = slots;
		this.stackSize = stackSize;
		this.scale = scale;
		this.size = size;

	}

	public double getMaxWeight() {
		return maxWeight;
	}

	public int getSlots() {
		return slots;
	}

	public int getMaxStack() {
		return stackSize;
	}
	public float getScale() {
		return scale;
	}

	public float getSize() {
		return size;
	}
}
