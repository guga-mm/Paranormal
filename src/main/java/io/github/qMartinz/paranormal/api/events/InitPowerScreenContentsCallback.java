package io.github.qMartinz.paranormal.api.events;

import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import org.quiltmc.qsl.base.api.event.Event;

public interface InitPowerScreenContentsCallback {
	Event<InitPowerScreenContentsCallback> EVENT = Event.create(InitPowerScreenContentsCallback.class,
			(listeners) -> (screen) -> {
				for (InitPowerScreenContentsCallback listener : listeners) {
					listener.initContents(screen);
				}
			});

	void initContents(PowersScreen screen);
}
