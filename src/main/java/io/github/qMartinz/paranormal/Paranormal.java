package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.events.ParanormalEvents;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.entity.events.LivingEntityEvents;
import io.github.qMartinz.paranormal.entity.events.VillagerFearEvents;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.registry.*;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
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

		ServerEntityTickCallback.EVENT.register((e, b) -> {
			if (e instanceof PlayerEntity player) {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
				playerData.powers.forEach(p -> p.onTick(player));
			}
		});

		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			boolean allowDamage = true;

			if (source.getAttacker() instanceof PlayerEntity playerAttacker) {
				PlayerData playerDataAttacker = StateSaverAndLoader.getPlayerState(playerAttacker);
				for (ParanormalPower p : playerDataAttacker.powers) {
					allowDamage = allowDamage && p.onAttack(playerAttacker, amount, entity, source);
				}
			}

			if (entity instanceof PlayerEntity playerTarget) {
				PlayerData playerDataTarget = StateSaverAndLoader.getPlayerState(playerTarget);
				for (ParanormalPower p : playerDataTarget.powers) {
					allowDamage = allowDamage && p.onHurt(playerTarget, amount, source.getAttacker(), source);
				}
			}

			return allowDamage;
		});

		ParanormalEvents.TAKEN_SHIELD_HIT.register(((attacker, target) -> {
			PlayerData playerDataAttacker = StateSaverAndLoader.getPlayerState(attacker);
			PlayerData playerDataTarget = StateSaverAndLoader.getPlayerState(target);

			if (attacker instanceof PlayerEntity player) {
				for (ParanormalPower p : playerDataAttacker.powers) {
					p.onAttackBlocked(player, target);
				}
			}

			if (target instanceof PlayerEntity player) {
				for (ParanormalPower p : playerDataTarget.powers) {
					p.onShieldBlock(player, attacker);
				}
			}
		}));
	}
}
