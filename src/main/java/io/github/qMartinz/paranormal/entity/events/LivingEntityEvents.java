package io.github.qMartinz.paranormal.entity.events;

import io.github.qMartinz.paranormal.api.ParanormalAttribute;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.entity.CorpseEntity;
import io.github.qMartinz.paranormal.registry.ModEntityRegistry;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class LivingEntityEvents {
	public static void onDeath(LivingEntity killed, DamageSource source){
		if (killed instanceof MerchantEntity && source.getAttacker().isPlayer()) {
			CorpseEntity corpse = new CorpseEntity(ModEntityRegistry.VILLAGER_CORPSE, killed.getWorld());
			corpse.setPosition(killed.getPos());
			corpse.setPitch(killed.getPitch());
			corpse.setYaw(killed.getYaw());
			killed.getWorld().spawnEntity(corpse);
		}
	}

	public static void onTick(Entity entity, boolean b) {
		if (entity instanceof ServerPlayerEntity player && player.isSleeping()){
			PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
			playerData.setOccultPoints(playerData.getOccultPoints() +
					(0.025d * (playerData.getAttribute(ParanormalAttribute.PRESENCE) * 0.25d + 1)));
			playerData.syncToClient(player);
		}
	}
}
