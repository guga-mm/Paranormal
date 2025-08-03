package io.github.gugamm.paranormal.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.gugamm.paranormal.api.ParanormalElement;
import io.github.gugamm.paranormal.screen.PowersScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PowerTabButton extends ButtonWidget {
	public final ParanormalElement element;
	public final boolean selected;

	public PowerTabButton(int x, int y, int width, int height, ParanormalElement element, boolean selected) {
		super(x, y, width, height, Text.empty(), new ChangeTab(), DEFAULT_NARRATION_SUPPLIER);
		this.element = element;
		this.selected = selected;
	}

	@Override
	protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		drawContext.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 32, 32, 52 + 32 * (element.index - 1), selected ? 176 : 144, 32, 32, 256, 256);

		if (isMouseOver(mouseX, mouseY) && !selected){
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(false);
			RenderSystem.disableDepthTest();

			RenderSystem.setShaderColor(1f, 1f, 1f, 0.5f);
			drawContext.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 32, 32, 52 + 32 * (element.index - 1), 176, 32, 32, 256, 256);
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
