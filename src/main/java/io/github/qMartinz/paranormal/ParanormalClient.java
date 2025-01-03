package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.events.ParanormalEvents;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.client.event.ClientEvents;
import io.github.qMartinz.paranormal.client.event.KeyInputHandler;
import io.github.qMartinz.paranormal.client.hud.PexHud;
import io.github.qMartinz.paranormal.client.hud.RitualHud;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.registry.*;
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
	public static RitualHud ritualHud = new RitualHud();

	@Override
	public void onInitializeClient(ModContainer modContainer) {
		ModEntityRegistry.registerRenderers();
		ModBlockRegistry.registerRenderers();
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

		ClientEvents.POWER_SCREEN_INIT.register((screen) -> {
			if (screen.element == ParanormalElement.BLOOD) {
				screen.addPower(ModPowerRegistry.TESTE_A);
				screen.addPower(ModPowerRegistry.TESTE_B);
				screen.addPower(ModPowerRegistry.TESTE_C);
				screen.addPower(ModPowerRegistry.TESTE_D);
				screen.addPower(ModPowerRegistry.TESTE_E);
				screen.addPower(ModPowerRegistry.TESTE_F);
				screen.addPower(ModPowerRegistry.TESTE_G);
				screen.addPower(ModPowerRegistry.TESTE_H);
				screen.addPower(ModPowerRegistry.TESTE_I);
				screen.addPower(ModPowerRegistry.TESTE_J);
				screen.addPower(ModPowerRegistry.TESTE_K);
				screen.addPower(ModPowerRegistry.TESTE_L);
				screen.addPower(ModPowerRegistry.TESTE_M);
				screen.addPower(ModPowerRegistry.TESTE_N);
				screen.addPower(ModPowerRegistry.TESTE_O);
				screen.addPower(ModPowerRegistry.TESTE_P);
				screen.addPower(ModPowerRegistry.TESTE_Q);
				screen.addPower(ModPowerRegistry.TESTE_R);
			}
		});

		ParanormalEvents.USE_ITEM.register(((world, player, hand, result) -> {
			PlayerData playerData = ParanormalClient.playerData;
			for (ParanormalPower p : playerData.powers) {
				return p.onUseItem(world, player, hand, result);
			}

			return result;
		}));

		ParanormalEvents.FINISH_USING_ITEM.register(((stack, world, user) -> {
			PlayerData playerData = ParanormalClient.playerData;
			for (ParanormalPower p : playerData.powers) {
				return p.onFinishUseItem(stack, world, user);
			}

			return stack;
		}));

		ParanormalEvents.TICK_USE_ITEM.register(((world, user, stack, remainingUseTicks) -> {
			PlayerData playerData = ParanormalClient.playerData;
			for (ParanormalPower p : playerData.powers) {
				p.onTickUseItem(world, user, stack, remainingUseTicks);
			}
		}));
	}

	private void setBlockRenderLayers(){
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlockRegistry.TRANSCENDANCE_ALTAR, RenderLayer.getCutout());
	}
}
