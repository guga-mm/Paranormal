package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.block.LightBlock;
import io.github.qMartinz.paranormal.block.TranscendanceAltar;
import io.github.qMartinz.paranormal.block.entities.LightBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

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

	public static void init() {
		Paranormal.LOGGER.info("Registering blocks for " + MODID);
	}
}
