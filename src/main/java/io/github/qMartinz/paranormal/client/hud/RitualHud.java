package io.github.qMartinz.paranormal.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RitualHud implements HudRenderCallback {
	public static final Identifier TEXTURES = new Identifier(Paranormal.MODID, "textures/gui/overlay.png");

	private boolean visible = false;

	private int ritualIndex = 0;

	@Override
	public void onHudRender(MatrixStack matrices, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerData playerData = ParanormalClient.playerData;
		if (visible && !client.options.hudHidden && client != null && !playerData.rituals.isEmpty()) {
			int width = client.getWindow().getScaledWidth();
			int height = client.getWindow().getScaledHeight();

			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

			RenderSystem.setShaderTexture(0, TEXTURES);
			DrawableHelper.drawTexture(matrices, width/2 - 41, 10, 30, 74, 82, 44);

			if (playerData.rituals.size() > 0) {
				if (ritualIndex > playerData.rituals.size() - 1) ritualIndex = playerData.rituals.size() - 1;

				AbstractRitual ritual = playerData.rituals.stream().toList().get(ritualIndex);
				Identifier symbol = new Identifier(ritual.getId().getNamespace(),
						"textures/ritual_symbol/" + ritual.getId().getPath() + ".png");

				int previousIndex = ritualIndex - 1;
				if (previousIndex < 0) previousIndex = playerData.rituals.size() - 1;

				int nextIndex = ritualIndex + 1;
				if (nextIndex > playerData.rituals.size() - 1) nextIndex = 0;

				RenderSystem.setShaderTexture(0, symbol);
				DrawableHelper.drawTexture(matrices, width / 2 - 16, 16, 0, 0, 64, 64, 128, 128);

				if (playerData.rituals.size() > 1) {
					AbstractRitual ritualPrevious = playerData.rituals.stream().toList().get(previousIndex);
					Identifier symbolPrevious = new Identifier(ritualPrevious.getId().getNamespace(),
							"textures/ritual_symbol/" + ritualPrevious.getId().getPath() + ".png");

					AbstractRitual ritualNext = playerData.rituals.stream().toList().get(nextIndex);
					Identifier symbolNext = new Identifier(ritualNext.getId().getNamespace(),
							"textures/ritual_symbol/" + ritualNext.getId().getPath() + ".png");

					RenderSystem.setShaderTexture(0, symbolPrevious);
					DrawableHelper.drawTexture(matrices, width / 2 - 37, 24, 0, 0, 64, 64, 64, 64);

					RenderSystem.setShaderTexture(0, symbolNext);
					DrawableHelper.drawTexture(matrices, width / 2 + 21, 24, 0, 0, 64, 64, 64, 64);
				} else {
					RenderSystem.setShaderTexture(0, symbol);
					DrawableHelper.drawTexture(matrices, width / 2 - 37, 24, 0, 0, 64, 64, 64, 64);

					RenderSystem.setShaderTexture(0, symbol);
					DrawableHelper.drawTexture(matrices, width / 2 + 21, 24, 0, 0, 64, 64, 64, 64);
				}

				Text ritualName = ritual.getDisplayName();
				DrawableHelper.drawTextWithShadow(matrices, client.textRenderer, ritualName,
						width / 2 - client.textRenderer.getWidth(ritualName) / 2, 46 + client.textRenderer.fontHeight,
						ritual.getElement().getColor());
			}
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setRitualIndex(int ritualIndex) {
		this.ritualIndex = Math.max(0, ritualIndex);
	}

	public int getRitualIndex() {
		return ritualIndex;
	}
}
