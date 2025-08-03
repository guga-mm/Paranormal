package io.github.gugamm.paranormal.event;

import io.github.gugamm.paranormal.ParanormalClient;
import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.networking.Payloads;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
	public static final String KEY_CATEGORY = "key.category.paranormal";
	public static final String KEY_RITUAL_HUD = "key.paranormal.ritual_hud";
	public static final String KEY_PREVIOUS_RITUAL = "key.paranormal.previous_ritual";
	public static final String KEY_NEXT_RITUAL = "key.paranormal.next_ritual";

	public static KeyBinding ritualHudKey;
	public static KeyBinding previousRitualKey;
	public static KeyBinding nextRitualKey;

    private static int castCooldownTicks = 0;

	public static void registerKeyInputs() {
		PlayerData playerData = ParanormalClient.clientPlayerData;

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
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
					ClientPlayNetworking.send(new Payloads.CastPayload(ParanormalClient.ritualHud.getRitualIndex()));
					castCooldownTicks = 20;
				}
			}

			if (castCooldownTicks > 0) castCooldownTicks--;
		});
	}

	public static void register() {
		ritualHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				KEY_RITUAL_HUD, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY));

		previousRitualKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				KEY_PREVIOUS_RITUAL, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY));

		nextRitualKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				KEY_NEXT_RITUAL, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY));

		registerKeyInputs();
	}
}
