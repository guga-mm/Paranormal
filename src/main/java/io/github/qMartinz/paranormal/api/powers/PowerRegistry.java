package io.github.qMartinz.paranormal.api.powers;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PowerRegistry {
	public static final HashMap<Identifier, ParanormalPower> powers = new HashMap<>();

	public static ParanormalPower register(Identifier key, ParanormalPower power) {
		return powers.compute(key, (key2, old) -> {
			if (old == null) {
				return power;
			}
			throw new IllegalStateException("Trying to add duplicate key \"" + key + "\" to power registry");
		});
	}

	public static Optional<ParanormalPower> getPower(Identifier key) {
		return Optional.ofNullable(powers.get(key));
	}
	public static Identifier getId(ParanormalPower value) {
		return powers.entrySet().stream().filter(entry -> value.equals(entry.getValue()))
				.map(Map.Entry::getKey).findFirst().orElse(null);
	}
}
