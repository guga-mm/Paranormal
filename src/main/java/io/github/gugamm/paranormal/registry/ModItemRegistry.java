package io.github.gugamm.paranormal.registry;

import io.github.gugamm.paranormal.Paranormal;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ModItemRegistry {
	public static final Item ORGAN = Registry.register(Registries.ITEM,
			Identifier.of(MODID, "organ"),
			new Item(new Item.Settings()
					.food(FoodComponents.ROTTEN_FLESH).maxCount(16)));

	public static final Item ASHES = Registry.register(Registries.ITEM,
			Identifier.of(MODID, "ashes"),
			new Item(new Item.Settings()
					.fireproof().maxCount(16)));

	public static final Item TRANSCENDANCE_ALTAR = Registry.register(Registries.ITEM,
			Identifier.of(MODID, "transcendance_altar"),
			new BlockItem(ModBlockRegistry.TRANSCENDANCE_ALTAR, new Item.Settings().rarity(Rarity.UNCOMMON)));

	public static final Item BLOOD_TABLE = Registry.register(Registries.ITEM,
			Identifier.of(MODID, "blood_table"),
			new BlockItem(ModBlockRegistry.BLOOD_TABLE, new Item.Settings().rarity(Rarity.RARE)));

	public static final Item DEATH_TABLE = Registry.register(Registries.ITEM,
			Identifier.of(MODID, "death_table"),
			new BlockItem(ModBlockRegistry.DEATH_TABLE, new Item.Settings().rarity(Rarity.RARE)));

	public static final Item ENERGY_TABLE = Registry.register(Registries.ITEM,
			Identifier.of(MODID, "energy_table"),
			new BlockItem(ModBlockRegistry.ENERGY_TABLE, new Item.Settings().rarity(Rarity.RARE)));

	public static final Item WISDOM_TABLE = Registry.register(Registries.ITEM,
			Identifier.of(MODID, "wisdom_table"),
			new BlockItem(ModBlockRegistry.WISDOM_TABLE, new Item.Settings().rarity(Rarity.RARE)));

	public static void init() {
		Paranormal.LOGGER.info("Registering items for " + MODID);
	}
}
