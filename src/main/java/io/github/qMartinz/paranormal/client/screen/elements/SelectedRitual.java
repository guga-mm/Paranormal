package io.github.qMartinz.paranormal.client.screen.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SelectedRitual extends ClickableWidget {
	private int ritualIndex = 0;

	public SelectedRitual(int x, int y) {
		super(x, y, 64, 64, Text.translatable("paranormal.screen.attributes_screen.selected_ritual"));
	}

	public void setRitualIndex(int ritualIndex) {
		this.ritualIndex = ritualIndex;
	}

	public int getRitualIndex() {
		return ritualIndex;
	}

	@Override
	public void drawWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerData playerData = ParanormalClient.playerData;

		if (!playerData.rituals.isEmpty()){
			AbstractRitual ritual = playerData.rituals.stream().toList().get(ritualIndex);

			Identifier symbol = new Identifier(ritual.getId().getNamespace(),
					"textures/ritual_symbol/" + ritual.getId().getPath() + ".png");

			RenderSystem.enableBlend();
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.defaultBlendFunc();

			guiGraphics.drawTexture(symbol, this.getX() + width/2 - 32, this.getY() + height/2 - 32, 0, 0, 64, 64, 256, 256);

			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.disableBlend();

			guiGraphics.fill(this.getX() + width/2 - 9/2, this.getY() + height - 9, this.getX() + width/2 + 9/2, this.getY() + height, 0xFF602c2c);
			guiGraphics.fill(this.getX() + width/2 - 9/2 - 1, this.getY() + height - 11, this.getX() + width/2 - 9/2, this.getY() + height + 2, 0xFFde9e41);
			guiGraphics.fill(this.getX() + width/2 + 9/2 + 1, this.getY() + height - 11, this.getX() + width/2 + 9/2, this.getY() + height + 2, 0xFFde9e41);
			guiGraphics.fill(this.getX() + width/2 - 9/2 - 2, this.getY() + height - 10, this.getX() + width/2 + 9/2 + 2, this.getY() + height - 9, 0xFFde9e41);
			guiGraphics.fill(this.getX() + width/2 - 9/2 - 2, this.getY() + height + 1, this.getX() + width/2 + 9/2 + 2, this.getY() + height, 0xFFde9e41);

			guiGraphics.drawText(client.textRenderer, String.valueOf(getRitualIndex() + 1),
					(int) (this.getX() + width/2f - client.textRenderer.getWidth(String.valueOf(getRitualIndex() + 1))/2f + 0.5f), this.getY() + height - client.textRenderer.fontHeight + 1,
					0xFFde9e41, false);
		}
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {

	}
}
