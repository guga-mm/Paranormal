package io.github.qMartinz.paranormal.client.event;

import com.mojang.blaze3d.platform.InputUtil;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.networking.ModMessages;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBind;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class KeyInputHandler {
	public static final String KEY_CATEGORY = "key.category.paranormal";
	public static final String KEY_RITUAL_HUD = "key.paranormal.ritual_hud";
	public static final String KEY_PREVIOUS_RITUAL = "key.paranormal.previous_ritual";
	public static final String KEY_NEXT_RITUAL = "key.paranormal.next_ritual";

	public static KeyBind ritualHudKey;
	public static KeyBind previousRitualKey;
	public static KeyBind nextRitualKey;

    private static int castCooldownTicks = 0;

	public static void registerKeyInputs() {
		PlayerData playerData = ParanormalClient.playerData;

		ClientTickEvents.END.register(client -> {
			if (ritualHudKey.wasPressed() && !playerData.rituals.isEmpty()) {
				ParanormalClient.ritualHud.setVisible(!ParanormalClient.ritualHud.isVisible());
			}

			if (nextRitualKey.wasPressed()) {
				int ritualIndex = ParanormalClient.ritualHud.getRitualIndex() + 1;
				if (ritualIndex > playerData.rituals.size() - 1) ritualIndex = 0;
				ParanormalClient.ritualHud.setRitualIndex(ritualIndex);
			}

			if (previousRitualKey.wasPressed()) {
				int ritualIndex = ParanormalClient.ritualHud.getRitualIndex() - 1;
				if (ritualIndex < 0) ritualIndex = playerData.rituals.size() - 1;
				ParanormalClient.ritualHud.setRitualIndex(ritualIndex);
			}

			if (client.options.useKey.isPressed() && castCooldownTicks <= 0) {
				if (ParanormalClient.ritualHud.isVisible() && !playerData.rituals.isEmpty()){
					PacketByteBuf data = PacketByteBufs.create();
					data.writeInt(ParanormalClient.ritualHud.getRitualIndex());
					ClientPlayNetworking.send(ModMessages.CAST_RITUAL_ID, data);
					castCooldownTicks = 20;
				}
			}

			if (castCooldownTicks > 0) castCooldownTicks--;
		});
	}

	public static void register() {
		ritualHudKey = KeyBindingHelper.registerKeyBinding(new KeyBind(
				KEY_RITUAL_HUD, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY));

		previousRitualKey = KeyBindingHelper.registerKeyBinding(new KeyBind(
				KEY_PREVIOUS_RITUAL, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY));

		nextRitualKey = KeyBindingHelper.registerKeyBinding(new KeyBind(
				KEY_NEXT_RITUAL, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY));

		registerKeyInputs();
	}
}
