package io.github.qMartinz.paranormal.api;

import net.minecraft.text.Text;

public enum ParanormalElement {
	FEAR("fear", 0),
	BLOOD("blood", 1),
	WISDOM("wisdom", 2),
	DEATH("death", 3),
	ENERGY("energy", 4);
	public final String name;
	public final int index;

	ParanormalElement(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public Text getDisplayName() {
		return Text.translatable("paranormal.element." + name);
	}
}
