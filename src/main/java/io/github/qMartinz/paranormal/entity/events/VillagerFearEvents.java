package io.github.qMartinz.paranormal.entity.events;

import io.github.qMartinz.paranormal.entity.CorpseEntity;
import io.github.qMartinz.paranormal.entity.FogEntity;
import io.github.qMartinz.paranormal.registry.EntityRegistry;
import io.github.qMartinz.paranormal.util.FearData;
import io.github.qMartinz.paranormal.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;

import java.util.List;

public class VillagerFearEvents {
	public static void onTick(Entity entity, boolean b) {
		if (entity instanceof VillagerEntity villager){
			int fear = ((IEntityDataSaver) villager).getPersistentData().getInt("fear_meter");
			int fearIncrement = 0;

			List<HostileEntity> undeadList = villager.getWorld().getEntitiesByClass(HostileEntity.class,
					Box.of(villager.getPos(), 128d, 128d, 128d),
					e -> (e.distanceTo(villager) <= 15d || villager.canSee(e)) && e.isUndead());

			List<RaiderEntity> raiderList = villager.getWorld().getEntitiesByClass(RaiderEntity.class,
					Box.of(villager.getPos(), 128d, 128d, 128d),
					e -> e.distanceTo(villager) <= 15d || villager.canSee(e));

			List<CorpseEntity> corpseList = villager.getWorld().getEntitiesByClass(CorpseEntity.class,
					Box.of(villager.getPos(), 128d, 128d, 128d),
					e -> e.distanceTo(villager) <= 15d || villager.canSee(e));

			if (!undeadList.isEmpty() && fear < 100) {
				fearIncrement += undeadList.size()/2;
			}

			if (!raiderList.isEmpty() && fear < 50) {
				fearIncrement += raiderList.size()/2;
			}

			if (!corpseList.isEmpty() && fear < 150) {
				fearIncrement += corpseList.size();
			}

			if (villager.getHealth() < villager.getMaxHealth() && fear < 25) {
				fearIncrement += (villager.getMaxHealth()/villager.getHealth());
			}

			if (fearIncrement > 0){
				if (FearData.getFearTimer((IEntityDataSaver) villager) > 0)
					FearData.setFearTimer((IEntityDataSaver) villager, FearData.getFearTimer((IEntityDataSaver) villager) - 1);

				int fearTimer = FearData.getFearTimer((IEntityDataSaver) villager);
				if (fearTimer <= 0) frighten(villager, fearIncrement);
			} else {
				if (FearData.getCalmTimer((IEntityDataSaver) villager) > 0)
					FearData.setCalmTimer((IEntityDataSaver) villager, FearData.getCalmTimer((IEntityDataSaver) villager) - 1);

				int calmTimer = FearData.getCalmTimer((IEntityDataSaver) villager);
				if (calmTimer <= 0) calm(villager, 2);
			}
		}
	}

	private static void frighten(VillagerEntity entity, int amount){
		// Fear particle effects & sfx
		for(int i = 0; i < 5; ++i) {
			double d = entity.getRandom().nextGaussian() * 0.02;
			double e = entity.getRandom().nextGaussian() * 0.02;
			double f = entity.getRandom().nextGaussian() * 0.02;
			entity.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER, entity.getParticleX(1.0), entity.getRandomBodyY() + 1.0, entity.getParticleZ(1.0), d, e, f);
		}

		FearData.addFear((IEntityDataSaver) entity, amount);
		FearData.setFearTimer((IEntityDataSaver) entity, 120);

		List<FogEntity> fogList = entity.getWorld().getEntitiesByClass(FogEntity.class, Box.of(entity.getPos(), 150, 150, 150),
				e -> e.distanceTo(entity) <= e.getRadius());

		if (FearData.getFear((IEntityDataSaver) entity) >= 50 && fogList.isEmpty()) {
			FogEntity fog = new FogEntity(EntityRegistry.FOG, entity.getWorld());
			fog.setPosition(entity.getPos());
			entity.getWorld().spawnEntity(fog);
		}
	}

	private static void calm(VillagerEntity entity, int amount){
		// Fear particle effects & sfx
		for(int i = 0; i < 5; ++i) {
			double d = entity.getRandom().nextGaussian() * 0.02;
			double e = entity.getRandom().nextGaussian() * 0.02;
			double f = entity.getRandom().nextGaussian() * 0.02;
			entity.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, entity.getParticleX(1.0), entity.getRandomBodyY() + 1.0, entity.getParticleZ(1.0), d, e, f);
		}

		FearData.removeFear((IEntityDataSaver) entity, amount);
		FearData.setCalmTimer((IEntityDataSaver) entity, 120);
	}
}
