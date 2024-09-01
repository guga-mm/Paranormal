package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.block.*;
import io.github.qMartinz.paranormal.block.entities.*;
import io.github.qMartinz.paranormal.block.entities.renderer.CurseTableRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ModBlockRegistry {
	public static final Block TRANSCENDANCE_ALTAR = Registry.register(Registries.BLOCK,
			new Identifier(MODID, "transcendance_altar"),
			new TranscendanceAltar(QuiltBlockSettings.copyOf(Blocks.ENCHANTING_TABLE)));

	public static final Block LIGHT_BLOCK = Registry.register(Registries.BLOCK,
			new Identifier(MODID, "light_block"),
			new LightBlock(QuiltBlockSettings.copyOf(Blocks.GLOWSTONE).mapColor(MapColor.PURPLE).breakInstantly()));

	public static final BlockEntityType<LightBlockEntity> LIGHT_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MODID, "light_block_entity"),
			QuiltBlockEntityTypeBuilder.create(LightBlockEntity::new, LIGHT_BLOCK).build());

	public static final Block CURSE_TABLE_BLOOD = Registry.register(Registries.BLOCK,
			new Identifier(MODID, "curse_table_blood"),
			new CurseTableBlood());

	public static final BlockEntityType<CurseTableEntity> CURSE_TABLE_BLOOD_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MODID, "curse_table_blood_entity"),
			QuiltBlockEntityTypeBuilder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.BLOOD), CURSE_TABLE_BLOOD).build());

	public static final Block CURSE_TABLE_DEATH = Registry.register(Registries.BLOCK,
			new Identifier(MODID, "curse_table_death"),
			new CurseTableDeath());

	public static final BlockEntityType<CurseTableEntity> CURSE_TABLE_DEATH_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MODID, "curse_table_death_entity"),
			QuiltBlockEntityTypeBuilder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.DEATH), CURSE_TABLE_DEATH).build());

	public static final Block CURSE_TABLE_ENERGY = Registry.register(Registries.BLOCK,
			new Identifier(MODID, "curse_table_energy"),
			new CurseTableEnergy());

	public static final BlockEntityType<CurseTableEntity> CURSE_TABLE_ENERGY_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MODID, "curse_table_energy_entity"),
			QuiltBlockEntityTypeBuilder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.ENERGY), CURSE_TABLE_ENERGY).build());

	public static final Block CURSE_TABLE_WISDOM = Registry.register(Registries.BLOCK,
			new Identifier(MODID, "curse_table_wisdom"),
			new CurseTableWisdom());

	public static final BlockEntityType<CurseTableEntity> CURSE_TABLE_WISDOM_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MODID, "curse_table_wisdom_entity"),
			QuiltBlockEntityTypeBuilder.create((pos, state) -> new CurseTableEntity(pos, state, ParanormalElement.WISDOM), CURSE_TABLE_WISDOM).build());

	public static void registerRenderers(){
		BlockEntityRendererFactories.register(CURSE_TABLE_BLOOD_ENTITY, CurseTableRenderer::new);
		BlockEntityRendererFactories.register(CURSE_TABLE_DEATH_ENTITY, CurseTableRenderer::new);
		BlockEntityRendererFactories.register(CURSE_TABLE_ENERGY_ENTITY, CurseTableRenderer::new);
		BlockEntityRendererFactories.register(CURSE_TABLE_WISDOM_ENTITY, CurseTableRenderer::new);
	}

	public static void init() {
		Paranormal.LOGGER.info("Registering blocks for " + MODID);
	}
}
