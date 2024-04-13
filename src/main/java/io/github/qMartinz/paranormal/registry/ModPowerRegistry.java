package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.api.powers.PowerRegistry;
import io.github.qMartinz.paranormal.power.Affinity;
import net.minecraft.util.Identifier;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ModPowerRegistry {
	public static final ParanormalPower BLOOD_AFFINITY = PowerRegistry.register(new Identifier(MODID, "blood_affinity"), new Affinity(ParanormalElement.BLOOD));
	public static final ParanormalPower ENERGY_AFFINITY = PowerRegistry.register(new Identifier(MODID, "energy_affinity"), new Affinity(ParanormalElement.ENERGY));
	public static final ParanormalPower WISDOM_AFFINITY = PowerRegistry.register(new Identifier(MODID, "wisdom_affinity"), new Affinity(ParanormalElement.WISDOM));
	public static final ParanormalPower DEATH_AFFINITY = PowerRegistry.register(new Identifier(MODID, "death_affinity"), new Affinity(ParanormalElement.DEATH));

	public static void init(){
		Paranormal.LOGGER.info("Registering powers for " + MODID);
	}
}
