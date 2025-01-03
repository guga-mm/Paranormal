package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.text.Text;

public class PowerTabButton extends ButtonWidget {
	public final ParanormalElement element;
	public final boolean selected;

	public PowerTabButton(int x, int y, int width, int height, ParanormalElement element, boolean selected) {
		super(x, y, width, height, Text.empty(), new ChangeTab(), DEFAULT_NARRATION);
		this.element = element;
		this.selected = selected;
	}

	@Override
	protected void drawWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		graphics.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 52 + 32 * (element.index - 1), selected ? 176 : 144, 32, 32);

		if (isMouseOver(mouseX, mouseY) && !selected){
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(false);
			RenderSystem.disableDepthTest();

			RenderSystem.setShaderColor(1f, 1f, 1f, 0.5f);
			graphics.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 52 + 32 * (element.index - 1), 176, 32, 32);
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
			RenderSystem.disableBlend();
		}
	}

	public static class ChangeTab implements PressAction {
		@Override
		public void onPress(ButtonWidget buttonWidget) {
			if (buttonWidget instanceof PowerTabButton powerTabButton) {
				PowersScreen screen = new PowersScreen(powerTabButton.element);
				MinecraftClient.getInstance().setScreen(screen);
			}
		}
	}
}
