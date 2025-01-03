package io.github.qMartinz.paranormal.block;

import com.mojang.serialization.MapCodec;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.block.entities.CurseTableEntity;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CurseTableWisdom extends CurseTable {
	public CurseTableWisdom() {
		super(ParanormalElement.WISDOM);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBlockRegistry.WISDOM_TABLE_ENTITY, CurseTableEntity::tick);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec((settings) -> new CurseTableWisdom());
	}
}
