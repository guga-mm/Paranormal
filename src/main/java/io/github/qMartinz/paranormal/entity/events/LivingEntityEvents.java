package io.github.qMartinz.paranormal.entity.events;

import io.github.qMartinz.paranormal.entity.CorpseEntity;
import io.github.qMartinz.paranormal.registry.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;

public class LivingEntityEvents {
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
