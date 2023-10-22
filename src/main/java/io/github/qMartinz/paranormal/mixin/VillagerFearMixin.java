package io.github.qMartinz.paranormal.mixin;

import io.github.qMartinz.paranormal.util.FearData;
import io.github.qMartinz.paranormal.util.IEntityDataSaver;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.annotation.Target;
import java.util.List;

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
