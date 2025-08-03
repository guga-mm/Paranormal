package io.github.gugamm.paranormal.registry;

import io.github.gugamm.paranormal.Paranormal;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroupRegistry {
	public static ItemGroup PARANORMAL = Registry.register(Registries.ITEM_GROUP, Identifier.of(Paranormal.MODID, "main_group"), FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItemRegistry.ORGAN))
			.displayName(Text.translatable("itemgroup.paranormal.items"))
			.entries((context, entries) -> {
				entries.add(ModItemRegistry.ORGAN);
				entries.add(ModItemRegistry.ASHES);
				entries.add(ModItemRegistry.TRANSCENDANCE_ALTAR);
				entries.add(ModItemRegistry.WISDOM_TABLE);
				entries.add(ModItemRegistry.ENERGY_TABLE);
				entries.add(ModItemRegistry.DEATH_TABLE);
				entries.add(ModItemRegistry.BLOOD_TABLE);
			}).build());

	public static void init() {}
}
