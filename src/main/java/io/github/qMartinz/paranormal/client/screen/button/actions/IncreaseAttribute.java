package io.github.qMartinz.paranormal.client.screen.button.actions;

import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import net.minecraft.client.gui.widget.ButtonWidget;

public class IncreaseAttribute implements ButtonWidget.PressAction {
	private final int attIndex;
	public IncreaseAttribute(int attIndex){
		this.attIndex = attIndex;
	}
	@Override
	public void onPress(ButtonWidget buttonWidget) {
		PlayerData playerData = ParanormalClient.playerData;

		if (playerData.getAttPoints() > 0){
			playerData.setAttribute(attIndex, playerData.getAttribute(attIndex) + 1);
			playerData.setAttPoints(playerData.getAttPoints() - 1);
			playerData.syncToServer();
		}
	}
}
