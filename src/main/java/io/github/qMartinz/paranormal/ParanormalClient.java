package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.client.PexHudOverlay;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import io.github.qMartinz.paranormal.registry.ModEntityRegistry;
import io.github.qMartinz.paranormal.registry.ModModelLayerRegistry;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.ActionResult;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ParanormalClient implements ClientModInitializer {
	public static PlayerData playerData = new PlayerData();
	@Override
	public void onInitializeClient(ModContainer mod) {
		ModEntityRegistry.registerRenderers();
		ModParticleRegistry.registerFactories();
		ModModelLayerRegistry.init();

		ModMessages.registerS2CPackets();

		registerEvents();
		setBlockRenderLayers();
	}

	private void registerEvents(){
		HudRenderCallback.EVENT.register(new PexHudOverlay());
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (world.getBlockState(hitResult.getBlockPos()).isOf(ModBlockRegistry.TRANSCENDANCE_ALTAR)){
				MinecraftClient.getInstance().setScreen(new AttributesScreen());
				return ActionResult.CONSUME;
			}
			return ActionResult.PASS;
		});
	}

	private void setBlockRenderLayers(){
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlockRegistry.TRANSCENDANCE_ALTAR, RenderLayer.getCutout());

	}
}
