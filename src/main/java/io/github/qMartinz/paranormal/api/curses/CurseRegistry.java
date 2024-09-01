package io.github.qMartinz.paranormal.api.curses;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurseRegistry {
	public static final HashMap<Identifier, AbstractCurse> curses = new HashMap<>();

	public static AbstractCurse register(Identifier key, AbstractCurse ritual) {
		return curses.compute(key, (key2, old) -> {
			if (old == null) {
				return ritual;
			}
			throw new IllegalStateException("Trying to add duplicate key \"" + key + "\" to curse registry");
		});
	}

	public static Optional<AbstractCurse> getCurse(Identifier key) {
		return Optional.ofNullable(curses.get(key));
	}
	public static Identifier getId(AbstractCurse value) {
		return curses.entrySet().stream().filter(entry -> value.equals(entry.getValue()))
				.map(Map.Entry::getKey).findFirst().orElse(null);
	}
}
