package io.github.qMartinz.paranormal.api.powers;

import net.minecraft.util.Identifier;

public abstract class AbstractPower {
	public Identifier getId() {
		return PowerRegistry.getId(this);
	}
}
