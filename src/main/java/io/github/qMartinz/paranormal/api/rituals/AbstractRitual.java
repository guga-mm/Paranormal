package io.github.qMartinz.paranormal.api.rituals;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import io.github.qMartinz.paranormal.api.rituals.types.RayTracingRitual;
import io.github.qMartinz.paranormal.api.rituals.types.SelfRitual;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public abstract class AbstractRitual {
	private final ParanormalElement element;
	private final int tier;
	private final int occultPointsCost;
	private final double range;
	private final boolean mustHoldIngredient;

	protected AbstractRitual(ParanormalElement element, int tier, int occultPointsCost, double range, boolean mustHoldIngredient) {
		this.element = element;
		this.tier = Math.max(1, Math.min(tier, 4));
		this.occultPointsCost = occultPointsCost;
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

	public int getOccultPointsCost() {
		return occultPointsCost;
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

	public boolean canCast(LivingEntity caster){
		return true;
	}

	public void onCast(LivingEntity caster){
		PlayerData playerData = StateSaverAndLoader.getPlayerState(caster);
		if (canCast(caster) && playerData.getOccultPoints() >= getOccultPointsCost()){
			boolean cast = false;

			if (this instanceof SelfRitual ritual) cast = ritual.useOnSelf(caster);

			if (this instanceof RayTracingRitual ritual) {
				cast = cast || ritual.onHit(caster, caster.raycast(getRange(), 0, false));
			}

			if (this instanceof ProjectileRitual ritual) cast = cast || ritual.onShoot(caster, this);

			if (cast) {
				if (caster instanceof ServerPlayerEntity player) {
					playerData.setOccultPoints(playerData.getOccultPoints() - getOccultPointsCost());
					playerData.syncToClient(player);
				}

				// cast effects
			}
		}
	}
}
