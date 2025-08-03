package io.github.gugamm.paranormal.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.gugamm.paranormal.ParanormalClient;
import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.screen.AttributesScreen;
import io.github.gugamm.paranormal.screen.elements.SelectedRitual;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class SelectRitualButtons {
	public static class NextRitual extends ButtonWidget {
		public NextRitual(int x, int y, int width, int height) {
			super(x, y, width, height, Text.translatable("paranormal.screen.attributes_screen.selected_ritual.next"), new NextRitualAction(), DEFAULT_NARRATION_SUPPLIER);
		}

		@Override
		protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
			PlayerData playerData = ParanormalClient.clientPlayerData;
			if (!playerData.rituals.isEmpty()){
				if (isMouseOver(mouseX, mouseY)) RenderSystem.setShaderColor(1.5f, 1.5f, 1.5f, 1f);
				drawContext.drawGuiTexture(AttributesScreen.TEXTURES, 8, 7, 174, 63, getX(), getY(), 8, 7);
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			}
		}

		public static class NextRitualAction implements PressAction {
			@Override
			public void onPress(ButtonWidget buttonWidget) {
				PlayerData playerData = ParanormalClient.clientPlayerData;
				SelectedRitual ritualWidget = (SelectedRitual) MinecraftClient.getInstance().currentScreen.children().stream().filter(w -> w instanceof SelectedRitual).findFirst().get();

				ritualWidget.setRitualIndex(playerData.rituals.size() - 1 >= ritualWidget.getRitualIndex() + 1 ? ritualWidget.getRitualIndex() + 1 : 0);
			}
		}
	}

	public static class PreviousRitual extends ButtonWidget {
		public PreviousRitual(int x, int y, int width, int height) {
			super(x, y, width, height, Text.translatable("paranormal.screen.attributes_screen.selected_ritual.previous"), new PreviousRitualAction(), DEFAULT_NARRATION_SUPPLIER);
		}

		@Override
		protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
			PlayerData playerData = ParanormalClient.clientPlayerData;
			if (!playerData.rituals.isEmpty()){
				if (isMouseOver(mouseX, mouseY)) RenderSystem.setShaderColor(1.5f, 1.5f, 1.5f, 1f);
				drawContext.drawGuiTexture(AttributesScreen.TEXTURES,8, 7, 174, 70, getX(), getY(), 8, 7);
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			}
		}

		public static class PreviousRitualAction implements PressAction {
			@Override
			public void onPress(ButtonWidget buttonWidget) {
				PlayerData playerData = ParanormalClient.clientPlayerData;
				SelectedRitual ritualWidget = (SelectedRitual) MinecraftClient.getInstance().currentScreen.children().stream().filter(w -> w instanceof SelectedRitual).findFirst().get();

				ritualWidget.setRitualIndex(ritualWidget.getRitualIndex() - 1 >= 0 ? ritualWidget.getRitualIndex() - 1 : playerData.rituals.size() - 1);
			}
		}
	}
}
