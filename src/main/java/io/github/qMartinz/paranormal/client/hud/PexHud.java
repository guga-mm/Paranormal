package io.github.qMartinz.paranormal.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.ColorUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class PexHud implements HudRenderCallback {
	public static final Identifier TEXTURES = new Identifier(Paranormal.MODID, "textures/gui/overlay.png");
	@Override
	public void onHudRender(MatrixStack matrices, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (!client.options.hudHidden && client != null) {
			int width = client.getWindow().getScaledWidth();
			int height = client.getWindow().getScaledHeight();

			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, TEXTURES);

			DrawableHelper.drawTexture(matrices, width - 95, height - 9, 3, 24, 92, 5);

			PlayerData playerData = ParanormalClient.playerData;
			float nextLvlXP = (playerData.getPex() + 1) * 50;
			float barFilled = playerData.getXp() / nextLvlXP * 90;
			DrawableHelper.drawTexture(matrices, width - 95, height - 9, 3, 29, (int) barFilled, 5);

			String s = playerData.getNexPercentage() + "%";
			client.textRenderer.drawWithShadow(matrices, s,
					width - (client.textRenderer.getWidth(s) + 96),
					height - (client.textRenderer.fontHeight + 2),
					0xFFFFFFFF);

			if (client.interactionManager.hasStatusBars()){
				int amount = playerData.getOccultPoints();
				int max = playerData.getMaxOccultPoints();

				int x = width / 2 + 91;
				int y = height - (client.player.getAir() < 300 ? 57 : 48);
				int i3 = (int) Math.ceil(max / 10f);
				int j2 = Math.max(10 - (i3 - 2), 3);

				for (int i = 0; i < max; i++){
					int j1 = i / 10;
					int k1 = i % 10;
					int l1 = x - k1 * 8 - 10;
					int i2 = y - j1 * j2;

					RenderSystem.setShaderTexture(0, TEXTURES);
					DrawableHelper.drawTexture(matrices, l1, i2, 125, 0, 9, 9);
				}

				for (int i = amount - 1; i >= 0; --i) {
					int j1 = i / 10;
					int k1 = i % 10;
					int l1 = x - k1 * 8 - 10;
					int i2 = y - j1 * j2;

					RenderSystem.setShaderTexture(0, TEXTURES);
					DrawableHelper.drawTexture(matrices, l1, i2, 116, 0, 9, 9);
				}
			}
		}
	}
}
