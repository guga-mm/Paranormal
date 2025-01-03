package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.client.screen.elements.SelectedRitual;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.text.Text;

public class SelectRitualButtons {
	public static class NextRitual extends ButtonWidget {
		public NextRitual(int x, int y, int width, int height) {
			super(x, y, width, height, Text.translatable("paranormal.screen.attributes_screen.selected_ritual.next"), new NextRitualAction(), DEFAULT_NARRATION);
		}

		@Override
		protected void drawWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
			PlayerData playerData = ParanormalClient.playerData;
			if (!playerData.rituals.isEmpty()){
				if (isMouseOver(mouseX, mouseY)) RenderSystem.setShaderColor(1.5f, 1.5f, 1.5f, 1f);
				graphics.drawTexture(AttributesScreen.TEXTURES, getX(), getY(), 174, 63, 8, 7);
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			}
		}

		public static class NextRitualAction implements PressAction {
			@Override
			public void onPress(ButtonWidget buttonWidget) {
				PlayerData playerData = ParanormalClient.playerData;
				SelectedRitual ritualWidget = (SelectedRitual) MinecraftClient.getInstance().currentScreen.children().stream().filter(w -> w instanceof SelectedRitual).findFirst().get();

				ritualWidget.setRitualIndex(playerData.rituals.size() - 1 >= ritualWidget.getRitualIndex() + 1 ? ritualWidget.getRitualIndex() + 1 : 0);
			}
		}
	}

	public static class PreviousRitual extends ButtonWidget {
		public PreviousRitual(int x, int y, int width, int height) {
			super(x, y, width, height, Text.translatable("paranormal.screen.attributes_screen.selected_ritual.previous"), new PreviousRitualAction(), DEFAULT_NARRATION);
		}

		@Override
		protected void drawWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
			PlayerData playerData = ParanormalClient.playerData;
			if (!playerData.rituals.isEmpty()){
				if (isMouseOver(mouseX, mouseY)) RenderSystem.setShaderColor(1.5f, 1.5f, 1.5f, 1f);
				graphics.drawTexture(AttributesScreen.TEXTURES, getX(), getY(), 174, 70, 8, 7);
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			}
		}

		public static class PreviousRitualAction implements PressAction {
			@Override
			public void onPress(ButtonWidget buttonWidget) {
				PlayerData playerData = ParanormalClient.playerData;
				SelectedRitual ritualWidget = (SelectedRitual) MinecraftClient.getInstance().currentScreen.children().stream().filter(w -> w instanceof SelectedRitual).findFirst().get();

				ritualWidget.setRitualIndex(ritualWidget.getRitualIndex() - 1 >= 0 ? ritualWidget.getRitualIndex() - 1 : playerData.rituals.size() - 1);
			}
		}
	}
}
