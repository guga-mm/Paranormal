package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class LightRitual extends AbstractRitual implements ProjectileRitual {
	public LightRitual() {
		super(ParanormalElement.ENERGY, 1, 1, 7.5d, true);
	}

	@Override
	public void onHit(LivingEntity caster, HitResult hitResult) {}

	@Override
	public void onEntityHit(LivingEntity caster, EntityHitResult hitResult) {}

	@Override
	public void onBlockHit(LivingEntity caster, BlockHitResult hitResult) {
		BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide());
		BlockState light = ModBlockRegistry.LIGHT_BLOCK.getDefaultState();

		if (caster.getWorld() instanceof ServerWorld serverWorld){
			// TODO light ritual VFX
		}

		if (caster.getWorld().getBlockState(blockPos).isAir()) caster.getWorld().setBlockState(blockPos, light);
	}
}
