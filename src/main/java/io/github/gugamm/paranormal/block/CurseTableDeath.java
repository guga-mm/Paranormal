package io.github.gugamm.paranormal.block;

import com.mojang.serialization.MapCodec;
import io.github.gugamm.paranormal.api.ParanormalElement;
import io.github.gugamm.paranormal.block.entities.CurseTableEntity;
import io.github.gugamm.paranormal.registry.ModBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CurseTableDeath extends CurseTable {
	public CurseTableDeath() {
		super(ParanormalElement.DEATH);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, ModBlockRegistry.DEATH_TABLE_ENTITY, CurseTableEntity::tick);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec((settings) -> new CurseTableDeath());
	}
}
