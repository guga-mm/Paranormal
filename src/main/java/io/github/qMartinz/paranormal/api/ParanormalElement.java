package io.github.qMartinz.paranormal.api;

import net.minecraft.text.Text;

import java.awt.*;

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

	public int getColor() {
		return switch (this){
			case FEAR -> 0xFFFFFFFF;
			case BLOOD -> 0xFFFF1100;
			case WISDOM -> 0xFFFFCC00;
			case DEATH -> 0xFF0C0C0D;
			case ENERGY -> 0xFF6C25FF;
		};
	}

	public Color particleColorS() {
		return switch (this){
			case FEAR -> new Color(0xFFFFFFFF);
			case BLOOD -> new Color(0xFF0000);
			case WISDOM -> new Color(0xFFFFCC00);
			case DEATH -> new Color(0xFF0C0C0D);
			case ENERGY -> new Color(0xFF00B3FF);
		};
	}

	public Color particleColorE() {
		return switch (this){
			case FEAR -> new Color(0xFFFFFFFF);
			case BLOOD -> new Color(0xFFFF1100);
			case WISDOM -> new Color(0xFFFFCC00);
			case DEATH -> new Color(0xFF0C0C0D);
			case ENERGY -> new Color(0xFFFF00E6);
		};
	}
}
