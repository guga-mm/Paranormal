package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroupRegistry {
	public static ItemGroup PARANORMAL = Registry.register(Registries.ITEM_GROUP, Identifier.of(Paranormal.MODID, "main_group"), FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItemRegistry.ORGAN))
			.name(Text.translatable("itemgroup.paranormal.items"))
			.entries((context, entries) -> {
				entries.addItem(ModItemRegistry.ORGAN);
				entries.addItem(ModItemRegistry.ASHES);
				entries.addItem(ModItemRegistry.TRANSCENDANCE_ALTAR);
				entries.addItem(ModItemRegistry.WISDOM_TABLE);
				entries.addItem(ModItemRegistry.ENERGY_TABLE);
				entries.addItem(ModItemRegistry.DEATH_TABLE);
				entries.addItem(ModItemRegistry.BLOOD_TABLE);
			}).build());

	public static void init() {}
}
