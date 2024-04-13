package io.github.qMartinz.paranormal.client.screen;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.ParanormalAttribute;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerButton;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerSlotButton;
import io.github.qMartinz.paranormal.client.screen.elements.button.PowerTabButton;
import io.github.qMartinz.paranormal.power.Affinity;
import io.github.qMartinz.paranormal.util.CommonText;
import io.github.qMartinz.paranormal.util.MathUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

public class PowersScreen extends Screen {
	public static final Identifier TEXTURE = new Identifier(Paranormal.MODID, "textures/gui/powerscreen.png");
	public final List<PowerButton> powerIcons = new ArrayList<>();
	private final Map<PowerButton, Integer> iconsX = new HashMap<>();
	private final Map<PowerButton, Integer> iconsY = new HashMap<>();
	public final ParanormalElement element;
	public double xOffset;
	public double yOffset;
	public int selectedSlot = 5;

	protected PowersScreen(ParanormalElement element) {
		super(Text.empty());
		this.element = element;
	}

	@Override
	protected void init() {
		int tabWidth = this.width - 41;

		powerIcons.clear();
		this.initContents();

		powerIcons.forEach(button -> iconsX.put(button, button.getX()));
		powerIcons.forEach(button -> iconsY.put(button, button.getY()));
		powerIcons.forEach(this::addDrawableChild);

		this.addSelectableChild(ButtonWidget.builder(Text.empty(), button -> {
			if (xOffset < -350) xOffset = 0;
		}).size(7, 7).position(37, (int) (this.height/2f - 7/2f)).build());

		this.addSelectableChild(ButtonWidget.builder(Text.empty(), button -> {
			if (xOffset > 350) xOffset = 0;
		}).size(7, 7).position(this.width - 13, (int) (this.height/2f - 7/2f)).build());

		this.addSelectableChild(ButtonWidget.builder(Text.empty(), button -> {
			if (yOffset < -200) yOffset = 0;
		}).size(7, 7).position((int) (tabWidth/2f - 7/2f) + 37, 6).build());

		this.addSelectableChild(ButtonWidget.builder(Text.empty(), button -> {
			if (yOffset > 200) yOffset = 0;
		}).size(7, 7).position((int) (tabWidth/2f - 7/2f) + 37, this.height - 13).build());

		for (int i = 0; i < 5; i++){
			this.addDrawableChild(new PowerSlotButton(tabWidth / 2 - 36 + 26*i, height - 46, 22, 22, i));
		}

		this.addDrawableChild(new PowerTabButton(2, 9, 32, 32, ParanormalElement.BLOOD, element == ParanormalElement.BLOOD));
		this.addDrawableChild(new PowerTabButton(2, 43, 32, 32, ParanormalElement.DEATH, element == ParanormalElement.DEATH));
		this.addDrawableChild(new PowerTabButton(2, 77, 32, 32, ParanormalElement.ENERGY, element == ParanormalElement.ENERGY));
		this.addDrawableChild(new PowerTabButton(2, 111, 32, 32, ParanormalElement.WISDOM, element == ParanormalElement.WISDOM));
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		PlayerData playerData = ParanormalClient.playerData;
		MinecraftClient minecraftClient = MinecraftClient.getInstance();

		int tabWidth = this.width - 41;
		guiGraphics.fill(0,0,this.width,this.height,0xFF341c27);

		this.renderContents(guiGraphics, pMouseX, pMouseY, pPartialTick);
		this.drawPowerHierarchyLines(guiGraphics);
		powerIcons.forEach(button -> button.render(guiGraphics, pMouseX, pMouseY, pPartialTick));

		drawBorder(guiGraphics);

		if (xOffset < -350) guiGraphics.drawTexture(TEXTURE, 37, (int) (this.height/2f - 7/2f), 0, 0, 7, 7);
		if (xOffset > 350) guiGraphics.drawTexture(TEXTURE, this.width - 13, (int) (this.height/2f - 7/2f), 7, 7, 7, 7);
		if (yOffset < -200) guiGraphics.drawTexture(TEXTURE, (int) (tabWidth/2f - 7/2f) + 37, 6, 0, 7, 7, 7);
		if (yOffset > 200) guiGraphics.drawTexture(TEXTURE, (int) (tabWidth/2f - 7/2f) + 37, this.height - 13, 7, 0, 7, 7);

		Text label = CommonText.POWER_POINTS;
		String value = String.valueOf(playerData.getPowerPoints());
		guiGraphics.drawText(minecraftClient.textRenderer, label, (int) (tabWidth/2f - minecraftClient.textRenderer.getWidth(label)/2f + 37),
				15, 0xFFFFFF, false);
		guiGraphics.drawText(minecraftClient.textRenderer, value, (int) (tabWidth/2f - minecraftClient.textRenderer.getWidth(value)/2f + 37),
				16 + minecraftClient.textRenderer.fontHeight, 0xFFFFFF, false);

		guiGraphics.drawTexture(TEXTURE, tabWidth/2 - 41, height - 55, 0, 164, 136, 40);
		drawActivePowerSlots(guiGraphics);
		if (selectedSlot < 5) guiGraphics.drawTexture(TEXTURE, tabWidth/2 - 38 + 26*selectedSlot, height - 50, 0, 134, 26, 30);

		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

		for (PowerButton powerButton : powerIcons){
			if (powerButton.isMouseOver(pMouseX, pMouseY)) drawPowerInfo(guiGraphics, powerButton.getPower());
		}

		for (Element element : children()){
			if (element instanceof PowerSlotButton button){
				if (button.getMessage().getString().startsWith("slot_") && button.isMouseOver(pMouseX, pMouseY))
					drawPowerInfo(guiGraphics, playerData.getActivePower(Integer.parseInt(button.getMessage().getString().replaceAll("\\D+",""))));
			}
		}
	}

	private void drawPowerHierarchyLines(GuiGraphics guiGraphics){
		for (PowerButton icon : powerIcons) {
			for (PowerButton icon2 : powerIcons){
				if (icon.getPower().getPowerRequirements().contains(icon2.getPower()) && !(icon2.getPower() instanceof Affinity)){
					int xd = icon.getX() - icon2.getX();
					int yd = icon.getY() - icon2.getY();
					boolean diagonal = Math.abs(xd) > 0 && Math.abs(yd) > 0;
					boolean horizontal = Math.abs(xd) > 0 & Math.abs(yd) == 0;
					boolean vertical = Math.abs(xd) == 0 & Math.abs(yd) > 0;

					if (diagonal){
						int minX = icon.getX() + icon.getWidth()/2;
						int maxX = icon2.getX() + icon2.getWidth()/2;
						int minY = icon.getY();
						int midY = icon.getY() - yd/2;
						int maxY = icon2.getY();

						if (yd < 0) {
							minY += icon.getHeight();
							midY += icon2.getHeight()/2;
						}
						if (yd > 0) {
							maxY += icon2.getHeight();
							midY += icon2.getHeight()/2;
						}

						guiGraphics.fill(minX - 1, minY, minX + 1, midY, 0xFFbf782c);
						guiGraphics.fill(minX - 1 + (xd > 0 ? 2 : 0), midY - 1, maxX + 1 - (xd > 0 ? 2 : 0), midY + 1, 0xFFbf782c);
						guiGraphics.fill(maxX - 1, midY, maxX + 1, maxY, 0xFFbf782c);
					} else {
						int minX = vertical ? (icon.getX() + icon.getWidth()/2) - 1 : icon.getX();
						int minY = horizontal ? (icon.getY() + icon.getHeight()/2) - 1 : icon.getY();
						int maxX = vertical ? minX + 2 : icon2.getX();
						int maxY = horizontal ? minY + 2 : icon2.getY();

						if (xd > 0 && horizontal) maxX += icon.getWidth();
						if (yd > 0 && vertical) maxY += icon.getHeight();
						if (xd < 0 && horizontal) minX += icon2.getWidth();
						if (yd < 0 && vertical) minY += icon2.getHeight();

						guiGraphics.fill(minX, minY, maxX, maxY, 0xFFbf782c);
					}
				}
			}
		}
	}

	private void drawBorder(GuiGraphics guiGraphics){
		guiGraphics.fill(0, 0, 36,this.height, 0xFF602c2c);
		guiGraphics.fill(this.width - 5, 0, this.width,this.height, 0xFF602c2c);
		guiGraphics.fill(0, 0, this.width, 5, 0xFF602c2c);
		guiGraphics.fill(0, this.height - 5, this.width, this.height, 0xFF602c2c);

		guiGraphics.fill(36, 0, 37, this.height, 0xFFde9e41);
		guiGraphics.fill(this.width - 5, 0, this.width - 6, this.height, 0xFFde9e41);
		guiGraphics.fill(0, 5, this.width, 6, 0xFFde9e41);
		guiGraphics.fill(0, this.height - 5, this.width, this.height - 6, 0xFFde9e41);
	}

	private void drawActivePowerSlots(GuiGraphics guiGraphics){
		PlayerData playerData = ParanormalClient.playerData;
		int tabWidth = this.width - 41;
		for (int i = 0; i < 5; i++){
			if (playerData.getActivePower(i) != null){
				guiGraphics.drawTexture(TEXTURE, tabWidth / 2 - 35 + 26 * i, height - 45, 20 * playerData.getActivePower(i).getElement().index, 84, 20, 20);

				Identifier icon = new Identifier(playerData.getActivePower(i).getId().getNamespace(), "textures/paranormal_power/" + playerData.getActivePower(i).getId().getPath() + ".png");
				guiGraphics.drawTexture(icon, tabWidth / 2 - 33 + 26 * i, height - 43, 0, 0, 16, 16, 16, 16);
			}
		}
	}

	private void drawPowerInfo(GuiGraphics guiGraphics, ParanormalPower power){
		if (power != null) {
			MinecraftClient minecraftClient = MinecraftClient.getInstance();
			int tooltipHeight = 4;
			List<OrderedText> splitLines = minecraftClient.textRenderer.wrapLines(power.getDescription(),
					122);

			if (!power.getPowerRequirements().isEmpty() || !Arrays.equals(power.getAttributesRequired(), new int[]{0, 0, 0}) || power.getPexRequired() != 0) {
				List<MutableText> requisites = new ArrayList<>();

				if (!power.getPowerRequirements().isEmpty()) power.getPowerRequirements().forEach(req -> requisites.add(req.getDisplayName().copyContentOnly()));

				if (power.getPexRequired() != 0) {
					int nex = power.getPexRequired() * 5 - (power.getPexRequired() == 20 ? 1 : 0);
					requisites.add(CommonText.PEX_ABBREVIATION.copyContentOnly().append(" " + nex + "%"));
				}
				if (power.getAttributeRequired(0) != 0)
					requisites.add(ParanormalAttribute.STRENGTH.getDisplayName().copyContentOnly().append(" " + power.getAttributeRequired(0)));

				if (power.getAttributeRequired(1) != 0)
					requisites.add(ParanormalAttribute.VIGOR.getDisplayName().copyContentOnly().append(" " + power.getAttributeRequired(1)));

				if (power.getAttributeRequired(2) != 0)
					requisites.add(ParanormalAttribute.PRESENCE.getDisplayName().copyContentOnly().append(" " + power.getAttributeRequired(2)));

				Iterator<MutableText> iterator = requisites.iterator();
				MutableText requisitesComponent = CommonText.REQUISITES.copyContentOnly().append(": ").append(iterator.next());
				iterator.forEachRemaining(req -> requisitesComponent.append(", " + req.getString()));

				splitLines.add(Text.empty().asOrderedText());
				splitLines.addAll(minecraftClient.textRenderer.wrapLines(requisitesComponent, 122));
			}

			tooltipHeight += minecraftClient.textRenderer.fontHeight * splitLines.size();

			int width = 129;
			int height = tooltipHeight + 17;
			int x = this.width - width - 10;
			int y = 10;

			if (power.isActive() && power.getOccultPointsCost() > 0) height += 12;

			guiGraphics.fill(x, y, x + width, y + height, 0xFF602c2c);
			guiGraphics.fill(x - 1, y - 3, x, y + height + 3, 0xFFde9e41);
			guiGraphics.fill(x + width + 1, y - 3, x + width, y + height + 3, 0xFFde9e41);
			guiGraphics.fill(x - 3, y - 1, x + width + 3, y, 0xFFde9e41);
			guiGraphics.fill(x - 3, y + height + 1, x + width + 3, y + height, 0xFFde9e41);

			guiGraphics.fill(x + 2, y + 13, x + width - 2, y + 15 + tooltipHeight, 0xFFde9e41);
			guiGraphics.fill(x + 3, y + 14, x + width - 3, y + 14 + tooltipHeight, 0xFF341c27);

			for (int i = 0; i < splitLines.size(); i++) {
				guiGraphics.drawText(minecraftClient.textRenderer, splitLines.get(i), x + 4, y + 17 + minecraftClient.textRenderer.fontHeight * i,
						0xFFFFFF, false);
			}

			if (power.isActive() && power.getOccultPointsCost() > 0) {
				guiGraphics.drawTexture(TEXTURE, x + width / 2 - (minecraftClient.textRenderer.getWidth(String.valueOf(power.getOccultPointsCost())) + 9) / 2, y + height - 10,
						100, 64, 9, 9);
				guiGraphics.drawText(minecraftClient.textRenderer, Integer.toString(power.getOccultPointsCost()),
						(int) (x + width / 2f - (minecraftClient.textRenderer.getWidth(String.valueOf(power.getOccultPointsCost())) + 9) / 2f + 9), y + height - 10,
						0x9cdb43, false);
			}

			guiGraphics.drawText(minecraftClient.textRenderer, power.getDisplayName(), (int) (x + width / 2f - minecraftClient.textRenderer.getWidth(power.getDisplayName()) / 2f),
					y + 2, 0xFFFFFF, false);
		}
	}

	@Override
	public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
		if (pMouseX > 36 && pMouseX < this.width - 5 &&
				pMouseY > 5 && pMouseY < this.height - 5){
			this.xOffset += pDragX;
			this.yOffset += pDragY;
			this.xOffset = MathUtils.clamp(xOffset, -850, 850);
			this.yOffset = MathUtils.clamp(yOffset, -850, 850);
		}

		return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
	}

	@Override
	public void tick() {
		iconsX.forEach((button, x) -> button.setX((int) (x + xOffset)));
		iconsY.forEach((button, y) -> button.setY((int) (y + yOffset)));
	}

	public PowerButton addPowerIcon(PowerButton icon){
		this.powerIcons.add(icon);
		return icon;
	}

	public void initContents(){}

	public void renderContents(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick){}

	@Override
	public boolean isPauseScreen() {
		return true;
	}
}
