package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.client.screen.powerscreen.BloodPowerScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PowersScreenButton extends ButtonWidget {
	public PowersScreenButton(int x, int y, int width, int height) {
		super(x, y, width, height, Text.empty(), (b) -> {
			MinecraftClient.getInstance().setScreen(new BloodPowerScreen());
		}, DEFAULT_NARRATION);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		if (isMouseOver(mouseX, mouseY)){
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(false);
			RenderSystem.disableDepthTest();

			graphics.drawTexture(AttributesScreen.TEXTURES, getX(), getY(), 228, 0, 20, 20);

			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
			RenderSystem.disableBlend();
		}
	}
}
