package io.github.gugamm.paranormal;

import io.github.gugamm.paranormal.api.ParanormalElement;
import io.github.gugamm.paranormal.api.ClientPlayerData;
import io.github.gugamm.paranormal.api.events.ParanormalEvents;
import io.github.gugamm.paranormal.api.powers.ParanormalPower;
import io.github.gugamm.paranormal.block.entity.renderer.CurseTableRenderer;
import io.github.gugamm.paranormal.entity.renderer.FogRenderer;
import io.github.gugamm.paranormal.entity.renderer.RitualProjectileRenderer;
import io.github.gugamm.paranormal.entity.renderer.VillagerCorpseRenderer;
import io.github.gugamm.paranormal.event.ClientEvents;
import io.github.gugamm.paranormal.event.KeyInputHandler;
import io.github.gugamm.paranormal.hud.PexHud;
import io.github.gugamm.paranormal.hud.RitualHud;
import io.github.gugamm.paranormal.registry.*;
import io.github.gugamm.paranormal.screen.AttributesScreen;
import io.github.gugamm.paranormal.networking.ClientModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.ActionResult;

public class ParanormalClient implements ClientModInitializer {
	public static ClientPlayerData clientPlayerData = new ClientPlayerData();
	public static RitualHud ritualHud = new RitualHud();

	@Override
	public void onInitializeClient() {
		registerRenderers();
		ModParticleRegistry.registerFactories();
		ModModelLayerRegistry.init();

		KeyInputHandler.register();
		ClientModMessages.registerS2CPackets();

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
			ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
			for (ParanormalPower p : clientPlayerData.powers) {
				return p.onUseItem(world, player, hand, result);
			}

			return result;
		}));

		ParanormalEvents.FINISH_USING_ITEM.register(((stack, world, user) -> {
			ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
			for (ParanormalPower p : clientPlayerData.powers) {
				return p.onFinishUseItem(stack, world, user);
			}

			return stack;
		}));

		ParanormalEvents.TICK_USE_ITEM.register(((world, user, stack, remainingUseTicks) -> {
			ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
			for (ParanormalPower p : clientPlayerData.powers) {
				p.onTickUseItem(world, user, stack, remainingUseTicks);
			}
		}));
	}

	public static void registerRenderers(){
		EntityRendererRegistry.register(ModEntityRegistry.FOG, FogRenderer::new);
		EntityRendererRegistry.register(ModEntityRegistry.RUINED_FOG, FogRenderer::new);
		EntityRendererRegistry.register(ModEntityRegistry.VILLAGER_CORPSE, VillagerCorpseRenderer::new);
		EntityRendererRegistry.register(ModEntityRegistry.RITUAL_PROJECTILE, RitualProjectileRenderer::new);

		BlockEntityRendererFactories.register(ModBlockRegistry.BLOOD_TABLE_ENTITY, CurseTableRenderer::new);
		BlockEntityRendererFactories.register(ModBlockRegistry.DEATH_TABLE_ENTITY, CurseTableRenderer::new);
		BlockEntityRendererFactories.register(ModBlockRegistry.ENERGY_TABLE_ENTITY, CurseTableRenderer::new);
		BlockEntityRendererFactories.register(ModBlockRegistry.WISDOM_TABLE_ENTITY, CurseTableRenderer::new);
	}

	private void setBlockRenderLayers(){
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlockRegistry.TRANSCENDANCE_ALTAR, RenderLayer.getCutout());
	}
}
