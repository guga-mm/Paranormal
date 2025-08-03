package io.github.gugamm.paranormal.mixin;

import io.github.gugamm.paranormal.api.events.ParanormalEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemEventsMixin {
	@Inject(at = @At("RETURN"), method = "use", cancellable = true)
	protected void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
		cir.setReturnValue(ParanormalEvents.USE_ITEM.invoker().useItem(world, user, hand, cir.getReturnValue()));
	}

	@Inject(at = @At("RETURN"), method = "finishUsing", cancellable = true)
	protected void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir){
		cir.setReturnValue(ParanormalEvents.FINISH_USING_ITEM.invoker().finishUsingItem(cir.getReturnValue(), world, user));
	}

	@Inject(at = @At("TAIL"), method = "usageTick")
	protected void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci){
		ParanormalEvents.TICK_USE_ITEM.invoker().tickUseItem(world, user, stack, remainingUseTicks);
	}
}
