package io.github.qMartinz.paranormal.api.rituals.types;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public interface RayTracingRitual {
	default boolean onHit(LivingEntity caster, double range){
		EntityHitResult entityHitResult = ProjectileUtil.raycast(caster, caster.getCameraPosVec(1f),
				caster.getCameraPosVec(1f).add(caster.getRotationVec(1f).multiply(range)),
				caster.getBoundingBox().stretch(caster.getRotationVec(1.0F).multiply(range))
						.expand(1.0D, 1.0D, 1.0D),
				(entityx) -> !entityx.isSpectator() && entityx.collides(),
				range);

		Vec3d end = caster.getCameraPosVec(1f).add(caster.getRotationVec(1f).multiply(range));
		BlockHitResult blockHitResult = caster.world.raycast(new RaycastContext(caster.getCameraPosVec(1f), end,
				RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, caster));

		if (entityHitResult != null){
			return onEntityHit(caster, entityHitResult.getEntity());
		}
		if (blockHitResult != null){
			return onBlockHit(caster, caster.world.getBlockState(blockHitResult.getBlockPos()), blockHitResult.getBlockPos());
		}

		return false;
	}

	boolean onEntityHit(LivingEntity caster, Entity target);
	boolean onBlockHit(LivingEntity caster, BlockState state, BlockPos blockPos);
}
