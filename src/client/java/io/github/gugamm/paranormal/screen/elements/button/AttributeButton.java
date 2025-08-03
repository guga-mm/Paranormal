package io.github.gugamm.paranormal.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.gugamm.paranormal.ParanormalClient;
import io.github.gugamm.paranormal.api.ClientPlayerData;
import io.github.gugamm.paranormal.api.ParanormalAttribute;
import io.github.gugamm.paranormal.screen.AttributesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class AttributeButton extends ButtonWidget {
	private final ParanormalAttribute attribute;

	public AttributeButton(int pX, int pY, ParanormalAttribute attribute) {
		super(pX, pY, 32, 32, attribute.getDisplayName(),
				new IncreaseAttribute(attribute), DEFAULT_NARRATION_SUPPLIER);
		this.attribute = attribute;
	}

	@Override
	protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		int u = 32 * this.attribute.index;
		if (isMouseOver(mouseX, mouseY)) RenderSystem.setShaderColor(1.4f, 1.4f, 1.4f, 1f);
		drawContext.drawTexture(AttributesScreen.TEXTURES, getX(), getY(), 32, 32, u, 217, 32, 32, 256, 256);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		int color = 0xFFFFFF;

		int level = ParanormalClient.clientPlayerData.getAttribute(attribute);
		MinecraftClient client = MinecraftClient.getInstance();
		drawContext.drawTextWithShadow(client.textRenderer, Integer.toString(level), getX() + 16 - client.textRenderer.getWidth(Integer.toString(level)) / 2, getY() + height + 1, color);

		Text text = attribute.getDisplayName();
		drawContext.drawTextWithShadow(client.textRenderer, text, getX() + 16 - client.textRenderer.getWidth(text) / 2, getY() + height + 2 + client.textRenderer.fontHeight, color);
	}

	public static class IncreaseAttribute implements PressAction {
		private final ParanormalAttribute attribute;
		public IncreaseAttribute(ParanormalAttribute attribute){
			this.attribute = attribute;
		}

		@Override
		public void onPress(ButtonWidget buttonWidget) {
			ClientPlayerData playerData = ParanormalClient.clientPlayerData;

			if (playerData.getAttPoints() > 0){
				playerData.setAttribute(attribute, playerData.getAttribute(attribute) + 1);
				playerData.setAttPoints(playerData.getAttPoints() - 1);
				if (attribute == ParanormalAttribute.PRESENCE) {
					playerData.setMaxOccultPoints(playerData.getMaxOccultPoints() + 1d);
					playerData.setOccultPoints(playerData.getOccultPoints() + 1d);
				}

				playerData.syncAttributesToServer();
			}
		}
	}
}
