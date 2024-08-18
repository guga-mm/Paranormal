package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ChangeScreenButton extends ButtonWidget {
	public ChangeScreenButton(int x, int y, int width, int height, Screen screen) {
		super(x, y, width, height, Text.empty(), (b) ->
				MinecraftClient.getInstance().setScreen(screen), DEFAULT_NARRATION);
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
