package io.github.gugamm.paranormal.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.ParanormalClient;
import io.github.gugamm.paranormal.api.PlayerData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class PexHud implements HudRenderCallback {
	public static final Identifier TEXTURES = Identifier.of(Paranormal.MODID, "textures/gui/overlay.png");

	@Override
	public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (!client.options.hudHidden && client != null) {
			int width = client.getWindow().getScaledWidth();
			int height = client.getWindow().getScaledHeight();

			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

			drawContext.drawTexture(TEXTURES, width - 95, height - 9, 92, 5, 3, 24, 92, 5, 256, 256);

			PlayerData playerData = ParanormalClient.clientPlayerData;
			float nextLvlXP = (playerData.getPex() + 1) * 50;
			float barFilled = playerData.getXp() / nextLvlXP * 90;
			drawContext.drawTexture(TEXTURES, width - 95, height - 9, (int) barFilled, 5,  3, 29, (int) barFilled, 5, 256, 256);

			String s = playerData.getNexPercentage() + "%";
			drawContext.drawTextWithShadow(client.textRenderer, s,
					width - (client.textRenderer.getWidth(s) + 96),
					height - (client.textRenderer.fontHeight + 2),
					0xFFFFFFFF);

			if (client.interactionManager.hasStatusBars()){
				int amount = (int) Math.floor(playerData.getOccultPoints());
				int max = (int) Math.floor(playerData.getMaxOccultPoints());

				int x = width / 2 + 91;
				int y = height - (client.player.getAir() < 300 ? 57 : 48);
				int i3 = (int) Math.ceil(max / 10f);
				int j2 = Math.max(10 - (i3 - 2), 3);

				for (int i = 0; i < max; i++){
					int j1 = i / 10;
					int k1 = i % 10;
					int l1 = x - k1 * 8 - 10;
					int i2 = y - j1 * j2;

					drawContext.drawTexture(TEXTURES, l1, i2, 9, 9, 125, 0, 9, 9, 256, 256);
				}

				for (int i = amount - 1; i >= 0; --i) {
					int j1 = i / 10;
					int k1 = i % 10;
					int l1 = x - k1 * 8 - 10;
					int i2 = y - j1 * j2;

					drawContext.drawTexture(TEXTURES, l1, i2, 9, 9, 116, 0, 9, 9, 256, 256);
				}
			}
		}
	}
}
