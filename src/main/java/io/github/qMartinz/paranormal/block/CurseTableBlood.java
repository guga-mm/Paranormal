package io.github.qMartinz.paranormal.block;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.block.entities.CurseTableEntity;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CurseTableBlood extends CurseTable{
	public CurseTableBlood() {
		super(ParanormalElement.BLOOD);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBlockRegistry.BLOOD_TABLE_ENTITY, CurseTableEntity::tick);
	}
}
