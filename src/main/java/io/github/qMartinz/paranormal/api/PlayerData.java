package io.github.qMartinz.paranormal.api;

import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class PlayerData {
	public int pex = 0;
	public int xp = 0;

	public void setPex(int amount){
		pex = Math.max(0, Math.min(amount, 20));
	}

	public int getPex(){
		return pex;
	}

	public void addXp(int amount){
		if (amount > 0) {
			int totalXp = xp + amount;
			while (totalXp >= (pex + 1) * 50 && pex < 20) {
				totalXp -= (pex + 1) * 50;
				pex++;
			}

			xp = totalXp;
		}
	}

	public int getXp(){
		return xp;
	}

	public int getNexPercentage(){
		if (pex == 20) return 99;
		return pex*5;
	}

	public void syncData(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		PacketByteBuf data = PacketByteBufs.create();
		data.writeInt(playerState.pex);
		data.writeInt(playerState.xp);

		ServerPlayNetworking.send(player, ModMessages.PLAYER_DATA_SYNC_ID, data);
	}
}
