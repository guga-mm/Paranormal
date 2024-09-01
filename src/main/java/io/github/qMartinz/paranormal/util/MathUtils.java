package io.github.qMartinz.paranormal.util;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.registry.ModDamageTypes;
import net.minecraft.entity.LivingEntity;

public class MathUtils {
	/**
	 * Returns the given value if between the lower and the upper bound. If the value is less than the lower bound,
	 * returns the lower bound. If the value is greater than the upper bound, returns the upper bound.
	 * @param pValue The value that is clamped.
	 * @param pMin The lower bound for the clamp.
	 * @param pMax The upper bound for the clamp.
	 */
	public static double clamp(double pValue, double pMin, double pMax) {
		return Math.max(pMin, Math.min(pMax, pValue));
	}

	public static float clamp(float pValue, float pMin, float pMax) {
		return Math.max(pMin, Math.min(pMax, pValue));
	}

	public static int clamp(int pValue, int pMin, int pMax) {
		return Math.max(pMin, Math.min(pMax, pValue));
	}

	public static int Oscillate(int input, int min, int max) {
		int range = max - min ;
		return min + Math.abs(((input + range) % (range * 2)) - range);
	}

	public static float calcParanormalDmg(float dmg, LivingEntity target, ParanormalElement element){
		return dmg * (ModDamageTypes.isEntityWeakTo(target, element.getDamage(target.getDamageSources())) ? 2f : 1f)
				/ (ModDamageTypes.isEntityResistant(target, element.getDamage(target.getDamageSources())) ? 2f : 1f);
	}
}
