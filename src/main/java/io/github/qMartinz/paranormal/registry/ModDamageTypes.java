package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModDamageTypes {
	public static final RegistryKey<DamageType> RITUAL = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Paranormal.MODID,
			"ritual"));

	private static final Map<RegistryKey<DamageType>, DamageSource> damageSourceCache = new HashMap<>();

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
