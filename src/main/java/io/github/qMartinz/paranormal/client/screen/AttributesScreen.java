package io.github.qMartinz.paranormal.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.client.screen.elements.SelectedRitual;
import io.github.qMartinz.paranormal.client.screen.elements.button.AttributeButton;
import io.github.qMartinz.paranormal.client.screen.elements.button.SelectRitualButtons;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class AttributesScreen extends Screen {
	public static final Identifier TEXTURES = new Identifier(Paranormal.MODID, "textures/gui/pexscreen.png");
	public static final Identifier RITUAL_TAB_BG = new Identifier(Paranormal.MODID, "textures/gui/ritual_tab.png");
	private final int screenHeight = 217;
	public AttributesScreen() {
		super(Text.translatable("paranormal.screen.attributes_screen"));
	}
	@Override
	protected void init(){
		int screenX = (this.width/2) - 266/2;
		int tabX = screenX + 110;
		int screenY = (this.height/2) - (this.screenHeight/2);

		addDrawableChild(new AttributeButton(screenX + 33, screenY + 12, 0));
		addDrawableChild(new AttributeButton(screenX + 33, screenY + 75, 1));
		addDrawableChild(new AttributeButton(screenX + 33, screenY + 138, 2));
		addDrawableChild(new SelectedRitual(tabX + 46, screenY + 7));

		addDrawableChild(new SelectRitualButtons.PreviousRitual(tabX + 44, screenY + 75, 8, 8));
		addDrawableChild(new SelectRitualButtons.NextRitual(tabX + 104, screenY + 75, 8, 8));

		//addDrawableChild(new PowerScreenButton(screenX + 39, screenY + 195, 20, 20));
	}
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		PlayerData playerData = ParanormalClient.playerData;

		renderBackground(stack);

		int screenX = (this.width/2) - 266/2;
		int screenY = (this.height/2) - (this.screenHeight/2);

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.disableDepthTest();
		RenderSystem.setShaderTexture(0, TEXTURES);

		drawTexture(stack, screenX + 98/2 - 27, screenY + 174, 174, 0, 54, 63);

		drawTexture(stack, screenX, screenY, 0, 0, 98, this.screenHeight);

		String label = Text.translatable("paranormal.screen.attributes_screen.attPoints").getString();
		String value = String.valueOf(playerData.getAttPoints());
		textRenderer.draw(stack, label, screenX + 98/2f - textRenderer.getWidth(label)/2f, screenY - 2 - textRenderer.fontHeight*2, 0xFFFFFF);
		textRenderer.draw(stack, value, screenX + 98/2f - textRenderer.getWidth(value)/2f, screenY - 1 - textRenderer.fontHeight, 0xFFFFFF);

		this.renderRitualTab(stack);

		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();

		super.render(stack, mouseX, mouseY, partialTicks);
	}

	public void renderRitualTab(MatrixStack stack){
		PlayerData playerData = ParanormalClient.playerData;

		int tabX = (this.width/2) - 266/2 + 110;
		int tabY = (this.height/2) - (this.screenHeight/2);

		RenderSystem.setShaderTexture(0, RITUAL_TAB_BG);
		drawTexture(stack, tabX + 156/2 - 80, tabY + screenHeight/2 - 90, 0, 0, 160, 160, 160, 160);

		children().stream().filter(w -> w instanceof SelectedRitual).findFirst().ifPresent(widget -> {
			if (widget instanceof SelectedRitual ritualWidget && playerData.rituals.size() > 0) {
				AbstractRitual ritual = playerData.rituals.stream().toList().get(ritualWidget.getRitualIndex());
				Text unsplitText = ritual.getDescription();
				int tabHeight = 112;

				List<StringVisitable> splitLines = client.textRenderer.getTextHandler().wrapLines(
						unsplitText,
						148, Style.EMPTY.withFormatting(Formatting.WHITE));
				tabHeight += 2 + client.textRenderer.fontHeight * splitLines.size();

				fill(stack, tabX, tabY, tabX + 156, tabY + tabHeight, 0xFF602c2c);
				fill(stack, tabX - 1, tabY - 3, tabX, tabY + tabHeight + 3, 0xFFde9e41);
				fill(stack, tabX + 157, tabY - 3, tabX + 156, tabY + tabHeight + 3, 0xFFde9e41);
				fill(stack, tabX - 3, tabY - 1, tabX + 159, tabY, 0xFFde9e41);
				fill(stack, tabX - 3, tabY + tabHeight + 1, tabX + 159, tabY + tabHeight, 0xFFde9e41);

				fill(stack, tabX + 1, tabY + 98, tabX + 155, tabY + 100 + client.textRenderer.fontHeight * splitLines.size(), 0xFFde9e41);
				fill(stack, tabX + 2, tabY + 99, tabX + 154, tabY + 99 + client.textRenderer.fontHeight * splitLines.size(), 0xFF341c27);

				RenderSystem.setShaderTexture(0, TEXTURES);
				drawTexture(stack, tabX + 40, tabY + 1, 98, 0, 76, 86);
				drawTexture(stack, tabX + 1, tabY + tabHeight - 11, 98, 86, 9, 9);

				String text = playerData.rituals.size() + "/" + playerData.getRitualSlots();
				client.textRenderer.draw(stack, text, tabX + 79 - client.textRenderer.getWidth(text)/2f, tabY + 76, 0xFFFFFF);

				client.textRenderer.draw(stack, ritual.getDisplayName(), tabX + 79 - client.textRenderer.getWidth(ritual.getDisplayName())/2f, tabY + 88, 0xFFFFFF);

				text = String.valueOf(ritual.getEffortCost());
				client.textRenderer.draw(stack, text, tabX + 11, tabY + tabHeight - 10, 0xFFFFFF);

				client.textRenderer.draw(stack, ritual.getElement().getDisplayName(), tabX + 156 - client.textRenderer.getWidth(ritual.getElement().getDisplayName()) - 2, tabY + tabHeight - 10, 0xFFFFFF);

				for (int i = 0; i < splitLines.size(); i++) {
					client.textRenderer.draw(stack, splitLines.get(i).getString(), tabX + 4, tabY + 100 + client.textRenderer.fontHeight * i, 0xFFFFFF);
				}
			}
		});
	}

	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
}
