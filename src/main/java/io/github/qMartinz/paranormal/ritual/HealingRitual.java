package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.RayTracingRitual;
import io.github.qMartinz.paranormal.api.rituals.types.SelfRitual;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class HealingRitual extends AbstractRitual implements SelfRitual, RayTracingRitual {
	public HealingRitual() {
		super(ParanormalElement.DEATH, 1, 1, 3d, true);
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
	public boolean onHit(LivingEntity caster, HitResult hitResult) {
		if (hitResult instanceof EntityHitResult entityHitResult){
			return onEntityHit(caster, entityHitResult);
		}
		return false;
	}

	@Override
	public boolean onEntityHit(LivingEntity caster, EntityHitResult hitResult) {
		if (!caster.isSneaking() && hitResult.getEntity() instanceof LivingEntity target){
			target.heal(5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockHit(LivingEntity caster, BlockHitResult hitResult) {
		return false;
	}
}
