package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.registry.EntityRegistry;
import io.github.qMartinz.paranormal.registry.ModelLayerRegistry;
import io.github.qMartinz.paranormal.registry.ParticleRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ParanormalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		EntityRegistry.registerRenderers();
		ParticleRegistry.registerFactories();
		ModelLayerRegistry.init();
	}
}
