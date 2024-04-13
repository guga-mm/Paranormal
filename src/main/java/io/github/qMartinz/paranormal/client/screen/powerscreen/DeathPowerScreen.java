package io.github.qMartinz.paranormal.client.screen.powerscreen;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerButton;
import io.github.qMartinz.paranormal.registry.ModPowerRegistry;

public class DeathPowerScreen extends PowersScreen {
	public DeathPowerScreen() {
		super(ParanormalElement.DEATH);
	}

	@Override
	public void initContents() {
		int tabWidth = this.width - 41;
		addPowerIcon(new PowerButton(tabWidth/2 + 27, height/2 - 10, ModPowerRegistry.DEATH_AFFINITY));
	}
}
