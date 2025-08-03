package io.github.gugamm.paranormal.screen.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.gugamm.paranormal.ParanormalClient;
import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
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
	public void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerData playerData = ParanormalClient.clientPlayerData;

		if (!playerData.rituals.isEmpty()){
			AbstractRitual ritual = playerData.rituals.stream().toList().get(ritualIndex);

			Identifier symbol = Identifier.of(ritual.getId().getNamespace(),
					"textures/ritual_symbol/" + ritual.getId().getPath() + ".png");

			RenderSystem.enableBlend();
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.defaultBlendFunc();

			drawContext.drawTexture(symbol, this.getX() + width/2 - 32, this.getY() + height/2 - 32, 0, 0, 64, 64, 256, 256);

			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.disableBlend();

			drawContext.fill(this.getX() + width/2 - 9/2, this.getY() + height - 9, this.getX() + width/2 + 9/2, this.getY() + height, 0xFF602c2c);
			drawContext.fill(this.getX() + width/2 - 9/2 - 1, this.getY() + height - 11, this.getX() + width/2 - 9/2, this.getY() + height + 2, 0xFFde9e41);
			drawContext.fill(this.getX() + width/2 + 9/2 + 1, this.getY() + height - 11, this.getX() + width/2 + 9/2, this.getY() + height + 2, 0xFFde9e41);
			drawContext.fill(this.getX() + width/2 - 9/2 - 2, this.getY() + height - 10, this.getX() + width/2 + 9/2 + 2, this.getY() + height - 9, 0xFFde9e41);
			drawContext.fill(this.getX() + width/2 - 9/2 - 2, this.getY() + height + 1, this.getX() + width/2 + 9/2 + 2, this.getY() + height, 0xFFde9e41);

			drawContext.drawText(client.textRenderer, String.valueOf(getRitualIndex() + 1),
					(int) (this.getX() + width/2f - client.textRenderer.getWidth(String.valueOf(getRitualIndex() + 1))/2f + 0.5f), this.getY() + height - client.textRenderer.fontHeight + 1,
					0xFFde9e41, false);
		}
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {

	}
}
