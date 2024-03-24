package io.github.qMartinz.paranormal.block.entities;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.networking.ParticleMessages;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.lodestar.lodestone.systems.rendering.particle.Easing;

import java.awt.*;

public class LightBlockEntity extends BlockEntity {
	public LightBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockRegistry.LIGHT_BLOCK_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, LightBlockEntity be) {
		if (world instanceof ServerWorld serverWorld) {
			for (int i = 0; i < 3; i++) {
				ParticleMessages.spawnAdditiveParticle(serverWorld, ModParticleRegistry.GLOWING_PARTICLE,
						pos.getX() + 0.5d + (world.random.rangeClosed(-15, 15) / 100d),
						pos.getY() + 0.5d + (world.random.rangeClosed(-15, 15) / 100d),
						pos.getZ() + 0.5d + (world.random.rangeClosed(-15, 15) / 100d),
						0d, 0.02d, 0d, ParanormalElement.ENERGY.particleColorS(), ParanormalElement.ENERGY.particleColorE(),
						1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
						0.3f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
			}
			for (int i = 0; i < 2; i++) {
				ParticleMessages.spawnAdditiveParticle(serverWorld, ModParticleRegistry.GLOWING_PARTICLE,
						pos.getX() + 0.5d + (world.random.rangeClosed(-10, 10) / 100d),
						pos.getY() + 0.5d + (world.random.rangeClosed(-10, 10) / 100d),
						pos.getZ() + 0.5d + (world.random.rangeClosed(-10, 10) / 100d),
						0d, 0.02d, 0d, ParanormalElement.ENERGY.complementColorS(), ParanormalElement.ENERGY.complementColorE(),
						1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
						0.3f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
			}
		}
	}
}
