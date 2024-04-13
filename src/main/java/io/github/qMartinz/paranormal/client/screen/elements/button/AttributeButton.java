package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.ParanormalAttribute;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class AttributeButton extends ButtonWidget {
	private final ParanormalAttribute attribute;

	public AttributeButton(int pX, int pY, ParanormalAttribute attribute) {
		super(pX, pY, 32, 32, attribute.getDisplayName(),
				new IncreaseAttribute(attribute), DEFAULT_NARRATION);
		this.attribute = attribute;
	}

	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		int u = 32 * this.attribute.index;
		if (isMouseOver(mouseX, mouseY)) RenderSystem.setShaderColor(1.4f, 1.4f, 1.4f, 1f);
		guiGraphics.drawTexture(AttributesScreen.TEXTURES, getX(), getY(), u, 217, width, height);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		int color = 0xFFFFFF;

		int level = ParanormalClient.playerData.getAttribute(attribute);
		MinecraftClient client = MinecraftClient.getInstance();
		guiGraphics.drawShadowedText(client.textRenderer, Integer.toString(level), getX() + 16 - client.textRenderer.getWidth(Integer.toString(level)) / 2, getY() + height + 1, color);

		Text text = attribute.getDisplayName();
		guiGraphics.drawShadowedText(client.textRenderer, text, getX() + 16 - client.textRenderer.getWidth(text) / 2, getY() + height + 2 + client.textRenderer.fontHeight, color);
	}

	public static class IncreaseAttribute implements PressAction {
		private final ParanormalAttribute attribute;
		public IncreaseAttribute(ParanormalAttribute attribute){
			this.attribute = attribute;
		}
		@Override
		public void onPress(ButtonWidget buttonWidget) {
			PlayerData playerData = ParanormalClient.playerData;

			if (playerData.getAttPoints() > 0){
				playerData.setAttribute(attribute, playerData.getAttribute(attribute) + 1);
				playerData.setAttPoints(playerData.getAttPoints() - 1);
				if (attribute == ParanormalAttribute.PRESENCE) {
					playerData.setMaxOccultPoints(playerData.getMaxOccultPoints() + 1d);
					playerData.setOccultPoints(playerData.getOccultPoints() + 1d);
				}

				playerData.syncToServer();
			}
		}
	}
}
