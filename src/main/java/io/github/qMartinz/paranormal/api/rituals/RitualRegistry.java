package io.github.qMartinz.paranormal.api.rituals;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

public class RitualRegistry {
	private static final HashMap<Identifier, AbstractRitual> rituals = new HashMap<>();

	private static void register(Identifier key, AbstractRitual ritual) {
		rituals.compute(key, (key2, old) -> {
			if (old == null) {
				return ritual;
			}
			throw new IllegalStateException("Trying to add duplicate key \"" + key + "\" to ritual registry");
		});
	}

	public static Optional<AbstractRitual> getRitual(Identifier key) {
		return Optional.ofNullable(rituals.get(key));
	}
}
