package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.block.TranscendanceAltar;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ModBlockRegistry {
	public static final Block TRANSCENDANCE_ALTAR = Registry.register(Registries.BLOCK,
			new Identifier(MODID, "transcendance_altar"),
			new TranscendanceAltar(QuiltBlockSettings.copyOf(Blocks.ENCHANTING_TABLE)));

	public static void init() {
		Paranormal.LOGGER.info("Registering blocks for " + MODID);
	}
}
