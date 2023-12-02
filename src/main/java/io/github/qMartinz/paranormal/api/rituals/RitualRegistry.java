package io.github.qMartinz.paranormal.api.rituals;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RitualRegistry {
	public static final HashMap<Identifier, AbstractRitual> rituals = new HashMap<>();

	public static AbstractRitual register(Identifier key, AbstractRitual ritual) {
		return rituals.compute(key, (key2, old) -> {
			if (old == null) {
				return ritual;
			}
			throw new IllegalStateException("Trying to add duplicate key \"" + key + "\" to ritual registry");
		});
	}

	public static Optional<AbstractRitual> getRitual(Identifier key) {
		return Optional.ofNullable(rituals.get(key));
	}
	public static Identifier getId(AbstractRitual value) {
		return rituals.entrySet().stream().filter(entry -> value.equals(entry.getValue()))
				.map(Map.Entry::getKey).findFirst().orElse(null);
	}
}
