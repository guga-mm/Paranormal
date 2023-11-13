package io.github.qMartinz.paranormal.api.powers;

import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

public class PowerRegistry {
	private static final HashMap<Identifier, AbstractPower> powers = new HashMap<>();

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
}
