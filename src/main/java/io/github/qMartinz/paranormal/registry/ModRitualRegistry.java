package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.RitualRegistry;
import net.minecraft.util.Identifier;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ModRitualRegistry {
	public static final AbstractRitual HEALING = RitualRegistry.register(new Identifier(MODID, "healing"),
			new AbstractRitual(ParanormalElement.DEATH, 1, 2, 7.5d, true) {});

	public static final AbstractRitual SKINNING = RitualRegistry.register(new Identifier(MODID, "skinning"),
			new AbstractRitual(ParanormalElement.BLOOD, 1, 2, 7.5d, true) {});

	public static void init(){
		Paranormal.LOGGER.info("Registering rituals for " + MODID);
	}
}
