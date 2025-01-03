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
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ModDamageTypes {
	public static final RegistryKey<DamageType> BLOOD_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Paranormal.MODID,
			"blood"));
	public static final RegistryKey<DamageType> DEATH_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Paranormal.MODID,
			"death"));
	public static final RegistryKey<DamageType> ENERGY_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Paranormal.MODID,
			"energy"));
	public static final RegistryKey<DamageType> WISDOM_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Paranormal.MODID,
			"wisdom"));
	public static final RegistryKey<DamageType> FEAR_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Paranormal.MODID,
			"fear"));

	public static DamageSource getDamageSource(RegistryKey<DamageType> damageType, World world){
		return new DamageSource(
			world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getHolder(damageType).orElse(null));
	}

	public static boolean isEntityResistant(LivingEntity entity, DamageSource damage){
		if (entity instanceof PlayerEntity player) {
			return StateSaverAndLoader.getPlayerState(player).powers.stream()
					.anyMatch(p -> p instanceof Affinity && p.getElement().getDamage(entity.getWorld()).getType() == damage.getType());
		}

		return false;
	}

	public static boolean isEntityWeakTo(LivingEntity entity, DamageSource damage){
		if(entity instanceof PlayerEntity player) {
			if (StateSaverAndLoader.getPlayerState(player).powers.stream().anyMatch(p -> p instanceof Affinity)){
				ParanormalElement affElement = StateSaverAndLoader.getPlayerState(player).powers.stream().filter(p -> p instanceof Affinity).findFirst().get().getElement();
				return affElement.getOpressingElement().getDamage(entity.getWorld()).getType() == damage.getType();
			}

			return false;
		}

		return false;
	}
}
