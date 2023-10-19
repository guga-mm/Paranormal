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
		List<FogEntity> fogList = killed.getWorld().getEntitiesByClass(FogEntity.class, Box.of(killed.getPos(), 150, 150, 150),
				e -> e.distanceTo(killed) <= 75);

		for (FogEntity fog : fogList){
			if (fog.entitiesWithin().contains(killed)) {
				if (fog.getIntensity() >= 3 && fog.getType() == EntityRegistry.FOG){
					FogEntity ruinedFog = new FogEntity(EntityRegistry.RUINED_FOG, fog.getWorld());
					ruinedFog.setPosition(fog.getPos());
					fog.remove(Entity.RemovalReason.DISCARDED);
					killed.getWorld().spawnEntity(ruinedFog);
				} else {
					fog.setIntensity(fog.getIntensity() + 1);
					fog.setRadius(fog.getRadius() + 25);
				}
			}
		}

		if (killed instanceof MerchantEntity) {
			CorpseEntity corpse = new CorpseEntity(EntityRegistry.VILLAGER_CORPSE, killed.getWorld());
			corpse.setPosition(killed.getPos());
			corpse.setPitch(killed.getPitch());
			corpse.setYaw(killed.getYaw());
			killed.getWorld().spawnEntity(corpse);
		};

		List<CorpseEntity> corpseList = killed.getWorld().getEntitiesByClass(CorpseEntity.class, Box.of(killed.getPos(), 30, 30, 30),
				e -> e.distanceTo(killed) <= 75);

		if (corpseList.size() >= 3 && fogList.stream().noneMatch(fog -> fog.entitiesWithin().contains(killed))) {
			FogEntity fog = new FogEntity(EntityRegistry.FOG, killed.getWorld());
			fog.setPosition(killed.getPos());
			killed.getWorld().spawnEntity(fog);
		}
	}
}
