package io.github.qMartinz.paranormal.power;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;

public class Affinity extends ParanormalPower {

	public Affinity(ParanormalElement element) {
		super(element, false, 0, 10, new int[]{0, 0, 0});
	}
}
