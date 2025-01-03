package io.github.qMartinz.paranormal.client.event;

import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class ClientEvents {

	/**
	 * Evento engatilhado quando a tela de poderes é iniciada para registro de poderes no GUI.
	 */
	public static final Event<PowerScreenInit> POWER_SCREEN_INIT = EventFactory.createArrayBacked(PowerScreenInit.class,
			(listeners) -> (screen) -> {
				for (PowerScreenInit listener : listeners) {
					listener.powerScreenInit(screen);
				}
			});

	public interface PowerScreenInit {
		void powerScreenInit(PowersScreen screen);
	}
}
