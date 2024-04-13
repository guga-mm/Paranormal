package io.github.qMartinz.paranormal.api.powers;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParanormalPower {
	private final ParanormalElement element;
	private final boolean isActive;
	private final int occultPointsCost;
	private final int pexRequired;
	private final int[] attributesRequired;
	private final Set<ParanormalPower> powerRequirements;

	protected ParanormalPower(ParanormalElement element, boolean isActive, int occultPointsCost, int pexRequired, int[] attributesRequired, ParanormalPower... powerRequirements) {
		this.element = element;
		this.isActive = isActive;
		this.occultPointsCost = occultPointsCost;
		this.pexRequired = pexRequired;
		this.attributesRequired = attributesRequired;
		this.powerRequirements = new HashSet<>(List.of(powerRequirements));
	}

	public Identifier getId() {
		return PowerRegistry.getId(this);
	}

	public ParanormalElement getElement() {
		return element;
	}

	public boolean isActive() {
		return isActive;
	}

	public int getOccultPointsCost() {
		return occultPointsCost;
	}

	public int getPexRequired() {
		return pexRequired;
	}

	/**
	 * <p>0 = str
	 * <p>1 = vig
	 * <p>2 = pre
	 */
	public int[] getAttributesRequired() {
		return attributesRequired;
	}

	/**
	 * <p>0 = str
	 * <p>1 = vig
	 * <p>2 = pre
	 */
	public int getAttributeRequired(int index) {
		return attributesRequired[index];
	}

	public Collection<ParanormalPower> getPowerRequirements() {
		return powerRequirements;
	}

	public String getTranslationKey(){
		return getId().getNamespace() + ".power." + getId().getPath();
	}

	public Text getDisplayName(){ return Text.translatable(getTranslationKey()); }

	public MutableText getDescription(){
		return Text.translatable(this.getTranslationKey() + ".description").formatted(Formatting.WHITE);
	}

	/**
	 * Utilizado para checar se o jogador pode colocar este poder em um slot de poder ativo.
	 *
	 * @param player o jogador que equipou o poder
	 */
	public boolean canEquip(PlayerEntity player){
		return true;
	}
}
