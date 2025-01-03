package io.github.qMartinz.paranormal.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.ParanormalAttribute;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.client.screen.elements.SelectedRitual;
import io.github.qMartinz.paranormal.client.screen.elements.button.AttributeButton;
import io.github.qMartinz.paranormal.client.screen.elements.button.ChangeScreenButton;
import io.github.qMartinz.paranormal.client.screen.elements.button.SelectRitualButtons;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class AttributesScreen extends Screen {
	public static final Identifier TEXTURES = Identifier.of(Paranormal.MODID, "textures/gui/pexscreen.png");
	public static final Identifier RITUAL_TAB_BG = Identifier.of(Paranormal.MODID, "textures/gui/ritual_tab.png");
	private final int screenHeight = 217;
	public AttributesScreen() {
		super(Text.translatable("paranormal.screen.attributes_screen"));
	}
	@Override
	protected void init(){
		int screenX = (this.width/2) - 266/2;
		int tabX = screenX + 110;
		int screenY = (this.height/2) - (this.screenHeight/2);

		addDrawableSelectableElement(new AttributeButton(screenX + 33, screenY + 12, ParanormalAttribute.STRENGTH));
		addDrawableSelectableElement(new AttributeButton(screenX + 33, screenY + 75, ParanormalAttribute.VIGOR));
		addDrawableSelectableElement(new AttributeButton(screenX + 33, screenY + 138, ParanormalAttribute.PRESENCE));
		addDrawableSelectableElement(new SelectedRitual(tabX + 46, screenY + 7));

		addDrawableSelectableElement(new SelectRitualButtons.PreviousRitual(tabX + 44, screenY + 75, 8, 8));
		addDrawableSelectableElement(new SelectRitualButtons.NextRitual(tabX + 104, screenY + 75, 8, 8));

		addDrawableSelectableElement(new ChangeScreenButton(screenX + 39, screenY + 195, 20, 20, new PowersScreen(ParanormalElement.BLOOD)));
	}
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);

		int screenX = (this.width/2) - 266/2;
		int screenY = (this.height/2) - (this.screenHeight/2);

		PlayerData playerData = ParanormalClient.playerData;

		String label = Text.translatable("paranormal.screen.attributes_screen.attPoints").getString();
		String value = String.valueOf(playerData.getAttPoints());
		graphics.drawText(client.textRenderer, label, (int) (screenX + 98/2f - textRenderer.getWidth(label)/2f), screenY - 2 - textRenderer.fontHeight*2, 0xFFFFFF, false);
		graphics.drawText(client.textRenderer, value, (int) (screenX + 98/2f - textRenderer.getWidth(value)/2f), screenY - 1 - textRenderer.fontHeight, 0xFFFFFF, false);
	}

	@Override
	protected void renderMenuBackground(GuiGraphics graphics) {
		int screenX = (this.width/2) - 266/2;
		int screenY = (this.height/2) - (this.screenHeight/2);

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.disableDepthTest();

		graphics.drawTexture(TEXTURES, screenX + 98/2 - 27, screenY + 174, 174, 0, 54, 63);

		graphics.drawTexture(TEXTURES, screenX, screenY, 0, 0, 98, this.screenHeight);

		this.renderRitualTab(graphics);

		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
	}

	public void renderRitualTab(GuiGraphics guiGraphics){
		PlayerData playerData = ParanormalClient.playerData;

		int tabX = (this.width/2) - 266/2 + 110;
		int tabY = (this.height/2) - (this.screenHeight/2);

		guiGraphics.drawTexture(RITUAL_TAB_BG, tabX + 156/2 - 80, tabY + screenHeight/2 - 90, 0, 0, 160, 160, 160, 160);

		children().stream().filter(w -> w instanceof SelectedRitual).findFirst().ifPresent(widget -> {
			if (widget instanceof SelectedRitual ritualWidget && playerData.rituals.size() > 0) {
				AbstractRitual ritual = playerData.rituals.stream().toList().get(ritualWidget.getRitualIndex());
				Text unsplitText = ritual.getDescription();
				int tabHeight = 112;

				List<StringVisitable> splitLines = client.textRenderer.getTextHandler().wrapLines(
						unsplitText,
						148, Style.EMPTY.withFormatting(Formatting.WHITE));
				tabHeight += 2 + client.textRenderer.fontHeight * splitLines.size();

				guiGraphics.fill(tabX, tabY, tabX + 156, tabY + tabHeight, 0xFF602c2c);
				guiGraphics.fill(tabX - 1, tabY - 3, tabX, tabY + tabHeight + 3, 0xFFde9e41);
				guiGraphics.fill(tabX + 157, tabY - 3, tabX + 156, tabY + tabHeight + 3, 0xFFde9e41);
				guiGraphics.fill(tabX - 3, tabY - 1, tabX + 159, tabY, 0xFFde9e41);
				guiGraphics.fill(tabX - 3, tabY + tabHeight + 1, tabX + 159, tabY + tabHeight, 0xFFde9e41);

				guiGraphics.fill(tabX + 1, tabY + 98, tabX + 155, tabY + 100 + client.textRenderer.fontHeight * splitLines.size(), 0xFFde9e41);
				guiGraphics.fill(tabX + 2, tabY + 99, tabX + 154, tabY + 99 + client.textRenderer.fontHeight * splitLines.size(), 0xFF341c27);

				RenderSystem.setShaderTexture(0, TEXTURES);
				guiGraphics.fill(tabX + 40, tabY + 1, 98, 0, 76, 86);
				guiGraphics.fill(tabX + 1, tabY + tabHeight - 11, 98, 86, 9, 9);

				String text = playerData.rituals.size() + "/" + playerData.getRitualSlots();
				guiGraphics.drawText(client.textRenderer, text, (int) (tabX + 79 - client.textRenderer.getWidth(text)/2f), tabY + 76, 0xFFFFFF, false);

				guiGraphics.drawText(client.textRenderer, ritual.getDisplayName().asOrderedText(), (int) (tabX + 79 - client.textRenderer.getWidth(ritual.getDisplayName())/2f), tabY + 88, 0xFFFFFF, false);

				text = String.valueOf((int) ritual.getOccultPointsCost());
				guiGraphics.drawText(client.textRenderer, text, tabX + 11, tabY + tabHeight - 10, 0xFFFFFF, false);

				guiGraphics.drawText(client.textRenderer, ritual.getElement().getDisplayName(), tabX + 156 - client.textRenderer.getWidth(ritual.getElement().getDisplayName()) - 2, tabY + tabHeight - 10, 0xFFFFFF, false);

				for (int i = 0; i < splitLines.size(); i++) {
					guiGraphics.drawText(client.textRenderer, splitLines.get(i).getString(), tabX + 4, tabY + 100 + client.textRenderer.fontHeight * i, 0xFFFFFF, false);
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
