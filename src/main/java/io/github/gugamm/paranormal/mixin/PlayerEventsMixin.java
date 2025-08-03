package io.github.gugamm.paranormal.mixin;

import io.github.gugamm.paranormal.api.events.ParanormalEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEventsMixin {
	@Inject(at = @At("HEAD"), method = "takeShieldHit")
	protected void takenShieldHit(LivingEntity attacker, CallbackInfo ci){
		ParanormalEvents.TAKEN_SHIELD_HIT.invoker().takenShieldHit(attacker, (PlayerEntity)(Object)this);
	}

	@ModifyVariable(method = "addExperience(I)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	protected int addExperience(int experience){
		return ParanormalEvents.ADD_EXPERIENCE.invoker().addExperience((PlayerEntity)(Object)this, experience);
	}

	@ModifyVariable(method = "addExperienceLevels(I)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	protected int addExperienceLevels(int levels){
		return ParanormalEvents.ADD_EXPERIENCE_LEVELS.invoker().addExperienceLevels((PlayerEntity)(Object)this, levels);
	}
}
