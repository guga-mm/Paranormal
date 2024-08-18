package io.github.qMartinz.paranormal.client.screen;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.client.event.ClientEvents;
import io.github.qMartinz.paranormal.client.screen.elements.button.ChangeScreenButton;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerSlotButton;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerTabButton;
import io.github.qMartinz.paranormal.util.MathUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PowersScreen extends Screen {
	public static final Identifier TEXTURE = new Identifier(Paranormal.MODID, "textures/gui/powerscreen.png");
	private final int guiWidth = 255;
	private final int guiHeight = 173;
	private final List<PowerSlotButton> slots = new ArrayList<>();
	private final List<ParanormalPower> powers = new ArrayList<>();
	public final ParanormalElement element;

	public PowersScreen(ParanormalElement element) {
		super(Text.empty());
		this.element = element;
	}

	@Override
	protected void init() {
		int x = width/2 - guiWidth/2;
		int y = height/2 - guiHeight/2;

		this.addDrawableChild(new PowerTabButton(x + 10, y + 10, 32, 32, ParanormalElement.BLOOD, element == ParanormalElement.BLOOD));
		this.addDrawableChild(new PowerTabButton(x + 10, y + 45, 32, 32, ParanormalElement.DEATH, element == ParanormalElement.DEATH));
		this.addDrawableChild(new PowerTabButton(x + 10, y + 80, 32, 32, ParanormalElement.ENERGY, element == ParanormalElement.ENERGY));
		this.addDrawableChild(new PowerTabButton(x + 10, y + 115, 32, 32, ParanormalElement.WISDOM, element == ParanormalElement.WISDOM));

		this.addDrawableChild(new ChangeScreenButton(x + 16, y + 151, 20, 20, new AttributesScreen()));

		ClientEvents.POWER_SCREEN_INIT.invoker().powerScreenInit(this);

		for (int i = 0; i < 5; i++) {
			PowerSlotButton newSlot = new PowerSlotButton(x + 61, y + 5 + (27 * i));
			slots.add(newSlot);
			this.addDrawableChild(newSlot);
		}

		this.addDrawableChild(new PowerScrollBar(width/2 - guiWidth/2 + 234, height/2 - guiHeight/2 + 6));
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		PlayerData playerData = ParanormalClient.playerData;
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		int x = width/2 - guiWidth/2;
		int y = height/2 - guiHeight/2;

		renderBackground(guiGraphics);

		guiGraphics.drawTexture(TEXTURE, x, y, 0, 0, 52, 173);
		guiGraphics.drawTexture(TEXTURE, x + 56, y, 52, 0, 199, 144);

		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

		for (Element e : children()){
			if (e instanceof PowerSlotButton slotButton && slotButton.isMouseOver(pMouseX, pMouseY) && slotButton.getPower() != null){
				ParanormalPower power = slotButton.getPower();
				List<OrderedText> splitLines = client.textRenderer.wrapLines(power.getDescription(), 122);
				List<OrderedText> lines = Lists.newArrayList();
				lines.addAll(client.textRenderer.wrapLines(power.getDisplayName(), 122));
				lines.add(Text.empty().asOrderedText());
				lines.addAll(splitLines);

				int tooltipHeight = client.textRenderer.fontHeight * lines.size() + 6;
				int width = 129;

				guiGraphics.fill(pMouseX + 4, pMouseY + 4, pMouseX + 4 + width, pMouseY + 4 + tooltipHeight, 0xFF602c2c);
				guiGraphics.fill(pMouseX + 4, pMouseY + 4, pMouseX + 4 + width, pMouseY + 4 + 1, 0xFFde9e41);
				guiGraphics.fill(pMouseX + 4, pMouseY + 4, pMouseX + 4 + 1, pMouseY + 4 + tooltipHeight, 0xFFde9e41);
				guiGraphics.fill(pMouseX + 4, pMouseY + 5 + tooltipHeight, pMouseX + 4 + width, pMouseY + 4 + tooltipHeight, 0xFFde9e41);
				guiGraphics.fill(pMouseX + 4 + width, pMouseY + 4, pMouseX + 5 + width, pMouseY + 4 + tooltipHeight, 0xFFde9e41);

				for (int i = 0; i < lines.size(); i++) {
					guiGraphics.drawText(client.textRenderer, lines.get(i), pMouseX + 7, pMouseY + 7 + client.textRenderer.fontHeight * i,
							0xFFFFFF, false);
				}
			}
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	protected void clearAndInit() {
		slots.clear();
		powers.clear();
		super.clearAndInit();
	}

	public void addPower(ParanormalPower power) {
		powers.add(power);
	}

	public void addPowerList(Collection<ParanormalPower> powerList) {
		powers.addAll(powerList);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		int x = width/2 - guiWidth/2;
		int y = height/2 - guiHeight/2;

		for (Element e : children()){
			if (e instanceof PowerScrollBar scrollBar && mouseX > x + 56 && mouseX < x + 251 && mouseY > y && mouseY < y + 144) {
				scrollBar.scrollPosition = MathUtils.clamp(scrollBar.scrollPosition - ((int) amount), 0, scrollBar.getMaxScrollPosition());
			}
		}
		return super.mouseScrolled(mouseX, mouseY, amount);
	}

	public static class PowerScrollBar extends ClickableWidget implements Drawable, Element {
		private final PowersScreen powersScreen = (PowersScreen) MinecraftClient.getInstance().currentScreen;
		private int scrollPosition = 0;
		public PowerScrollBar(int x, int y) {
			super(x, y, 15, 132, Text.empty());
		}

		@Override
		public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
			super.render(graphics, mouseX, mouseY, delta);
			if (powersScreen != null && !powersScreen.powers.isEmpty()) {
				int size = powersScreen.powers.size();
				int scroll = this.getScrollPosition();
				int maxScroll = this.getMaxScrollPosition();

				// int a = scrollPosition % (maxScrollPosition / (size / 5));
				int b = (int) ((scroll / (maxScroll / (Math.ceil(size / 5d))))*5);

				graphics.drawTexture(PowersScreen.TEXTURE, getX(), getY() + scrollPosition, 226, 144, 15, 19);

				setSlots(Math.min(b, size - 1));
			}
		}

		@Override
		protected void drawWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

		}

		public int getScrollPosition() {
			return scrollPosition;
		}

		public int getMaxScrollPosition() {
			return height - 19;
		}

		private void setSlots(int startingIndex){
			// TODO clean
			if (powersScreen != null && !powersScreen.powers.isEmpty()) for (int i = 0; i < 5; i++) {
				if (powersScreen.powers.size() > startingIndex + i) {
					powersScreen.slots.get(i).setPower(powersScreen.powers.get(startingIndex + i));
				} else {
					powersScreen.slots.get(i).setPower(null);
				}
			}
		}

		@Override
		protected void updateNarration(NarrationMessageBuilder builder) {}

		@Override
		public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
			return super.mouseScrolled(mouseX, mouseY, amount);
		}

		@Override
		public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
			// if (mouseX >= getX() && mouseX <= getX() + width && mouseY >= getY() && mouseY <= getY() + height) {
				// scrollPosition = MathUtils.clamp(scrollPosition + ((int) deltaY * 2), 0, getMaxScrollPosition());
			// }

			if (mouseY < (double)this.getY()) {
				scrollPosition = 0;
			} else if (mouseY > (double)(this.getY() + this.height)) {
				scrollPosition = getMaxScrollPosition();
			} else {
				int i = 19;
				double d = Math.max(1, getMaxScrollPosition() / (this.height - i));
				scrollPosition = (int) MathUtils.clamp((scrollPosition + deltaY * d), 0, getMaxScrollPosition());
			}

			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
	}
}
