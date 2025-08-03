package io.github.gugamm.paranormal.util;

import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.api.curses.CurseInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.*;

public class NBTUtil {
	public static List<Identifier> readStrings(NbtCompound nbt, String prefix){
		List<Identifier> ids = new ArrayList<>();
		if(nbt == null)
			return ids;

		for (int i = 0; i < nbt.getKeys().size(); i++) {
			String itemKey = nbt.getKeys().stream().toList().get(i);
			String[] splitKey = itemKey.split("_" + prefix + "_");
			String namespace = Objects.equals(splitKey[0], "") ? Paranormal.MODID : splitKey[0];

			ids.add(Identifier.of(namespace, nbt.getString(itemKey)));
		}
		return ids;
	}

	public static List<NbtCompound> readCurses(NbtCompound tag){
		List<NbtCompound> curses = new ArrayList<>();
		if (tag == null)
			return curses;

		for (String s : tag.getKeys()) {
			if(s.contains("curse_")){
				curses.add(tag.getCompound(s));
			}
		}
		return curses;
	}

	public static void writeStrings(NbtCompound nbt, String prefix, List<Identifier> ids){
		int i = 0;
		for (Identifier id : ids) {
			nbt.putString(id.getNamespace() + "_" + prefix + "_" + i, id.getPath());
			i++;
		}
	}

	public static void writeCurses(NbtCompound tag, Collection<CurseInstance> instances){
		int i = 0;
		for (CurseInstance c : instances) {
			NbtCompound curse = new NbtCompound();
			curse.putString("id", c.getCurse().getId().toString());
			curse.putInt("uses", c.getUses());

			tag.put("curse_" + i, curse);
			i++;
		}
	}
}
