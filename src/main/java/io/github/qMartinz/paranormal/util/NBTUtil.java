package io.github.qMartinz.paranormal.util;

import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NBTUtil {
	public static List<String> readStrings(NbtCompound nbt, String prefix){
		List<String> strings = new ArrayList<>();
		if(nbt == null)
			return strings;

		for(String s : nbt.getKeys()){
			if(s.contains(prefix)){
				strings.add(nbt.getString(s));
			}
		}
		return strings;
	}

	public static void writeStrings(NbtCompound nbt, String prefix, Collection<String> strings){
		int i = 0;
		for(String s : strings){
			nbt.putString(prefix + "_" + i, s);
			i++;
		}
	}
}
