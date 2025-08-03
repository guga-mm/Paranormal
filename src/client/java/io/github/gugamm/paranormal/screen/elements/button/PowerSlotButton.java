package io.github.gugamm.paranormal.screen.elements.button;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.gugamm.paranormal.ParanormalClient;
import io.github.gugamm.paranormal.api.ClientPlayerData;
import io.github.gugamm.paranormal.api.ParanormalAttribute;
import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.api.powers.ParanormalPower;
import io.github.gugamm.paranormal.screen.PowersScreen;
import io.github.gugamm.paranormal.networking.Payloads;
import io.github.gugamm.paranormal.power.Affinity;
import io.github.gugamm.paranormal.util.CommonText;
import io.github.gugamm.paranormal.util.MathUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PowerSlotButton extends ButtonWidget {
	private final MinecraftClient client = MinecraftClient.getInstance();

	private ParanormalPower power;

	public PowerSlotButton(int x, int y) {
		super(x, y, 170, 26, Text.empty(), new AddPower(), DEFAULT_NARRATION_SUPPLIER);
	}

	@Override
	protected void renderWidget(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		if (power != null) {
			PlayerData playerData = ParanormalClient.clientPlayerData;

			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.depthMask(false);
			RenderSystem.disableDepthTest();

			drawContext.drawTexture(PowersScreen.TEXTURE, getX(), getY(), 170, 26, 57, 5, 170,  26, 256, 256);

			float alpha = 1f;
			boolean powerRequirement = power.getPowerRequirements().stream().allMatch(playerData::hasPower) || power.getPowerRequirements().isEmpty();
			boolean requirements = playerData.getPex() >= power.getPexRequired() &&
				playerData.getAttribute(ParanormalAttribute.STRENGTH) >= power.getAttributeRequired(ParanormalAttribute.STRENGTH) &&
				playerData.getAttribute(ParanormalAttribute.VIGOR) >= power.getAttributeRequired(ParanormalAttribute.VIGOR) &&
				playerData.getAttribute(ParanormalAttribute.PRESENCE) >= power.getAttributeRequired(ParanormalAttribute.PRESENCE) &&
				powerRequirement;
			if (!playerData.hasPower(power)) {
				alpha = requirements ? MathUtils.Oscillate((int) ((System.currentTimeMillis() / 10) % 200), 50, 100) / 100f : 0.5f;
			}

			if (playerData.hasPower(power) && !playerData.hasAffinity(power) &&
				playerData.getPowers().stream().anyMatch(p -> p instanceof Affinity a && a.getElement() == power.getElement())) {
				RenderSystem.setShaderColor(1f, 1f, 1f, MathUtils.Oscillate((int) ((System.currentTimeMillis() / 10) % 200), 1, 100) / 100f);
				drawContext.drawTexture(PowersScreen.TEXTURE, getX() + 1, getY() + 1, 24, 24, 120, 208, 24, 24, 256, 256);
			}

			RenderSystem.setShaderColor(1f, 1f, 1f, alpha);

			drawContext.drawTexture(PowersScreen.TEXTURE, getX() + 1, getY() + 1, 24, 24, 24 * power.getElement().index, playerData.hasAffinity(power) ? 208 : 232, 24, 24, 256, 256);

			Identifier icon = Identifier.of(this.power.getId().getNamespace(), "textures/paranormal_power/" + this.power.getId().getPath() + ".png");
			drawContext.drawTexture(icon, getX() + 5, getY() + 5, 0, 0, 16, 16, 16, 16);

			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

			drawContext.drawText(client.textRenderer, power.getDisplayName(), getX() + 29, getY() + 3, new Color(255, 255, 255).getRGB(), true);

			if (!power.getPowerRequirements().isEmpty() || Arrays.stream(power.getAttributesRequired()).anyMatch(i -> i > 0) || power.getPexRequired() != 0) {
				List<MutableText> requisites = new ArrayList<>();

				if (!power.getPowerRequirements().isEmpty())
					power.getPowerRequirements().forEach(req -> requisites.add(req.getDisplayName().copyContentOnly()));

				if (power.getPexRequired() != 0) {
					int nex = power.getPexRequired() * 5 - (power.getPexRequired() == 20 ? 1 : 0);
					requisites.add(CommonText.PEX_ABBREVIATION.copyContentOnly().append(" " + nex + "%"));
				}
				if (power.getAttributeRequired(ParanormalAttribute.STRENGTH) != 0)
					requisites.add(ParanormalAttribute.STRENGTH.getDisplayName().copyContentOnly().append(" " + power.getAttributeRequired(ParanormalAttribute.STRENGTH)));

				if (power.getAttributeRequired(ParanormalAttribute.VIGOR) != 0)
					requisites.add(ParanormalAttribute.VIGOR.getDisplayName().copyContentOnly().append(" " + power.getAttributeRequired(ParanormalAttribute.VIGOR)));

				if (power.getAttributeRequired(ParanormalAttribute.PRESENCE) != 0)
					requisites.add(ParanormalAttribute.PRESENCE.getDisplayName().copyContentOnly().append(" " + power.getAttributeRequired(ParanormalAttribute.PRESENCE)));

				Iterator<MutableText> iterator = requisites.iterator();
				MutableText requisitesComponent = CommonText.REQUISITES.copyContentOnly().append(": ").append(iterator.next());
				iterator.forEachRemaining(req -> requisitesComponent.append(", " + req.getString()));

				drawContext.drawText(client.textRenderer, requisitesComponent, getX() + 29, getY() + 5 + client.textRenderer.fontHeight, new Color(148, 148, 148).getRGB(), false);
			}

			RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
			RenderSystem.disableBlend();
		}
	}

	public ParanormalPower getPower() {
		return power;
	}

	public void setPower(ParanormalPower power) {
		this.power = power;
	}

	public static class AddPower implements PressAction {
		@Override
		public void onPress(ButtonWidget buttonWidget) {
			if (buttonWidget instanceof PowerSlotButton powerSlotButton) {
				ParanormalPower power = powerSlotButton.power;
				if (power == null) return;
				ClientPlayerData playerData = ParanormalClient.clientPlayerData;

				boolean powerRequirement = power.getPowerRequirements().stream().allMatch(playerData::hasPower) || power.getPowerRequirements().isEmpty();
				boolean requirements = playerData.getPex() >= power.getPexRequired() &&
						playerData.getAttribute(ParanormalAttribute.STRENGTH) >= power.getAttributeRequired(ParanormalAttribute.STRENGTH) &&
						playerData.getAttribute(ParanormalAttribute.VIGOR) >= power.getAttributeRequired(ParanormalAttribute.VIGOR) &&
						playerData.getAttribute(ParanormalAttribute.PRESENCE) >= power.getAttributeRequired(ParanormalAttribute.PRESENCE) &&
						powerRequirement;


				if (power instanceof Affinity && playerData.getPowers().stream().anyMatch(p -> p instanceof Affinity))
					requirements = false;
				if (!playerData.hasPower(power)) {
					if (requirements && playerData.getPowerPoints() > 0) {
						playerData.setPowerPoints(playerData.getPowerPoints() - 1);
						playerData.addPower(power);

						ClientPlayNetworking.send(new Payloads.AddedPowerTriggerPayload(power.getId()));
					}
				} else if (!playerData.hasAffinity(power)) {
					if (playerData.getPowerPoints() > 0) {
						playerData.setPowerPoints(playerData.getPowerPoints() - 1);
						playerData.addAffinity(power);
					}
				}

				playerData.syncPowersToServer();
			}
		}
	}
}
