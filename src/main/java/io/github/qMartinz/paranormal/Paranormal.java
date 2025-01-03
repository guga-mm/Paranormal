package io.github.qMartinz.paranormal;

import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.curses.CurseHelper;
import io.github.qMartinz.paranormal.api.events.ParanormalEvents;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.entity.events.LivingEntityEvents;
import io.github.qMartinz.paranormal.entity.events.VillagerFearEvents;
import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.networking.Payloads;
import io.github.qMartinz.paranormal.registry.*;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Paranormal implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Paranormal");
	public static final String MODID = "paranormal";

	@Override
	public void onInitialize(ModContainer modContainer) {
		// Registry
		ModEntityRegistry.init();
		ModParticleRegistry.init();
		ModItemGroupRegistry.init();
		ModItemRegistry.init();
		ModBlockRegistry.init();
		ModRitualRegistry.init();
		ModPowerRegistry.init();
		ModCommandRegistry.registerCommands();
		ModComponentsRegistry.initialize();

		Payloads.registerPayloads();
		ModMessages.registerC2SPackets();

		registerEvents();
	}

	private void registerEvents(){
		ServerLivingEntityEvents.AFTER_DEATH.register(LivingEntityEvents::onDeath);

		ParanormalEvents.ENTITY_TICK.register(VillagerFearEvents::onTick);
		ParanormalEvents.ENTITY_TICK.register(LivingEntityEvents::onTick);
		ParanormalEvents.ENTITY_TICK.register((entity) -> {
			if (entity instanceof PlayerEntity player) {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
				playerData.powers.forEach(p -> p.onTick(player));
			}

			if (entity instanceof LivingEntity livingEntity) CurseHelper.doTickEffects(livingEntity);
		});

		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			if (source.getSource() instanceof LivingEntity living) {
				amount = CurseHelper.doPostAttackEffects(living, entity, amount, source);
			}

			amount = CurseHelper.doPostHurtEffects(entity, source.getSource(), amount, source);

			if (source.getAttacker() instanceof PlayerEntity playerAttacker) {
				PlayerData playerDataAttacker = StateSaverAndLoader.getPlayerState(playerAttacker);
				for (ParanormalPower p : playerDataAttacker.powers) {
					amount = p.onAttack(playerAttacker, amount, entity, source);
				}
			}

			if (entity instanceof PlayerEntity playerTarget) {
				PlayerData playerDataTarget = StateSaverAndLoader.getPlayerState(playerTarget);
				for (ParanormalPower p : playerDataTarget.powers) {
					amount = p.onHurt(playerTarget, amount, source.getAttacker(), source);
				}
			}

			return true;
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

		ParanormalEvents.ADD_EXPERIENCE.register(((player, experience) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
			for (ParanormalPower p : playerData.powers){
				experience = p.onXPChange(player, experience);
			}

			return experience;
		}));

		ParanormalEvents.ADD_EXPERIENCE_LEVELS.register(((player, levels) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
			for (ParanormalPower p : playerData.powers){
				levels = p.onXPLevelChange(player, levels);
			}

			return levels;
		}));

		PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
			boolean allowBreak = true;
			for (ParanormalPower p : playerData.powers) {
				boolean result = p.onBlockBreak(player, world, pos, state, blockEntity);
				allowBreak = allowBreak && result;
			}

			allowBreak = allowBreak && CurseHelper.doBlockBreakEffects(player, pos, state);

			return allowBreak;
		}));

		ParanormalEvents.RITUAL_CAST.register((ritual, caster) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(caster);
			boolean allowCasting = true;

			if (caster instanceof PlayerEntity player){
				for (ParanormalPower p : playerData.powers) {
					boolean result = p.onCastRitual(player, ritual);
					allowCasting = allowCasting && result;
				}
			}

			allowCasting = allowCasting && CurseHelper.isRitualCasterEffects(caster, ritual);

			return allowCasting;
		});

		ParanormalEvents.RITUAL_TARGET.register((ritual, caster, target) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(caster);
			boolean allowCasting = true;

			if (caster instanceof PlayerEntity player){
				for (ParanormalPower p : playerData.powers) {
					boolean result = p.onTargetRitual(player, ritual, caster);
					allowCasting = allowCasting && result;
				}
			}

			allowCasting = allowCasting && CurseHelper.isRitualTargetEffects(target, ritual, caster);

			return allowCasting;
		});
	}
}
