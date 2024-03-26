package io.github.qMartinz.paranormal.ritual;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.RayTracingRitual;
import io.github.qMartinz.paranormal.api.rituals.types.SelfRitual;
import io.github.qMartinz.paranormal.networking.ParticleMessages;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;

public class HealingRitual extends AbstractRitual implements SelfRitual, RayTracingRitual {
	public HealingRitual() {
		super(ParanormalElement.DEATH, 1, 1f, 4.5d, true);
	}
	@Override
	public boolean useOnSelf(LivingEntity caster) {
		if (caster.isSneaking()){
			caster.heal(5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean onEntityHit(LivingEntity caster, Entity target) {
		if (!caster.isSneaking() && target instanceof LivingEntity livingEntity){
			livingEntity.heal(5f);
			rayCastEffects(getElement(), getComplement(), caster, target.getEyePos());

			if (target.getWorld() instanceof ServerWorld world) ParticleMessages.spawnLumitransparentCircle(world,
					ModParticleRegistry.GLOWING_PARTICLE, target.getX(), target.getY() + 0.2d, target.getZ(),
					0.5d, 12, 0d, 0.25d, 0d, getElement().particleColorS(), getElement().particleColorE(),
					1f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR,
					0.3f, 0f, -1f, 1f, Easing.LINEAR, Easing.LINEAR, 36, 1f);

			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockHit(LivingEntity caster, BlockState state, BlockPos blockPos) {
		if (BoneMealItem.useOnFertilizable(ItemStack.EMPTY, caster.getWorld(), blockPos)) {
			if (!caster.getWorld().isClient) {
				caster.getWorld().syncWorldEvent(1505, blockPos, 0);
				rayCastEffects(getElement(), getComplement(), caster, blockPos.ofCenter());
			}
			return true;
		}
		return false;
	}
}
