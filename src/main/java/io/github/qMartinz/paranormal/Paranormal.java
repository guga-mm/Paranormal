package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.entity.events.LivingEntityEvents;
import io.github.qMartinz.paranormal.entity.events.VillagerFearEvents;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.registry.*;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.event.api.LivingEntityDeathCallback;
import org.quiltmc.qsl.entity.event.api.ServerEntityTickCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Paranormal implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Paranormal");
	public static final String MODID = "paranormal";

	@Override
	public void onInitialize(ModContainer mod) {
		// Registry
		ModEntityRegistry.init();
		ModParticleRegistry.init();
		ModItemGroupRegistry.registerItemGroup();
		ModItemRegistry.init();
		ModBlockRegistry.init();
		ModRitualRegistry.init();
		ModPowerRegistry.init();
		ModCommandRegistry.registerCommands();

		ModMessages.registerC2SPackets();

		registerEvents();
	}

	private void registerEvents(){
		LivingEntityDeathCallback.EVENT.register(LivingEntityEvents::onDeath);
		ServerEntityTickCallback.EVENT.register(VillagerFearEvents::onTick);
		ServerEntityTickCallback.EVENT.register(LivingEntityEvents::onTick);
	}
}
