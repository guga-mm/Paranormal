package io.github.qMartinz.paranormal.api.rituals.types;

import net.minecraft.entity.LivingEntity;

public interface SelfRitual {
	boolean useOnSelf(LivingEntity caster);
}
