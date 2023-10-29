package io.github.qMartinz.paranormal.util;

import net.minecraft.nbt.NbtCompound;

public class PexData {
	public static void addPex(IEntityDataSaver player, int amount){
		NbtCompound nbt = player.getPersistentData();
		int pex = nbt.getInt("pex");

		pex = Math.max(0, Math.min(pex + amount, 20));

		nbt.putInt("pex", pex);
	}

	public static void removePex(IEntityDataSaver player, int amount){
		NbtCompound nbt = player.getPersistentData();
		int pex = nbt.getInt("pex");

		pex = Math.max(0, Math.min(pex - amount, 20));

		nbt.putInt("pex", pex);
	}

	public static void setPex(IEntityDataSaver player, int amount){
		NbtCompound nbt = player.getPersistentData();
		nbt.putInt("pex", amount);
	}

	public static int getPex(IEntityDataSaver player){
		NbtCompound nbt = player.getPersistentData();
		return nbt.getInt("pex");
	}

	public static int getPexPercentage(IEntityDataSaver player){
		if (getPex(player) == 20) return 99;
		return getPex(player) * 5;
	}

	public static void addXp(IEntityDataSaver player, int amount){
		NbtCompound nbt = player.getPersistentData();
		int pex = getPex(player);
		int xp = nbt.getInt("xp") + amount;

		while (xp >= (pex + 1) * 50 && pex < 20){
			xp -= (pex + 1) * 50;
			pex++;
		}

		nbt.putInt("xp", xp);
		nbt.putInt("pex", pex);
	}

	public static void setXp(IEntityDataSaver player, int amount){
		NbtCompound nbt = player.getPersistentData();
		nbt.putInt("xp", amount);
	}

	public static int getXp(IEntityDataSaver player){
		NbtCompound nbt = player.getPersistentData();
		return nbt.getInt("xp");
	}
}
