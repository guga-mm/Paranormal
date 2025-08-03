package io.github.gugamm.paranormal.registry;

import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.particle.FogParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ModParticleRegistry {
	public static final SimpleParticleType FOG_1 = register("fog_particle_1", FabricParticleTypes.simple());
	public static final SimpleParticleType FOG_2 = register("fog_particle_2", FabricParticleTypes.simple());

	static SimpleParticleType register(String id, SimpleParticleType type) {
		return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MODID, id), type);
	}

	public static void registerFactories(){
		ParticleFactoryRegistry.getInstance().register(FOG_1, FogParticle.DefaultFactory::new);
		ParticleFactoryRegistry.getInstance().register(FOG_2, FogParticle.DefaultFactory::new);
	}

	public static void init() {
		Paranormal.LOGGER.info("Registering particles for " + MODID);
	}
}
