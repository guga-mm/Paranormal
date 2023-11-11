package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.client.PexHudOverlay;
import io.github.qMartinz.paranormal.client.event.PlayerClientEvents;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.registry.BlockRegistry;
import io.github.qMartinz.paranormal.registry.EntityRegistry;
import io.github.qMartinz.paranormal.registry.ModelLayerRegistry;
import io.github.qMartinz.paranormal.registry.ParticleRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;

public class ParanormalClient implements ClientModInitializer {
	public static PlayerData playerData = new PlayerData();
	@Override
	public void onInitializeClient(ModContainer mod) {
		EntityRegistry.registerRenderers();
		ParticleRegistry.registerFactories();
		ModelLayerRegistry.init();

		ModMessages.registerS2CPackets();

		registerEvents();
		setBlockRenderLayers();
	}

	private void registerEvents(){
		HudRenderCallback.EVENT.register(new PexHudOverlay());
	}

	private void setBlockRenderLayers(){
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.TRANSCENDANCE_ALTAR, RenderLayer.getCutout());

	}
}
