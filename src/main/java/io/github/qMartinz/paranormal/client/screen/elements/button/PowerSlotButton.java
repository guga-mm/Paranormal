package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PowerSlotButton extends ButtonWidget {
	private final int slot;

	public PowerSlotButton(int x, int y, int width, int height, int slot) {
		super(x, y, width, height, Text.literal("slot_" + slot), new ChangeSlot(), DEFAULT_NARRATION);
		this.slot = slot;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		if (isMouseOver(mouseX, mouseY)){
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(false);
			RenderSystem.disableDepthTest();

			RenderSystem.setShaderColor(1f, 1f, 1f, 0.5f);
			graphics.drawTexture(PowersScreen.TEXTURE, getX() - 2, getY() - 4, 0, 134, 26, 30);
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
			RenderSystem.disableBlend();
		}
	}

	public static class ChangeSlot implements PressAction {
		@Override
		public void onPress(ButtonWidget buttonWidget) {
			if (MinecraftClient.getInstance().currentScreen instanceof PowersScreen pScreen && buttonWidget instanceof PowerSlotButton slotButton){
				if (pScreen.selectedSlot != slotButton.slot) {
					pScreen.selectedSlot = slotButton.slot;
				} else pScreen.selectedSlot = 6;
			}
		}
	}
}
