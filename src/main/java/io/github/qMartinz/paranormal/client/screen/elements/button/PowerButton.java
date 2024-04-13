package io.github.qMartinz.paranormal.client.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import io.github.qMartinz.paranormal.power.Affinity;
import io.github.qMartinz.paranormal.util.MathUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PowerButton extends ButtonWidget {
	private final ParanormalPower power;
	private final MinecraftClient client = MinecraftClient.getInstance();
	public PowerButton(int x, int y, ParanormalPower power) {
		super(x, y, 20, 20, Text.empty(), new AddPower(power), DEFAULT_NARRATION);
		this.power = power;
	}
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

		PlayerData playerData = ParanormalClient.playerData;

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.disableDepthTest();

		float alpha = 1f;
		boolean powerRequirement = power.getPowerRequirements().stream().allMatch(playerData::hasPower) || power.getPowerRequirements().isEmpty();
		boolean requirements = playerData.getPex() >= power.getPexRequired() &&
				playerData.getAttribute(0) >= power.getAttributeRequired(0) &&
				playerData.getAttribute(1) >= power.getAttributeRequired(1) &&
				playerData.getAttribute(2) >= power.getAttributeRequired(2) &&
				powerRequirement;
		if (power instanceof Affinity && playerData.getPowers().stream().anyMatch(p -> p instanceof Affinity && p != power)) requirements = false;
		if (!requirements) alpha -= 0.5f;

		if (playerData.hasPower(power)) {
			guiGraphics.drawTexture(PowersScreen.TEXTURE, getX() - 5, getY() - 5, 0, 104, 30, 30);

			if (!playerData.getActivePowers().containsValue(this.power)){
				if (this.power.isActive() && this.power.canEquip(client.player) && client.currentScreen instanceof PowersScreen pScreen){
					if (pScreen.selectedSlot < 5) {
						RenderSystem.setShaderColor(1f, 1f, 1f, 0.5f);
						guiGraphics.drawTexture(PowersScreen.TEXTURE, getX() - 4, getY() - 4, 76, 104, 28, 28);
					}
				}
			} else {
				guiGraphics.drawTexture(PowersScreen.TEXTURE, getX() - 4, getY() - 4, 76, 104, 28, 28);
			}

			if (power instanceof Affinity) guiGraphics.drawTexture(PowersScreen.TEXTURE, (getX() + width/2) - 23, (getY() + height/2) - 24, 30, 104, 46, 44);
		}

		RenderSystem.setShaderColor(1f, 1f, 1f, alpha);

		if (playerData.hasPower(power) && this.power.isActive() && this.power.canEquip(client.player) && client.currentScreen instanceof PowersScreen pScreen){
			if (pScreen.selectedSlot < 5) {
				RenderSystem.setShaderColor(1.15f, 1.15f, 1.15f, 1f);
			}
		}

		guiGraphics.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 20 * power.getElement().index, power.isActive() ? 84 : 64, 20, 20);

		Identifier icon = new Identifier(this.power.getId().getNamespace(), "textures/paranormal_power/" + this.power.getId().getPath() + ".png");
		guiGraphics.drawTexture(icon, getX() + 2, getY() + 2, 0, 0, 16, 16, 16, 16);

		if (!playerData.hasPower(power) && power instanceof Affinity && playerData.getPex() >= 10 && playerData.getPowers().stream().noneMatch(p -> p instanceof Affinity && p != power)){
			RenderSystem.setShaderColor(1f, 1f, 1f, MathUtils.Oscillate((int) ((System.currentTimeMillis()/10)%200), 1, 100)/100f);
			guiGraphics.drawTexture(PowersScreen.TEXTURE, (getX() + width/2) - 23, (getY() + height/2) - 24, 30, 104, 46, 44);
		}

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
	}
	public ParanormalPower getPower() {
		return power;
	}

	public static class AddPower implements PressAction {
		private final ParanormalPower power;
		public AddPower(ParanormalPower power){
			this.power = power;
		}
		@Override
		public void onPress(ButtonWidget buttonWidget) {
			PlayerData playerData = ParanormalClient.playerData;
			MinecraftClient client = MinecraftClient.getInstance();

			boolean powerRequirement = power.getPowerRequirements().stream().allMatch(playerData::hasPower) || power.getPowerRequirements().isEmpty();
			boolean requirements = playerData.getPex() >= power.getPexRequired() &&
					playerData.getAttribute(0) >= power.getAttributeRequired(0) &&
					playerData.getAttribute(1) >= power.getAttributeRequired(1) &&
					playerData.getAttribute(2) >= power.getAttributeRequired(2) &&
					powerRequirement;


			if (power instanceof Affinity && playerData.getPowers().stream().anyMatch(p -> p instanceof Affinity)) requirements = false;
			if (requirements && playerData.getPowerPoints() > 0 && !playerData.hasPower(power)){
				playerData.setPowerPoints(playerData.getPowerPoints() - 1);
				playerData.addPower(power);
				// TODO onAdded power trigger
				// TODO AddPower packet
			}

			if (playerData.hasPower(this.power) && this.power.isActive() && this.power.canEquip(client.player) && client.currentScreen instanceof PowersScreen pScreen){
				if (pScreen.selectedSlot < 5) {
					playerData.setActivePower(this.power, pScreen.selectedSlot);
					pScreen.selectedSlot = 5;
				}
			}

			// TODO updatePowers Packet
			playerData.syncToServer();
		}
	}
}
