package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.entity.FogEntity;
import io.github.qMartinz.paranormal.particle.FogParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ParticleRegistry {
	public static final DefaultParticleType FOG_1 = register("fog_particle_1", FabricParticleTypes.simple());
	public static final DefaultParticleType FOG_2 = register("fog_particle_2", FabricParticleTypes.simple());

	static DefaultParticleType register(String id, DefaultParticleType type) {
		return Registry.register(Registries.PARTICLE_TYPE, new Identifier(MODID, id), type);
	}

	public static void registerFactories(){
		ParticleFactoryRegistry.getInstance().register(FOG_1, FogParticle.DefaultFactory::new);
		ParticleFactoryRegistry.getInstance().register(FOG_2, FogParticle.DefaultFactory::new);
	}

	public static void init() {
		Paranormal.LOGGER.info("Registering particles for " + MODID);
	}
}
