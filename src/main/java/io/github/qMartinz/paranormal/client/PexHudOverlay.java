package io.github.qMartinz.paranormal.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.util.IEntityDataSaver;
import io.github.qMartinz.paranormal.util.PexData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.ColorUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class PexHudOverlay implements HudRenderCallback {
	public static final Identifier TEXTURES = new Identifier(Paranormal.MODID, "textures/gui/overlay.png");
	@Override
	public void onHudRender(MatrixStack matrixStack, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerEntity player = client.player;
		if (!client.options.hudHidden) {
			int width = client.getWindow().getScaledWidth();
			int height = client.getWindow().getScaledHeight();

			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, TEXTURES);

			DrawableHelper.drawTexture(matrixStack, width - 95, height - 9, 3, 24, 92, 5);

			int nextLvlXP = (PexData.getPex(((IEntityDataSaver) player)) + 1) * 50;
			int barFilled = (PexData.getXp(((IEntityDataSaver) player)) / nextLvlXP) * 90;
			DrawableHelper.drawTexture(matrixStack, width - 95, height - 9, 3, 29, barFilled, 5);

			String s = PexData.getPexPercentage(((IEntityDataSaver) player)) + "%";
			client.textRenderer.drawWithShadow(matrixStack, s,
					width - (client.textRenderer.getWidth(s) + 96),
					height - (client.textRenderer.fontHeight + 2),
					ColorUtil.ARGB32.getArgb(255, 255, 255, 255));
		}
	}
}
