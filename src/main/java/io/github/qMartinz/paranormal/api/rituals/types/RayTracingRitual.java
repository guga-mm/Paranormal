package io.github.qMartinz.paranormal.api.rituals.types;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.networking.ParticleMessages;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import team.lodestar.lodestone.systems.rendering.particle.Easing;

public interface RayTracingRitual {
	default boolean onHit(ParanormalElement element, LivingEntity caster, double range){
		EntityHitResult entityHitResult = ProjectileUtil.raycast(caster, caster.getCameraPosVec(1f),
				caster.getCameraPosVec(1f).add(caster.getRotationVec(1f).multiply(range)),
				caster.getBoundingBox().stretch(caster.getRotationVec(1.0F).multiply(range))
						.expand(1.0D, 1.0D, 1.0D),
				(entityx) -> !entityx.isSpectator() && entityx.collides(),
				range);

		Vec3d end = caster.getCameraPosVec(1f).add(caster.getRotationVec(1f).multiply(range));
		BlockHitResult blockHitResult = caster.getWorld().raycast(new RaycastContext(caster.getCameraPosVec(1f), end,
				RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, caster));

		if (entityHitResult != null){
			return onEntityHit(caster, entityHitResult.getEntity());
		}
		if (blockHitResult != null){
			return onBlockHit(caster, caster.getWorld().getBlockState(blockHitResult.getBlockPos()), blockHitResult.getBlockPos());
		}

		return false;
	}

	default boolean onCastByMob(ParanormalElement element, MobEntity caster, LivingEntity target){
		return onEntityHit(caster, target);
	}

	boolean onEntityHit(LivingEntity caster, Entity target);
	boolean onBlockHit(LivingEntity caster, BlockState state, BlockPos blockPos);

	default void rayCastEffects(ParanormalElement element, ParanormalElement complement, LivingEntity caster, Vec3d hitPos){
		if (caster.getWorld() instanceof ServerWorld world){
			Vec3d start = new Vec3d(caster.getEyePos().x, caster.getEyePos().y, caster.getEyePos().z);
			Vec3d end = new Vec3d(hitPos.x, hitPos.y, hitPos.z);
			Vec3d diff = start.subtract(end);
			int amount = (int) (start.distanceTo(end) * 12);

			for (int i = 0; i < amount; i++){
				double x = start.x - ((diff.x / amount) * i);
				double y = start.y - ((diff.y / amount) * i);
				double z = start.z - ((diff.z / amount) * i);

				if (element == ParanormalElement.DEATH){
					ParticleMessages.spawnLumitransparentParticle(world, ModParticleRegistry.GLOWING_PARTICLE, x, y, z,
							0f, 0f, 0f, element.particleColorS(), element.particleColorE(), 1f,
							0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 0.1f,
							0.1f, 0.1f, 1f, Easing.LINEAR, Easing.LINEAR, 46, 0f);

					if (caster.getPitch() < 45 && caster.getPitch() > -45) {
						if (caster.getHorizontalFacing().getAxis() == Direction.Axis.Z) {
							ParticleMessages.spawnLumitransparentParticle(world, ModParticleRegistry.GLOWING_PARTICLE,
									x + Math.sin(i / 3f) * 0.25d, y + Math.cos(i / 3f) * 0.25d, z,
									0d, 0d, 0d, complement.complementColorS(), complement.complementColorE(),
									1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
									0.15f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
						} else {
							ParticleMessages.spawnLumitransparentParticle(world, ModParticleRegistry.GLOWING_PARTICLE,
									x, y + Math.cos(i / 3f) * 0.25d, z + Math.sin(i / 3f) * 0.25d,
									0d, 0d, 0d, complement.complementColorS(), complement.complementColorE(),
									1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
									0.15f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
						}
					} else {
						ParticleMessages.spawnLumitransparentParticle(world, ModParticleRegistry.GLOWING_PARTICLE,
								x + Math.sin(i / 3f) * 0.25d, y, z + Math.cos(i / 3f) * 0.25d,
								0d, 0d, 0d, complement.complementColorS(), complement.complementColorE(),
								1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
								0.15f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
					}
				} else {
					ParticleMessages.spawnAdditiveParticle(world, ModParticleRegistry.GLOWING_PARTICLE, x, y, z,
							0f, 0f, 0f, element.particleColorS(), element.particleColorE(), 1f,
							0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 0.1f,
							0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 46, 0f);

					if (caster.getPitch() < 45 && caster.getPitch() > -45) {
						if (caster.getHorizontalFacing().getAxis() == Direction.Axis.Z) {
							ParticleMessages.spawnAdditiveParticle(world, ModParticleRegistry.GLOWING_PARTICLE,
									x + Math.sin(i / 3f) * 0.25d, y + Math.cos(i / 3f) * 0.25d, z,
									0d, 0d, 0d, complement.complementColorS(), complement.complementColorE(),
									1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
									0.15f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
						} else {
							ParticleMessages.spawnAdditiveParticle(world, ModParticleRegistry.GLOWING_PARTICLE,
									x, y + Math.cos(i / 3f) * 0.25d, z + Math.sin(i / 3f) * 0.25d,
									0d, 0d, 0d, complement.complementColorS(), complement.complementColorE(),
									1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
									0.15f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
						}
					} else {
						ParticleMessages.spawnAdditiveParticle(world, ModParticleRegistry.GLOWING_PARTICLE,
								x + Math.sin(i / 3f) * 0.25d, y, z + Math.cos(i / 3f) * 0.25d,
								0d, 0d, 0d, complement.complementColorS(), complement.complementColorE(),
								1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
								0.15f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 0f);
					}
				}
			}
		}
	}
}
