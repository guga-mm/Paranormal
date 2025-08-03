package io.github.gugamm.paranormal.api;

import io.github.gugamm.paranormal.networking.Payloads;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientPlayerData extends PlayerData {
	public void syncAllToServer(){
		ClientPlayNetworking.send(new Payloads.PexPayload(pex, xp));
		ClientPlayNetworking.send(new Payloads.AttributesPayload(attPoints, attributes[0], attributes[1], attributes[2]));
		ClientPlayNetworking.send(new Payloads.PowersPayload(powerPoints, serializePowers(), serializeAffinities()));
		ClientPlayNetworking.send(new Payloads.RitualsPayload(ritualSlots, serializeRituals()));
		ClientPlayNetworking.send(new Payloads.OccultPointsPayload(maxOccultPoints, occultPoints));
	}

	public void syncPexToServer(){
		ClientPlayNetworking.send(new Payloads.PexPayload(pex, xp));
	}

	public void syncAttributesToServer(){
		ClientPlayNetworking.send(new Payloads.AttributesPayload(attPoints, attributes[0], attributes[1], attributes[2]));
	}

	public void syncPowersToServer(){
		ClientPlayNetworking.send(new Payloads.PowersPayload(powerPoints, serializePowers(), serializeAffinities()));
	}

	public void syncRitualsToServer(){
		ClientPlayNetworking.send(new Payloads.RitualsPayload(ritualSlots, serializeRituals()));
	}

	public void syncOccultPointsToServer(){
		ClientPlayNetworking.send(new Payloads.OccultPointsPayload(maxOccultPoints, occultPoints));
	}
}
