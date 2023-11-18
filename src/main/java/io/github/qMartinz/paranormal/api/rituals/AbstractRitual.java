package io.github.qMartinz.paranormal.api.rituals;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.awt.*;

public abstract class AbstractRitual {
	private final ParanormalElement element;
	private final int tier;
	private final int effortCost;
	private final double range;
	private final boolean mustHoldIngredient;

	protected AbstractRitual(ParanormalElement element, int tier, int effortCost, double range, boolean mustHoldIngredient) {
		this.element = element;
		this.tier = Math.max(1, Math.min(tier, 4));
		this.effortCost = effortCost;
		this.range = range;
		this.mustHoldIngredient = mustHoldIngredient;
	}

	public Identifier getId() {
		return RitualRegistry.getId(this);
	}

	public String getTranslationKey(){
		return getId().getNamespace() + ".ritual." + getId().getPath();
	}

    public Text getDescription() {
		return Text.translatable(this.getTranslationKey() + ".description").formatted(Formatting.WHITE);
	}

	public Text getDisplayName() {
		return Text.translatable(getTranslationKey());
	}

	public int getEffortCost() {
		return effortCost;
	}

	public ParanormalElement getElement() {
		return element;
	}

	public double getRange() {
		return range;
	}

	public int getTier() {
		return tier;
	}

	public boolean mustHoldIngredient() {
		return mustHoldIngredient;
	}
}
