package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import io.github.qMartinz.paranormal.registry.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class SkinningRitual extends AbstractRitual implements ProjectileRitual {
	public SkinningRitual() {
		super(ParanormalElement.BLOOD, 1, 1f, 7.5d, true);
	}

	@Override
	public void onHit(LivingEntity caster, HitResult hitResult) {}

	@Override
	public void onEntityHit(LivingEntity caster, EntityHitResult hitResult) {
		if (hitResult.getEntity() instanceof LivingEntity livingEntity){
			livingEntity.damage(getElement().getDamage(caster.getWorld()), 4f);
		}
	}

	@Override
	public void onBlockHit(LivingEntity caster, BlockHitResult hitResult) {}
}
