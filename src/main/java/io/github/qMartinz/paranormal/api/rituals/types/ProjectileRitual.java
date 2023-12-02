package io.github.qMartinz.paranormal.api.rituals.types;

import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.entity.RitualProjectile;
import io.github.qMartinz.paranormal.registry.ModEntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public interface ProjectileRitual {
	default boolean onShoot(LivingEntity caster, AbstractRitual ritual){
		RitualProjectile projectile = new RitualProjectile(ModEntityRegistry.RITUAL_PROJECTILE, caster.getWorld(), ritual);
		projectile.setProperties(caster, caster.getPitch(), caster.getYaw(), 0.0f, 1.5f, 0.0f);
		projectile.setOwner(caster);
		caster.world.spawnEntity(projectile);
		return true;
	};
	void onHit(LivingEntity caster, HitResult hitResult);
	void onEntityHit(LivingEntity caster, EntityHitResult hitResult);
	void onBlockHit(LivingEntity caster, BlockHitResult hitResult);
}
