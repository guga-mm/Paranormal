package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class AttributeButton extends ButtonWidget {
	private final int attIndex;

	public AttributeButton(int pX, int pY, int attIndex) {
		super(pX, pY, 32, 32, Text.translatable("paranormal.screen.attributes_screen.increase_attribute_" + attIndex),
				new IncreaseAttribute(attIndex), DEFAULT_NARRATION);
		this.attIndex = attIndex;
	}

	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		RenderSystem.setShaderTexture(0, AttributesScreen.TEXTURES);

		int u = 32 * this.attIndex;
		if (isMouseOver(mouseX, mouseY)) RenderSystem.setShaderColor(1.4f, 1.4f, 1.4f, 1f);
		drawTexture(stack, getX(), getY(), u, 217, width, height);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		int color = 0xFFFFFF;

		int level = ParanormalClient.playerData.getAttribute(attIndex);
		MinecraftClient client = MinecraftClient.getInstance();
		drawStringWithShadow(stack, client.textRenderer, Integer.toString(level), getX() + 16 - client.textRenderer.getWidth(Integer.toString(level)) / 2, getY() + height + 1, color);

		String text = Text.translatable("paranormal.screen.attributes_screen.attribute_" + attIndex).getString();
		drawStringWithShadow(stack, client.textRenderer, text, getX() + 16 - client.textRenderer.getWidth(text) / 2, getY() + height + 2 + client.textRenderer.fontHeight, color);
	}

	public static class IncreaseAttribute implements PressAction {
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
				if (attIndex == 2) {
					playerData.setMaxOccultPoints(playerData.getMaxOccultPoints() + 1);
					playerData.setOccultPoints(playerData.getOccultPoints() + 1);
				}

				playerData.syncToServer();
			}
		}
	}
}
