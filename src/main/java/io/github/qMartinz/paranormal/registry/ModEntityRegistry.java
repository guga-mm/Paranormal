package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.entity.CorpseEntity;
import io.github.qMartinz.paranormal.entity.FogEntity;
import io.github.qMartinz.paranormal.entity.RitualProjectile;
import io.github.qMartinz.paranormal.entity.renderer.FogRenderer;
import io.github.qMartinz.paranormal.entity.renderer.RitualProjectileRenderer;
import io.github.qMartinz.paranormal.entity.renderer.VillagerCorpseRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ModEntityRegistry {
	public static final EntityType<FogEntity> FOG = Registry.register(Registries.ENTITY_TYPE,
			Identifier.of(MODID, "fog"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, FogEntity::new)
			.dimensions(EntityDimensions.fixed(1f, 1f)).build());

	public static final EntityType<FogEntity> RUINED_FOG = Registry.register(Registries.ENTITY_TYPE,
			Identifier.of(MODID, "ruined_fog"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, FogEntity::new)
					.dimensions(EntityDimensions.fixed(1f, 1f)).build());

	public static final EntityType<CorpseEntity> VILLAGER_CORPSE = Registry.register(Registries.ENTITY_TYPE,
			Identifier.of(MODID, "villager_corpse"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, CorpseEntity::new)
					.dimensions(EntityDimensions.fixed(1.2f, 0.2f)).build());

	public static final EntityType<RitualProjectile> RITUAL_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
			Identifier.of(MODID, "ritual_projectile"),
			FabricEntityTypeBuilder.<RitualProjectile>create(SpawnGroup.MISC, RitualProjectile::new)
					.dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

	public static void registerRenderers(){
		EntityRendererRegistry.register(FOG, FogRenderer::new);
		EntityRendererRegistry.register(RUINED_FOG, FogRenderer::new);
		EntityRendererRegistry.register(VILLAGER_CORPSE, VillagerCorpseRenderer::new);
		EntityRendererRegistry.register(RITUAL_PROJECTILE, RitualProjectileRenderer::new);
	}

	public static void init() {
		Paranormal.LOGGER.info("Registering entities for " + MODID);
	}
}
