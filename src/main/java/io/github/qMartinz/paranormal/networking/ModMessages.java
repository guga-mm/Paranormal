package io.github.qMartinz.paranormal.networking;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class ModMessages {
	public static final Identifier PLAYER_DATA_SYNC_ID = new Identifier(Paranormal.MODID, "player_data_sync");

	public static void registerC2SPackets() {
		ServerPlayNetworking.registerGlobalReceiver(PLAYER_DATA_SYNC_ID, (server, player, handler, buf, responseSender) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(handler.player);
			playerData.pex = buf.readInt();
			playerData.xp = buf.readInt();
			playerData.attPoints = buf.readInt();
			playerData.attributes = buf.readIntArray();
			playerData.ritualSlots = buf.readInt();
			playerData.powerPoints = buf.readInt();
			playerData.maxOccultPoints = buf.readInt();
			playerData.occultPoints = buf.readInt();

			playerData.deserializeRituals(buf.readNbt());
			playerData.deserializePowers(buf.readNbt());
		});
	}

	public static void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(PLAYER_DATA_SYNC_ID, (client, handler, buf, responseSender) -> {
			ParanormalClient.playerData.pex = buf.readInt();
			ParanormalClient.playerData.xp = buf.readInt();
			ParanormalClient.playerData.attPoints = buf.readInt();
			ParanormalClient.playerData.attributes = buf.readIntArray();
			ParanormalClient.playerData.ritualSlots = buf.readInt();
			ParanormalClient.playerData.powerPoints = buf.readInt();
			ParanormalClient.playerData.maxOccultPoints = buf.readInt();
			ParanormalClient.playerData.occultPoints = buf.readInt();

			ParanormalClient.playerData.deserializeRituals(buf.readNbt());
			ParanormalClient.playerData.deserializePowers(buf.readNbt());
		});
	}
}
