package io.github.qMartinz.paranormal.api.rituals.types;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public interface RayTracingRitual {
	boolean onHit(LivingEntity caster, HitResult hitResult);
	boolean onEntityHit(LivingEntity caster, EntityHitResult hitResult);
	boolean onBlockHit(LivingEntity caster, BlockHitResult hitResult);
}
