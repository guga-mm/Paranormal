package io.github.gugamm.paranormal.api.rituals;

import io.github.gugamm.paranormal.api.ParanormalElement;
import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.api.events.ParanormalEvents;
import io.github.gugamm.paranormal.api.rituals.types.ProjectileRitual;
import io.github.gugamm.paranormal.api.rituals.types.RayTracingRitual;
import io.github.gugamm.paranormal.api.rituals.types.SelfRitual;
import io.github.gugamm.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public abstract class AbstractRitual {
	private final ParanormalElement element;
	private final ParanormalElement complement;
	private final int tier;
	private final float occultPointsCost;
	private final double range;
	private final boolean mustHoldIngredient;

	protected AbstractRitual(ParanormalElement element, int tier, float occultPointsCost, double range, boolean mustHoldIngredient) {
		this.element = element;
		this.complement = element;
		this.tier = Math.max(1, Math.min(tier, 4));
		this.occultPointsCost = occultPointsCost;
		this.range = range;
		this.mustHoldIngredient = mustHoldIngredient;
	}

	protected AbstractRitual(ParanormalElement element, ParanormalElement complement, int tier, float occultPointsCost, double range, boolean mustHoldIngredient) {
		this.element = element;
		this.complement = complement;
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

	public float getOccultPointsCost() {
		return occultPointsCost;
	}

	public ParanormalElement getElement() {
		return element;
	}

	public ParanormalElement getComplement() {
		return complement;
	}

	public double getRange() {
		return range;
	}

	public int getTier() {
		return tier;
	}

	public Rarity getRarity(){
		return switch (getTier()){
			case 2 -> Rarity.UNCOMMON;
			case 3 -> Rarity.RARE;
			case 4 -> Rarity.EPIC;
			default -> Rarity.COMMON;
		};
	}

	public int getPresenceRequired(){
		return switch (tier) {
			case 2 -> 2;
			case 3 -> 4;
			case 4 -> 6;
			default -> 0;
		};
	}

	public boolean mustHoldIngredient() {
		return mustHoldIngredient;
	}

	public boolean canCast(LivingEntity caster){
		return true;
	}

	public void onCast(LivingEntity caster){
		PlayerData playerData = StateSaverAndLoader.getPlayerState(caster);
		if (canCast(caster) && (playerData.getOccultPoints() >= getOccultPointsCost() || (caster instanceof PlayerEntity player && player.isCreative())) &&
				ParanormalEvents.RITUAL_CAST.invoker().ritualCast(this, caster)){
			boolean cast = false;

			if (this instanceof SelfRitual ritual) cast = ritual.useOnSelf(caster);

			if (this instanceof RayTracingRitual ritual && caster instanceof PlayerEntity) {
				cast = cast || ritual.onHit(getElement(), caster, getRange());
			} else if (this instanceof RayTracingRitual ritual && caster instanceof MobEntity mob && mob.distanceTo(mob.getTarget()) <= getRange()) {
				cast = cast || ritual.onCastByMob(getElement(), mob, mob.getTarget());
			}

			if (this instanceof ProjectileRitual ritual) cast = cast || ritual.onShoot(caster, this);

			if (cast) {
				if (caster instanceof ServerPlayerEntity player && !player.isCreative()) {
					playerData.setOccultPoints(playerData.getOccultPoints() - getOccultPointsCost());
					StateSaverAndLoader.syncOccultPointsToClient(player);
				}

				castEffects(caster);
			}
		}
	}

	public void castEffects(LivingEntity caster){
		if (caster.getWorld() instanceof ServerWorld world){
			// TODO cast VFX
		}
	}
}
