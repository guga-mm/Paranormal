package io.github.qMartinz.paranormal.block;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.block.CurseTable;
import io.github.qMartinz.paranormal.block.entities.CurseTableEntity;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CurseTableEnergy extends CurseTable {
	public CurseTableEnergy() {
		super(ParanormalElement.ENERGY);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBlockRegistry.CURSE_TABLE_ENERGY_ENTITY, CurseTableEntity::tick);
	}
}
