package io.github.qMartinz.paranormal.networking;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class ModMessages {
	public static final Identifier PLAYER_DATA_SYNC_ID = new Identifier(Paranormal.MODID, "player_data_sync");

	public static void registerC2SPackets() {
	}

	public static void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(PLAYER_DATA_SYNC_ID, (client, handler, buf, responseSender) -> {
			ParanormalClient.playerData.pex = buf.readInt();
			ParanormalClient.playerData.xp = buf.readInt();
		});
	}
}
