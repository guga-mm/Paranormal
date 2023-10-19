package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.entity.events.CorpseEvents;
import io.github.qMartinz.paranormal.registry.EntityRegistry;
import io.github.qMartinz.paranormal.registry.ItemRegistry;
import io.github.qMartinz.paranormal.registry.ModelLayerRegistry;
import io.github.qMartinz.paranormal.registry.ParticleRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.event.api.LivingEntityDeathCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Paranormal implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Paranormal");
	public static final String MODID = "paranormal";

	@Override
	public void onInitialize(ModContainer mod) {
		// Registry
		EntityRegistry.init();
		ParticleRegistry.init();
		ItemRegistry.init();

		registerEvents();
	}

	private void registerEvents(){
		LivingEntityDeathCallback.EVENT.register(CorpseEvents::onDeath);
	}
}
