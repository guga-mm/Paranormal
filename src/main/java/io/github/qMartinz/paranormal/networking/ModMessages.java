package io.github.qMartinz.paranormal.networking;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.client.screen.AttributesScreen;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class ModMessages {
	public static final Identifier PLAYER_DATA_SYNC_ID = new Identifier(Paranormal.MODID, "player_data_sync");
	public static final Identifier INCREASE_ATTRIBUTE_ID = new Identifier(Paranormal.MODID, "increase_attribute");

	public static void registerC2SPackets() {
		ServerPlayNetworking.registerGlobalReceiver(INCREASE_ATTRIBUTE_ID, (server, player, handler, buf, responseSender) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(handler.player);
			playerData.attPoints = buf.readInt();
			playerData.attributes = buf.readIntArray();
		});
	}

	public static void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(PLAYER_DATA_SYNC_ID, (client, handler, buf, responseSender) -> {
			ParanormalClient.playerData.pex = buf.readInt();
			ParanormalClient.playerData.xp = buf.readInt();
			ParanormalClient.playerData.attPoints = buf.readInt();
			ParanormalClient.playerData.attributes = buf.readIntArray();
		});
	}
}
