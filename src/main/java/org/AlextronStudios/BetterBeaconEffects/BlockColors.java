package org.AlextronStudios.BetterBeaconEffects;

public enum BlockColors {
	PURPUR_BLOCK(234F, 119F, 255F, 1F),
	COMMAND_BLOCK(255F, 232F, 119F, 1F),
	QUARTZ_BLOCK(255F, 255F, 255F, 1F),
	GLASS(255F, 255F, 255F, 0.5F),
	OBSIDIAN(127F, 0F, 255F, 1F),
	REDSTONE_BLOCK(255F, 0F, 0F, 1F),
	SLIME_BLOCK(0F, 255F, 0F, 1F),
	OBSERVER(127F, 127F, 127F, 1F),
	SEA_LANTERN(0F, 255F, 255F, 1F),
	PRISMARINE(0F, 255F, 255F, 1F),
	LAPIS_BLOCK(0F, 0F, 255F, 1F),
	COAL_BLOCK(0F, 0F, 0F, 1F),
	END_STONE(255F, 255F, 100F, 1F);
	
	public float red;
	public float green;
	public float blue;
	public float alpha;
	
	BlockColors(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
}