package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.events.InitPowerScreenContentsCallback;
import io.github.qMartinz.paranormal.client.event.KeyInputHandler;
import io.github.qMartinz.paranormal.client.hud.PexHud;
import io.github.qMartinz.paranormal.client.hud.RitualHud;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerButton;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.registry.*;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.ActionResult;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class ParanormalClient implements ClientModInitializer {
	public static PlayerData playerData = new PlayerData();
	public static RitualHud ritualHud = new RitualHud();
	@Override
	public void onInitializeClient(ModContainer mod) {
		ModEntityRegistry.registerRenderers();
		ModParticleRegistry.registerFactories();
		ModModelLayerRegistry.init();

		KeyInputHandler.register();
		ModMessages.registerS2CPackets();

		registerEvents();
		setBlockRenderLayers();
	}

	private void registerEvents(){
		HudRenderCallback.EVENT.register(new PexHud());
		HudRenderCallback.EVENT.register(ritualHud);

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (world.getBlockState(hitResult.getBlockPos()).isOf(ModBlockRegistry.TRANSCENDANCE_ALTAR)){
				MinecraftClient.getInstance().setScreen(new AttributesScreen());
				return ActionResult.CONSUME;
			}
			return ActionResult.PASS;
		});

		InitPowerScreenContentsCallback.EVENT.register((screen -> {
			int tabWidth = screen.width - 41;

			if (screen.element == ParanormalElement.BLOOD){
				screen.addPowerIcon(new PowerButton((tabWidth/2) + 27, screen.height/2 - 10, ModPowerRegistry.BLOOD_AFFINITY));
			}

			if (screen.element == ParanormalElement.DEATH){
				screen.addPowerIcon(new PowerButton((tabWidth/2) + 27, screen.height/2 - 10, ModPowerRegistry.DEATH_AFFINITY));
			}

			if (screen.element == ParanormalElement.WISDOM){
				screen.addPowerIcon(new PowerButton((tabWidth/2) + 27, screen.height/2 - 10, ModPowerRegistry.WISDOM_AFFINITY));
			}

			if (screen.element == ParanormalElement.ENERGY){
				screen.addPowerIcon(new PowerButton((tabWidth/2) + 27, screen.height/2 - 10, ModPowerRegistry.ENERGY_AFFINITY));
			}
		}));
	}

	private void setBlockRenderLayers(){
		BlockRenderLayerMap.put(RenderLayer.getCutout(), ModBlockRegistry.TRANSCENDANCE_ALTAR);

	}
}
