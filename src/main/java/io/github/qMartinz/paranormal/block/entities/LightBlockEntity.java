package io.github.qMartinz.paranormal.block.entities;

import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightBlockEntity extends BlockEntity {
	public LightBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockRegistry.LIGHT_BLOCK_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, LightBlockEntity be) {
		if (world instanceof ServerWorld serverWorld) {
			// TODO light block VFX
		}
	}
}
