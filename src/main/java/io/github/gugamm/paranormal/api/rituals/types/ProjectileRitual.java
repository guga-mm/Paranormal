package io.github.gugamm.paranormal.api.rituals.types;

import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import io.github.gugamm.paranormal.entity.RitualProjectile;
import io.github.gugamm.paranormal.registry.ModEntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public interface ProjectileRitual {
	default boolean onShoot(LivingEntity caster, AbstractRitual ritual){
		RitualProjectile projectile = new RitualProjectile(ModEntityRegistry.RITUAL_PROJECTILE, caster.getWorld(), caster, ritual);

		Vec3d eyePos = caster.getEyePos();
		Vec3d lookDirection = caster.getRotationVec(1.0F);

		projectile.setPosition(eyePos.x + lookDirection.x, eyePos.y + lookDirection.y, eyePos.z + lookDirection.z);
		projectile.setVelocity(lookDirection.x, lookDirection.y, lookDirection.z, 0.7f, 1.0f); // Ajuste a velocidade (o '0.7f') e imprecisão (o '1.0f') conforme necessário

		caster.getWorld().spawnEntity(projectile);
		return true;
	}
	void onHit(LivingEntity caster, HitResult hitResult);
	void onEntityHit(LivingEntity caster, EntityHitResult hitResult);
	void onBlockHit(LivingEntity caster, BlockHitResult hitResult);
}
