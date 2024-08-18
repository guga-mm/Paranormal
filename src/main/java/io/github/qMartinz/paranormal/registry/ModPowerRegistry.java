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

	public static final ParanormalPower TESTE_A = PowerRegistry.register(new Identifier(MODID, "teste_a"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_B = PowerRegistry.register(new Identifier(MODID, "teste_b"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_C = PowerRegistry.register(new Identifier(MODID, "teste_c"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_D = PowerRegistry.register(new Identifier(MODID, "teste_d"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_E = PowerRegistry.register(new Identifier(MODID, "teste_e"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_F = PowerRegistry.register(new Identifier(MODID, "teste_f"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_G = PowerRegistry.register(new Identifier(MODID, "teste_g"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_H = PowerRegistry.register(new Identifier(MODID, "teste_h"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_I = PowerRegistry.register(new Identifier(MODID, "teste_i"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_J = PowerRegistry.register(new Identifier(MODID, "teste_j"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_K = PowerRegistry.register(new Identifier(MODID, "teste_k"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_L = PowerRegistry.register(new Identifier(MODID, "teste_l"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_M = PowerRegistry.register(new Identifier(MODID, "teste_m"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_N = PowerRegistry.register(new Identifier(MODID, "teste_n"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_O = PowerRegistry.register(new Identifier(MODID, "teste_o"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_P = PowerRegistry.register(new Identifier(MODID, "teste_p"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_Q = PowerRegistry.register(new Identifier(MODID, "teste_q"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));
	public static final ParanormalPower TESTE_R = PowerRegistry.register(new Identifier(MODID, "teste_r"), new ParanormalPower(ParanormalElement.BLOOD, 0, 0, 0, 0));

	public static void init(){
		Paranormal.LOGGER.info("Registering powers for " + MODID);
	}
}
