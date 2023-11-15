package io.github.qMartinz.paranormal.api.rituals;

import io.github.qMartinz.paranormal.api.powers.PowerRegistry;
import net.minecraft.util.Identifier;

public abstract class AbstractRitual {
	public Identifier getId() {
		return RitualRegistry.getId(this);
	}
}
