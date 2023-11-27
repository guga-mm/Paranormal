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
	public void onHudRender(MatrixStack matrixStack, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (!client.options.hudHidden && client != null) {
			int width = client.getWindow().getScaledWidth();
			int height = client.getWindow().getScaledHeight();

			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, TEXTURES);

			DrawableHelper.drawTexture(matrixStack, width - 95, height - 9, 3, 24, 92, 5);

			PlayerData playerData = ParanormalClient.playerData;
			float nextLvlXP = (playerData.getPex() + 1) * 50;
			float barFilled = playerData.getXp() / nextLvlXP * 90;
			DrawableHelper.drawTexture(matrixStack, width - 95, height - 9, 3, 29, (int) barFilled, 5);

			String s = playerData.getNexPercentage() + "%";
			client.textRenderer.drawWithShadow(matrixStack, s,
					width - (client.textRenderer.getWidth(s) + 96),
					height - (client.textRenderer.fontHeight + 2),
					0xFFFFFFFF);
		}
	}
}
