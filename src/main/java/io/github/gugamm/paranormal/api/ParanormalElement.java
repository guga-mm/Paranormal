package io.github.gugamm.paranormal.api;

import io.github.gugamm.paranormal.registry.ModDamageTypes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;

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
			case ENERGY -> new Color(0xFFFF00E6);
		};
	}

	public Color particleColorE() {
		return switch (this){
			case FEAR -> new Color(0xFFFFFFFF);
			case BLOOD -> new Color(0xFFFF1100);
			case WISDOM -> new Color(0xFFFFCC00);
			case DEATH -> new Color(0xFF0C0C0D);
			case ENERGY -> new Color(0xFF00B3FF);
		};
	}

	public Color complementColorS() {
		return switch (this){
			case FEAR -> new Color(0xD6E2FF);
			case BLOOD -> new Color(0xFF513B);
			case WISDOM -> new Color(0xFFDC4B);
			case DEATH -> new Color(0x2D2D3F);
			case ENERGY -> new Color(0xC163FF);
		};
	}

	public Color complementColorE() {
		return switch (this){
			case FEAR -> new Color(0xD6E2FF);
			case BLOOD -> new Color(0xFF513B);
			case WISDOM -> new Color(0xFFDC4B);
			case DEATH -> new Color(0x2D2D3F);
			case ENERGY -> new Color(0x8BFFA6);
		};
	}

	public boolean isCompatible(ParanormalElement other){
		boolean result = true;

		if (other.equals(this)) return true;

		switch (other) {
			case BLOOD -> {
				if (this.equals(DEATH) || this.equals(WISDOM)) result = false;
			}
			case DEATH -> {
				if (this.equals(ENERGY) || this.equals(BLOOD)) result = false;
			}
			case WISDOM -> {
				if (this.equals(BLOOD) || this.equals(ENERGY)) result = false;
			}
			case ENERGY -> {
				if (this.equals(WISDOM) || this.equals(DEATH)) result = false;
			}
		}

		return result;
	}

	public ParanormalElement getOpressingElement() {
		return switch (this){
			case FEAR -> null;
			case BLOOD -> DEATH;
			case WISDOM -> BLOOD;
			case DEATH -> ENERGY;
			case ENERGY -> WISDOM;
		};
	}

	public DamageSource getDamage(World world) {
		return switch (this){
			case FEAR -> ModDamageTypes.getDamageSource(ModDamageTypes.FEAR_DAMAGE, world);
			case BLOOD -> ModDamageTypes.getDamageSource(ModDamageTypes.BLOOD_DAMAGE, world);
			case WISDOM -> ModDamageTypes.getDamageSource(ModDamageTypes.WISDOM_DAMAGE, world);
			case DEATH -> ModDamageTypes.getDamageSource(ModDamageTypes.DEATH_DAMAGE, world);
			case ENERGY -> ModDamageTypes.getDamageSource(ModDamageTypes.ENERGY_DAMAGE, world);
		};
	}
}
