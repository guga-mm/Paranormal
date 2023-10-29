package io.github.qMartinz.paranormal.entity.events;

import io.github.qMartinz.paranormal.entity.CorpseEntity;
import io.github.qMartinz.paranormal.entity.FogEntity;
import io.github.qMartinz.paranormal.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.util.math.Box;

import java.util.List;

public class CorpseEvents {
	public static void onDeath(LivingEntity killed, DamageSource source){
		if (killed instanceof MerchantEntity && source.getAttacker().isPlayer()) {
			CorpseEntity corpse = new CorpseEntity(EntityRegistry.VILLAGER_CORPSE, killed.getWorld());
			corpse.setPosition(killed.getPos());
			corpse.setPitch(killed.getPitch());
			corpse.setYaw(killed.getYaw());
			killed.getWorld().spawnEntity(corpse);
		}
	}
}
