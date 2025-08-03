package io.github.gugamm.paranormal.mixin;

import io.github.gugamm.paranormal.util.IEntityDataSaver;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerFearMixin implements IEntityDataSaver {
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
			nbt.put("paranormal.fear_data", persistentData);
		}
	}

	@Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
	protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
		if (nbt.contains("paranormal.fear_data", 10)){
			persistentData = nbt.getCompound("paranormal.fear_data");
		}
	}
}
