package io.github.gugamm.paranormal.ritual;

import io.github.gugamm.paranormal.api.ParanormalElement;
import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import io.github.gugamm.paranormal.api.rituals.types.RayTracingRitual;
import io.github.gugamm.paranormal.api.rituals.types.SelfRitual;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class HealingRitual extends AbstractRitual implements SelfRitual, RayTracingRitual {
	public HealingRitual() {
		super(ParanormalElement.DEATH, 1, 1f, 4.5d, true);
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
			rayCastEffects(getElement(), getComplement(), caster, target.getEyePos());

			if (target.getWorld() instanceof ServerWorld world) // TODO healing ritual VFX

			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockHit(LivingEntity caster, BlockState state, BlockPos blockPos) {
		if (BoneMealItem.useOnFertilizable(ItemStack.EMPTY, caster.getWorld(), blockPos)) {
			if (!caster.getWorld().isClient) {
				caster.getWorld().syncWorldEvent(1505, blockPos, 0);
				rayCastEffects(getElement(), getComplement(), caster, blockPos.toCenterPos());
			}
			return true;
		}
		return false;
	}
}
