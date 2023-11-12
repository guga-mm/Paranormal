package io.github.qMartinz.paranormal.api.powers;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

public class PowerRegistry {
	private static final HashMap<Identifier, AbstractPower> powers = new HashMap<>();

	private static void register(Identifier key, AbstractPower power) {
		powers.compute(key, (key2, old) -> {
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
