package io.github.qMartinz.paranormal.util;

import net.minecraft.nbt.NbtCompound;

public class FearData {
	public static int addFear(IEntityDataSaver villager, int amount){
		NbtCompound nbt = villager.getPersistentData();
		int fear = nbt.getInt("fear_meter");

		fear = Math.max(0, Math.min(fear + amount, 200));

		nbt.putInt("fear_meter", fear);
		return fear;
	}

	public static int removeFear(IEntityDataSaver villager, int amount){
		NbtCompound nbt = villager.getPersistentData();
		int fear = nbt.getInt("fear_meter");

		fear = Math.max(0, Math.min(fear - amount, 200));

		nbt.putInt("fear_meter", fear);
		return fear;
	}

	public static int getFear(IEntityDataSaver villager){
		NbtCompound nbt = villager.getPersistentData();
		return nbt.getInt("fear_meter");
	}

	public static void setFearTimer(IEntityDataSaver villager, int amount){
		NbtCompound nbt = villager.getPersistentData();
		nbt.putInt("fear_timer", amount);
	}

	public static int getFearTimer(IEntityDataSaver villager){
		NbtCompound nbt = villager.getPersistentData();
		return nbt.getInt("fear_timer");
	}

	public static void setCalmTimer(IEntityDataSaver villager, int amount){
		NbtCompound nbt = villager.getPersistentData();
		nbt.putInt("calm_timer", amount);
	}

	public static int getCalmTimer(IEntityDataSaver villager){
		NbtCompound nbt = villager.getPersistentData();
		return nbt.getInt("calm_timer");
	}
}
