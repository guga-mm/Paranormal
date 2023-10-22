package io.github.qMartinz.paranormal.mixin;

import io.github.qMartinz.paranormal.util.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerPexMixin implements IEntityDataSaver {
	private NbtCompound persistentData;

	@Override
	public NbtCompound getPersistentData() {
		if (persistentData == null){
			persistentData = new NbtCompound();
		}

		return persistentData;
	}

	@Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
	protected void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
		if (persistentData != null){
			nbt.put("paranormal.pex_data", persistentData);
		}
	}

	@Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
	protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
		if (nbt.contains("paranormal.pex_data", 10)){
			persistentData = nbt.getCompound("paranormal.pex_data");
		}
	}
}
