package io.github.qMartinz.paranormal.util;

import net.minecraft.nbt.NbtCompound;

public class NexData {
	public static int addNex(IEntityDataSaver player, int amount) {
		NbtCompound nbt = player.getPersistentData();
		int nex = nbt.getInt("nex");
		if(nex + amount >= 20) {
			nex = 20;
		} else {
			nex += amount;
		}

		nbt.putInt("nex", nex);
		// sync the data
		return nex;
	}

	public static int getNex(IEntityDataSaver player) {
		NbtCompound nbt = player.getPersistentData();
		return nbt.getInt("nex");
	}
}
