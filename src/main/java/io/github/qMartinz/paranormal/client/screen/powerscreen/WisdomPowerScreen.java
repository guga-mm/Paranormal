package io.github.qMartinz.paranormal.client.screen.powerscreen;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerButton;
import io.github.qMartinz.paranormal.registry.ModPowerRegistry;

public class WisdomPowerScreen extends PowersScreen {
	public WisdomPowerScreen() {
		super(ParanormalElement.WISDOM);
	}

	@Override
	public void initContents() {
		int tabWidth = this.width - 41;
		addPowerIcon(new PowerButton(tabWidth/2 + 27, height/2 - 10, ModPowerRegistry.WISDOM_AFFINITY));
	}
}
