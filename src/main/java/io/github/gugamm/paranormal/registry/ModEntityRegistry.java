package io.github.gugamm.paranormal.registry;

import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.entity.CorpseEntity;
import io.github.gugamm.paranormal.entity.FogEntity;
import io.github.gugamm.paranormal.entity.RitualProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ModEntityRegistry {
	public static final EntityType<FogEntity> FOG = register(Identifier.of(MODID, "fog"),
		EntityType.Builder.create(FogEntity::new, SpawnGroup.MISC).dimensions(1f, 1f));

	public static final EntityType<FogEntity> RUINED_FOG = register(Identifier.of(MODID, "ruined_fog"),
			EntityType.Builder.create(FogEntity::new, SpawnGroup.MISC).dimensions(1f, 1f));

	public static final EntityType<CorpseEntity> VILLAGER_CORPSE = register(Identifier.of(MODID, "villager_corpse"),
			EntityType.Builder.create(CorpseEntity::new, SpawnGroup.MISC).dimensions(1.2f, 0.2f));

	public static final EntityType<RitualProjectile> RITUAL_PROJECTILE = register(Identifier.of(MODID, "ritual_projectile"),
			EntityType.Builder.<RitualProjectile>create(RitualProjectile::new, SpawnGroup.MISC).dimensions(0.5f, 0.5f));

	public static <T extends Entity> EntityType<T> register(Identifier id, EntityType.Builder<T> builder) {
		return Registry.register(Registries.ENTITY_TYPE, id,
			builder.build());
	}

	public static void init() {
		Paranormal.LOGGER.info("Registering entities for " + MODID);
	}
}
