package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.RayTracingRitual;
import io.github.qMartinz.paranormal.api.rituals.types.SelfRitual;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class HealingRitual extends AbstractRitual implements SelfRitual, RayTracingRitual {
	public HealingRitual() {
		super(ParanormalElement.DEATH, 1, 1f, 3d, true);
	}
	@Override
	public boolean useOnSelf(LivingEntity caster) {
		if (caster.isSneaking()){
			caster.heal(5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean onEntityHit(LivingEntity caster, Entity target) {
		if (!caster.isSneaking() && target instanceof LivingEntity livingEntity){
			livingEntity.heal(5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockHit(LivingEntity caster, BlockState state, BlockPos blockPos) {
		return false;
	}
}
