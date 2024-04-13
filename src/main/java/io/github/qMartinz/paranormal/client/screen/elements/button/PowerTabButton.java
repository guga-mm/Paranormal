package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import io.github.qMartinz.paranormal.client.screen.powerscreen.BloodPowerScreen;
import io.github.qMartinz.paranormal.client.screen.powerscreen.DeathPowerScreen;
import io.github.qMartinz.paranormal.client.screen.powerscreen.EnergyPowerScreen;
import io.github.qMartinz.paranormal.client.screen.powerscreen.WisdomPowerScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.ButtonWidget;
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
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		graphics.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 14 + 32 * (element.index - 1), selected ? 32 : 0, 32, 32);

		if (isMouseOver(mouseX, mouseY) && !selected){
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(false);
			RenderSystem.disableDepthTest();

			RenderSystem.setShaderColor(1f, 1f, 1f, 0.5f);
			graphics.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 14 + 32 * (element.index - 1), 32, 32, 32);
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
				PowersScreen screen = switch (powerTabButton.element) {
					case DEATH -> new DeathPowerScreen();
					case ENERGY -> new EnergyPowerScreen();
					case WISDOM -> new WisdomPowerScreen();
					default -> new BloodPowerScreen();
				};

				MinecraftClient.getInstance().setScreen(screen);
			}
		}
	}
}
