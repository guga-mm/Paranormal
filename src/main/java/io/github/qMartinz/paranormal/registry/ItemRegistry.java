package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.entity.FogEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ItemRegistry {
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

	public static void init() {
		Paranormal.LOGGER.info("Registering items for " + MODID);
	}
}
