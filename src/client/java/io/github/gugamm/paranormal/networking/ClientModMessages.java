package io.github.gugamm.paranormal.networking;

import io.github.gugamm.paranormal.ParanormalClient;
import io.github.gugamm.paranormal.api.ClientPlayerData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientModMessages {
	public static void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(Payloads.PexPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
				clientPlayerData.pex = payload.pex();
				clientPlayerData.xp = payload.xp();
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.AttributesPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
				clientPlayerData.attPoints = payload.attPoints();
				clientPlayerData.attributes = new int[]{payload.str(), payload.pre(), payload.vig()};
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.PowersPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
				clientPlayerData.powerPoints = payload.powerPoints();
				clientPlayerData.deserializePowers(payload.powers());
				clientPlayerData.deserializeAffinities(payload.affinities());
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.RitualsPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
				clientPlayerData.ritualSlots = payload.ritualSlots();
				clientPlayerData.deserializeRituals(payload.rituals());
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(Payloads.OccultPointsPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayerData clientPlayerData = ParanormalClient.clientPlayerData;
				clientPlayerData.maxOccultPoints = payload.maxOccultPoints();
				clientPlayerData.occultPoints = payload.occultPoints();
			});
		});
	}
}
