package io.github.gugamm.paranormal.registry;

import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.api.ParanormalElement;
import io.github.gugamm.paranormal.api.powers.ParanormalPower;
import io.github.gugamm.paranormal.api.powers.PowerRegistry;
import io.github.gugamm.paranormal.power.Affinity;
import net.minecraft.util.Identifier;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ModPowerRegistry {
	public static final ParanormalPower BLOOD_AFFINITY = PowerRegistry.register(Identifier.of(MODID, "blood_affinity"), new Affinity(ParanormalElement.BLOOD));
	public static final ParanormalPower ENERGY_AFFINITY = PowerRegistry.register(Identifier.of(MODID, "energy_affinity"), new Affinity(ParanormalElement.ENERGY));
	public static final ParanormalPower WISDOM_AFFINITY = PowerRegistry.register(Identifier.of(MODID, "wisdom_affinity"), new Affinity(ParanormalElement.WISDOM));
	public static final ParanormalPower DEATH_AFFINITY = PowerRegistry.register(Identifier.of(MODID, "death_affinity"), new Affinity(ParanormalElement.DEATH));

	public static final ParanormalPower TESTE_A = PowerRegistry.register(Identifier.of(MODID, "teste_a"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_B = PowerRegistry.register(Identifier.of(MODID, "teste_b"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_C = PowerRegistry.register(Identifier.of(MODID, "teste_c"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_D = PowerRegistry.register(Identifier.of(MODID, "teste_d"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_E = PowerRegistry.register(Identifier.of(MODID, "teste_e"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_F = PowerRegistry.register(Identifier.of(MODID, "teste_f"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_G = PowerRegistry.register(Identifier.of(MODID, "teste_g"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_H = PowerRegistry.register(Identifier.of(MODID, "teste_h"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_I = PowerRegistry.register(Identifier.of(MODID, "teste_i"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_J = PowerRegistry.register(Identifier.of(MODID, "teste_j"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_K = PowerRegistry.register(Identifier.of(MODID, "teste_k"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_L = PowerRegistry.register(Identifier.of(MODID, "teste_l"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_M = PowerRegistry.register(Identifier.of(MODID, "teste_m"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_N = PowerRegistry.register(Identifier.of(MODID, "teste_n"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_O = PowerRegistry.register(Identifier.of(MODID, "teste_o"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_P = PowerRegistry.register(Identifier.of(MODID, "teste_p"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_Q = PowerRegistry.register(Identifier.of(MODID, "teste_q"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_R = PowerRegistry.register(Identifier.of(MODID, "teste_r"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));

	public static void init(){
		Paranormal.LOGGER.info("Registering powers for " + MODID);
	}
}
