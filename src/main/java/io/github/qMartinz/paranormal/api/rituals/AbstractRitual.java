package io.github.qMartinz.paranormal.api.rituals;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import io.github.qMartinz.paranormal.api.rituals.types.RayTracingRitual;
import io.github.qMartinz.paranormal.api.rituals.types.SelfRitual;
import io.github.qMartinz.paranormal.networking.ParticleMessages;
import io.github.qMartinz.paranormal.particle.GlowingParticle;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.LodestoneWorldParticleTextureSheet;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;

public abstract class AbstractRitual {
	private final ParanormalElement element;
	private final int tier;
	private final float occultPointsCost;
	private final double range;
	private final boolean mustHoldIngredient;

	protected AbstractRitual(ParanormalElement element, int tier, float occultPointsCost, double range, boolean mustHoldIngredient) {
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

	public float getOccultPointsCost() {
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
		if (canCast(caster) && (playerData.getOccultPoints() >= getOccultPointsCost() || (caster instanceof PlayerEntity player && player.isCreative()))){
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
					playerData.syncToClient(player);
				}

				castEffects(caster);
			}
		}
	}

	public void castEffects(LivingEntity caster){
		if (caster.getWorld() instanceof ServerWorld world){
			if (getElement() == ParanormalElement.DEATH){
				ParticleMessages.spawnLumitransparentCircle(world, ModParticleRegistry.GLOWING_PARTICLE, caster.getX(),
						caster.getY(), caster.getZ(), 0.5d, 12, 0d, 0.5d, 0d,
						getElement().particleColorS(), getElement().particleColorE(), 1f, 0f, -1f,
						1f, Easing.LINEAR, Easing.LINEAR, 0.3f, 0f, -1f, 1f,
						Easing.LINEAR, Easing.LINEAR, 36, 0f);
			} else {
				ParticleMessages.spawnAdditiveCircle(world, ModParticleRegistry.GLOWING_PARTICLE, caster.getX(),
						caster.getY(), caster.getZ(), 0.5d, 12, 0d, 0.5d, 0d,
						getElement().particleColorS(), getElement().particleColorE(), 1f, 0f, -1f,
						1f, Easing.LINEAR, Easing.LINEAR, 0.3f, 0f, -1f, 1f,
						Easing.LINEAR, Easing.LINEAR, 36, 0f);
			}
		}
	}
}
