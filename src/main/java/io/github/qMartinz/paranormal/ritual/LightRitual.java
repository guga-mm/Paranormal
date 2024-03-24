package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import io.github.qMartinz.paranormal.networking.ParticleMessages;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import team.lodestar.lodestone.systems.rendering.particle.Easing;

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
			for (int i = 0; i < 10; i++){
				ParticleMessages.spawnAdditiveParticle(serverWorld, ModParticleRegistry.GLOWING_PARTICLE,
						blockPos.getX() + 0.5d + (serverWorld.random.rangeClosed(-20, 20) / 100d),
						blockPos.getY() + 0.5d + (serverWorld.random.rangeClosed(-20, 20) / 100d),
						blockPos.getZ() + 0.5d + (serverWorld.random.rangeClosed(-20, 20) / 100d),
						0d, 0.04d, 0d, ParanormalElement.ENERGY.particleColorS(), ParanormalElement.ENERGY.particleColorE(),
						1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
						0.3f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
			}
		}

		if (caster.getWorld().getBlockState(blockPos).isAir()) caster.getWorld().setBlockState(blockPos, light);
	}
}
