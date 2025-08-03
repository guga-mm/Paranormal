package io.github.gugamm.paranormal.mixin;

import io.github.gugamm.paranormal.api.events.ParanormalEvents;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityEventsMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"), method = "baseTick")
	protected void baseTick(CallbackInfo ci){
		if (!((Entity)(Object)this).getWorld().isClient()) {
			ParanormalEvents.ENTITY_TICK.invoker().entityTick((Entity)(Object)this);
		}
	}
}
