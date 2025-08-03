package io.github.gugamm.paranormal.entity.events;

import io.github.gugamm.paranormal.api.ParanormalAttribute;
import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.entity.CorpseEntity;
import io.github.gugamm.paranormal.registry.ModEntityRegistry;
import io.github.gugamm.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.server.network.ServerPlayerEntity;

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

	public static void onTick(Entity entity) {
		if (entity instanceof ServerPlayerEntity player && player.isSleeping()){
			PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
			playerData.setOccultPoints(playerData.getOccultPoints() +
					(0.025d * (playerData.getAttribute(ParanormalAttribute.PRESENCE) * 0.25d + 1)));
			StateSaverAndLoader.syncOccultPointsToClient(player);
		}
	}
}
