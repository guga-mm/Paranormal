package io.github.gugamm.paranormal.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.gugamm.paranormal.screen.AttributesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ChangeScreenButton extends ButtonWidget {
	public ChangeScreenButton(int x, int y, int width, int height, Screen screen) {
		super(x, y, width, height, Text.empty(), (b) ->
				MinecraftClient.getInstance().setScreen(screen), DEFAULT_NARRATION_SUPPLIER);
	}

	@Override
	protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		if (isMouseOver(mouseX, mouseY)){
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(false);
			RenderSystem.disableDepthTest();

			drawContext.drawTexture(AttributesScreen.TEXTURES, getX(), getY(), 20, 20, 228, 0, 20, 20, 256, 256);

			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
			RenderSystem.disableBlend();
		}
	}
}
