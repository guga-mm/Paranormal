package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ModItemRegistry {
	public static final Item ORGAN = Registry.register(Registries.ITEM,
			new Identifier(MODID, "organ"),
			new Item(new QuiltItemSettings()
					.food(new FoodComponent.Builder()
							.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 1.0f)
							.statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 0), 1.0f)
							.alwaysEdible().hunger(2).build()).maxCount(16)));

	public static final Item ASHES = Registry.register(Registries.ITEM,
			new Identifier(MODID, "ashes"),
			new Item(new QuiltItemSettings()
					.fireproof().maxCount(16)));

	public static final Item TRANSCENDANCE_ALTAR = Registry.register(Registries.ITEM,
			new Identifier(MODID, "transcendance_altar"),
			new BlockItem(ModBlockRegistry.TRANSCENDANCE_ALTAR, new QuiltItemSettings().rarity(Rarity.UNCOMMON)));

	public static void addToItemGroup(ItemGroup group, Item item) {
		ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addItem(item));
	}

	public static void addItemsToItemGroup(){
		addToItemGroup(ModItemGroupRegistry.PARANORMAL, ORGAN);
		addToItemGroup(ModItemGroupRegistry.PARANORMAL, ASHES);
		addToItemGroup(ModItemGroupRegistry.PARANORMAL, ModBlockRegistry.TRANSCENDANCE_ALTAR.asItem());
	}

	public static void init() {
		Paranormal.LOGGER.info("Registering items for " + MODID);
		addItemsToItemGroup();
	}
}
