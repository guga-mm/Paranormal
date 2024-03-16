package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
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
			livingEntity.damage(new DamageSources(livingEntity.getWorld().getRegistryManager()).generic(), 4f);
			Paranormal.LOGGER.info("Hit!");
		}
	}

	@Override
	public void onBlockHit(LivingEntity caster, BlockHitResult hitResult) {}
}
