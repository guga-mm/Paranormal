package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import io.github.qMartinz.paranormal.api.rituals.types.RayTracingRitual;
import io.github.qMartinz.paranormal.api.rituals.types.SelfRitual;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class SkinningRitual extends AbstractRitual implements ProjectileRitual {
	public SkinningRitual() {
		super(ParanormalElement.DEATH, 1, 1, 7.5d, true);
	}

	@Override
	public void onHit(LivingEntity caster, HitResult hitResult) {}

	@Override
	public void onEntityHit(LivingEntity caster, EntityHitResult hitResult) {
		if (hitResult.getEntity() instanceof LivingEntity livingEntity){
			livingEntity.damage(new DamageSources(livingEntity.getServer().getRegistryManager()).generic(), 4f);
		}
	}

	@Override
	public void onBlockHit(LivingEntity caster, BlockHitResult hitResult) {}
}
