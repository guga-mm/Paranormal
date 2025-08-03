package io.github.gugamm.paranormal.registry;

import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.api.ParanormalElement;
import io.github.gugamm.paranormal.block.*;
import io.github.gugamm.paranormal.block.entities.CurseTableEntity;
import io.github.gugamm.paranormal.block.entities.LightBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ModBlockRegistry {
	public static final Block TRANSCENDANCE_ALTAR = Registry.register(Registries.BLOCK,
			Identifier.of(MODID, "transcendance_altar"),
			new TranscendanceAltar(AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE)));

	public static final Block LIGHT_BLOCK = Registry.register(Registries.BLOCK,
			Identifier.of(MODID, "light_block"),
			new LightBlock(AbstractBlock.Settings.copy(Blocks.GLOWSTONE).mapColor(MapColor.PURPLE).breakInstantly()));

	public static final BlockEntityType<LightBlockEntity> LIGHT_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			Identifier.of(MODID, "light_block_entity"),
			BlockEntityType.Builder.create(LightBlockEntity::new, LIGHT_BLOCK).build());

	public static final Block BLOOD_TABLE = Registry.register(Registries.BLOCK,
			Identifier.of(MODID, "blood_table"),
			new CurseTableBlood());

	public static final BlockEntityType<CurseTableEntity> BLOOD_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			Identifier.of(MODID, "blood_table_entity"),
			BlockEntityType.Builder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.BLOOD), BLOOD_TABLE).build());

	public static final Block DEATH_TABLE = Registry.register(Registries.BLOCK,
			Identifier.of(MODID, "death_table"),
			new CurseTableDeath());

	public static final BlockEntityType<CurseTableEntity> DEATH_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			Identifier.of(MODID, "death_table_entity"),
			BlockEntityType.Builder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.DEATH), DEATH_TABLE).build());

	public static final Block ENERGY_TABLE = Registry.register(Registries.BLOCK,
			Identifier.of(MODID, "energy_table"),
			new CurseTableEnergy());

	public static final BlockEntityType<CurseTableEntity> ENERGY_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			Identifier.of(MODID, "energy_table_entity"),
			BlockEntityType.Builder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.ENERGY), ENERGY_TABLE).build());

	public static final Block WISDOM_TABLE = Registry.register(Registries.BLOCK,
			Identifier.of(MODID, "wisdom_table"),
			new CurseTableWisdom());

	public static final BlockEntityType<CurseTableEntity> WISDOM_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			Identifier.of(MODID, "wisdom_table_entity"),
			BlockEntityType.Builder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.WISDOM), WISDOM_TABLE).build());

	public static void init() {
		Paranormal.LOGGER.info("Registering blocks for " + MODID);
	}
}
