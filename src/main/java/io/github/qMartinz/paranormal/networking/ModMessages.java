package io.github.qMartinz.paranormal.networking;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.events.ParanormalEvents;
import io.github.qMartinz.paranormal.api.powers.PowerRegistry;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
	public static final Identifier PEX_SYNC_ID = Identifier.of(Paranormal.MODID, "pex_sync");
	public static final Identifier ATTRIBUTES_SYNC_ID = Identifier.of(Paranormal.MODID, "attributes_sync");
	public static final Identifier POWERS_SYNC_ID = Identifier.of(Paranormal.MODID, "powers_sync");
	public static final Identifier RITUALS_SYNC_ID = Identifier.of(Paranormal.MODID, "rituals_sync");
	public static final Identifier OCCULT_POINTS_SYNC_ID = Identifier.of(Paranormal.MODID, "occult_points_sync");
	public static final Identifier CAST_ID = Identifier.of(Paranormal.MODID, "cast_ritual");
	public static final Identifier ADDED_POWER_TRIGGER_ID = Identifier.of(Paranormal.MODID, "on_added_power_trigger");

	public static void registerC2SPackets() {
		ServerPlayNetworking.registerGlobalReceiver(Payloads.PexPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(context.player());
				playerData.pex = payload.pex();
				playerData.xp = payload.xp();
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(Payloads.AttributesPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(context.player());
				playerData.attPoints = payload.attPoints();
				playerData.attributes = new int[]{payload.str(), payload.pre(), payload.vig()};
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(Payloads.PowersPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(context.player());
				playerData.powerPoints = payload.powerPoints();
				playerData.deserializePowers(payload.powers());
				playerData.deserializeAffinities(payload.affinities());
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(Payloads.RitualsPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(context.player());
				playerData.ritualSlots = payload.ritualSlots();
				playerData.deserializeRituals(payload.rituals());
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(Payloads.OccultPointsPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(context.player());
				playerData.maxOccultPoints = payload.maxOccultPoints();
				playerData.occultPoints = payload.occultPoints();
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(Payloads.CastPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PlayerData playerData = StateSaverAndLoader.getPlayerState(context.player());
				playerData.getRitual(payload.index()).onCast(context.player());
			});
		});


		ServerPlayNetworking.registerGlobalReceiver(Payloads.AddedPowerTriggerPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PowerRegistry.getPower(payload.powerId()).ifPresent(power -> {
					power.onAdded(context.player());
					ParanormalEvents.POWER_ADDED.invoker().powerAdded(power, context.player());
				});
			});
		});
	}

	public static void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(Payloads.PexPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				PlayerData playerData = ParanormalClient.playerData;
				playerData.pex = payload.pex();
				playerData.xp = payload.xp();
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.AttributesPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				PlayerData playerData = ParanormalClient.playerData;
				playerData.attPoints = payload.attPoints();
				playerData.attributes = new int[]{payload.str(), payload.pre(), payload.vig()};
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.PowersPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				PlayerData playerData = ParanormalClient.playerData;
				playerData.powerPoints = payload.powerPoints();
				playerData.deserializePowers(payload.powers());
				playerData.deserializeAffinities(payload.affinities());
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.RitualsPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				PlayerData playerData = ParanormalClient.playerData;
				playerData.ritualSlots = payload.ritualSlots();
				playerData.deserializeRituals(payload.rituals());
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.OccultPointsPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				PlayerData playerData = ParanormalClient.playerData;
				playerData.maxOccultPoints = payload.maxOccultPoints();
				playerData.occultPoints = payload.occultPoints();
			});
		});
	}
}
