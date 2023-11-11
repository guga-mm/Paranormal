package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.entity.events.LivingEntityEvents;
import io.github.qMartinz.paranormal.entity.events.PlayerEvents;
import io.github.qMartinz.paranormal.entity.events.VillagerFearEvents;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.registry.*;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.event.api.EntityWorldChangeEvents;
import org.quiltmc.qsl.entity.event.api.LivingEntityDeathCallback;
import org.quiltmc.qsl.entity.event.api.ServerEntityTickCallback;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
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
		ItemGroupRegistry.registerItemGroup();
		ItemRegistry.init();
		BlockRegistry.init();

		ModMessages.registerC2SPackets();

		registerEvents();
	}

	private void registerEvents(){
		LivingEntityDeathCallback.EVENT.register(LivingEntityEvents::onDeath);
		ServerEntityTickCallback.EVENT.register(VillagerFearEvents::onTick);
	}
}
