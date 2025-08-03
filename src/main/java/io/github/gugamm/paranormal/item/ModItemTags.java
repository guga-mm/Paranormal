package io.github.gugamm.paranormal.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ModItemTags {
	public static final TagKey<Item> BLOOD_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "blood_fuel"));
	public static final TagKey<Item> ENERGY_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "energy_fuel"));
	public static final TagKey<Item> DEATH_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "death_fuel"));
	public static final TagKey<Item> WISDOM_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "wisdom_fuel"));
}
