package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.power.Affinity;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModDamageTypes {
	public static final RegistryKey<DamageType> BLOOD_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Paranormal.MODID,
			"blood"));
	public static final RegistryKey<DamageType> WISDOM_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Paranormal.MODID,
			"wisdom"));
	public static final RegistryKey<DamageType> DEATH_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Paranormal.MODID,
			"death"));
	public static final RegistryKey<DamageType> ENERGY_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Paranormal.MODID,
			"energy"));
	public static final RegistryKey<DamageType> FEAR_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Paranormal.MODID,
			"fear"));

	private static final Map<RegistryKey<DamageType>, DamageSource> damageSourceCache = new HashMap<>();

	public static boolean isEntityResistant(LivingEntity entity, DamageSource damage){
		if (entity instanceof PlayerEntity player) {
			return StateSaverAndLoader.getPlayerState(player).powers.stream()
					.anyMatch(p -> p instanceof Affinity && p.getElement().getDamage(entity.getDamageSources()).getType() == damage.getType());
		}

		return false;
	}

	public static boolean isEntityWeakTo(LivingEntity entity, DamageSource damage){
		if(entity instanceof PlayerEntity player) {
			if (StateSaverAndLoader.getPlayerState(player).powers.stream().anyMatch(p -> p instanceof Affinity)){
				ParanormalElement affElement = StateSaverAndLoader.getPlayerState(player).powers.stream().filter(p -> p instanceof Affinity).findFirst().get().getElement();
				return affElement.getOpressingElement().getDamage(entity.getDamageSources()).getType() == damage.getType();
			}

			return false;
		}

		return false;
	}

	public static DamageSource getSource(DamageSources damageSources, RegistryKey<DamageType> damageType) {
		return damageSourceCache.computeIfAbsent(damageType, damageSources::create);
	}

	public static DamageSource getSource(DamageSources damageSources, RegistryKey<DamageType> damageType, Entity source) {
		return damageSourceCache.computeIfAbsent(damageType, (k) -> damageSources.create(k, source));
	}

	public static DamageSource getSource(DamageSources damageSources, RegistryKey<DamageType> damageType, Entity source, Entity attacker) {
		return damageSourceCache.computeIfAbsent(damageType, (k) -> damageSources.create(k, source, attacker));
	}
}
