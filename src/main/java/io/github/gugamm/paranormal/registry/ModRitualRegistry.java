package io.github.gugamm.paranormal.registry;

import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import io.github.gugamm.paranormal.api.rituals.RitualRegistry;
import io.github.gugamm.paranormal.ritual.HealingRitual;
import io.github.gugamm.paranormal.ritual.LightRitual;
import io.github.gugamm.paranormal.ritual.SkinningRitual;
import net.minecraft.util.Identifier;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ModRitualRegistry {
	public static final AbstractRitual HEALING = RitualRegistry.register(Identifier.of(MODID, "healing"),
			new HealingRitual());

	public static final AbstractRitual SKINNING = RitualRegistry.register(Identifier.of(MODID, "skinning"),
			new SkinningRitual());

	public static final AbstractRitual LIGHT = RitualRegistry.register(Identifier.of(MODID, "light"),
			new LightRitual());

	public static void init(){
		Paranormal.LOGGER.info("Registering rituals for " + MODID);
	}
}
