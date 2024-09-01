package io.github.qMartinz.paranormal.util;

import io.github.qMartinz.paranormal.api.curses.CurseInstance;
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

	public static List<NbtCompound> readCurses(NbtCompound tag){
		List<NbtCompound> curses = new ArrayList<>();
		if(tag == null)
			return curses;

		for(String s : tag.getKeys()){
			if(s.contains("curse_")){
				curses.add(tag.getCompound(s));
			}
		}
		return curses;
	}

	public static void writeStrings(NbtCompound nbt, String prefix, Collection<String> strings){
		int i = 0;
		for(String s : strings){
			nbt.putString(prefix + "_" + i, s);
			i++;
		}
	}

	public static void writeCurses(NbtCompound tag, Collection<CurseInstance> instances){
		int i = 0;
		for(CurseInstance c : instances){
			NbtCompound curse = new NbtCompound();
			curse.putString("id", c.getCurse().getId().toString());
			curse.putInt("uses", c.getUses());

			tag.put("curse_" + i, curse);
			i++;
		}
	}
}
