package io.github.qMartinz.paranormal.api.powers;

import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PowerRegistry {
	public static final HashMap<Identifier, AbstractPower> powers = new HashMap<>();

	public static AbstractPower register(Identifier key, AbstractPower power) {
		return powers.compute(key, (key2, old) -> {
			if (old == null) {
				return power;
			}
			throw new IllegalStateException("Trying to add duplicate key \"" + key + "\" to power registry");
		});
	}

	public static Optional<AbstractPower> getPower(Identifier key) {
		return Optional.ofNullable(powers.get(key));
	}
	public static Identifier getId(AbstractPower value) {
		return powers.entrySet().stream().filter(entry -> value.equals(entry.getValue()))
				.map(Map.Entry::getKey).findFirst().orElse(null);
	}
}
