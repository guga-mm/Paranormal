package io.github.qMartinz.paranormal.mixin;

import io.github.qMartinz.paranormal.api.events.ParanormalEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEventsMixin {

	@Inject(at = @At("HEAD"), method = "takeShieldHit")
	protected void takenShieldHit(LivingEntity attacker, CallbackInfo ci){
		ParanormalEvents.TAKEN_SHIELD_HIT.invoker().takenShieldHit(attacker, (PlayerEntity)(Object)this);
	}
}
