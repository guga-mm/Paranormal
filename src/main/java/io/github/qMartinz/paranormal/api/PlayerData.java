package io.github.qMartinz.paranormal.api;

import io.github.qMartinz.paranormal.networking.ModMessages;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.Map;

public class PlayerData {
	public int pex = 0;
	public int xp = 0;
	public int attPoints = 0;

	/*
	0 = str
	1 = vig
	2 = pre
	 */
	public int[] attributes = new int[]{0, 0, 0};

	public int ritualSlots;
	public int powerPoints;

	// Rituals
	// Powers

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
				// +1 attPoint each 10%
				if ((pex - 1) % 2 == 0) attPoints++;

				// +2 ritualSlots in 5% and +1 ritualSlot each 20%
				if (pex % 4 == 0) ritualSlots++;
				if (pex == 1) ritualSlots += 2;

				// +1 powerPoint each 15% and +1 powerPoint in 99%
				if ((pex - 1) % 3 == 0) powerPoints++;
				if (pex == 20) powerPoints++;
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

	public void setAttPoints(int attPoints) {
		this.attPoints = attPoints;
	}

	public int getAttPoints() {
		return attPoints;
	}

	public void setAttribute(int index, int value) {
		this.attributes[index] = value;
	}

	public int getAttribute(int index){
		return attributes[index];
	}

	public void syncData(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		PacketByteBuf data = PacketByteBufs.create();
		data.writeInt(playerState.pex);
		data.writeInt(playerState.xp);
		data.writeInt(playerState.attPoints);
		data.writeIntArray(playerState.attributes);

		ServerPlayNetworking.send(player, ModMessages.PLAYER_DATA_SYNC_ID, data);
	}
	public void increaseAttributes(){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeInt(attPoints);
		data.writeIntArray(attributes);

		ClientPlayNetworking.send(ModMessages.INCREASE_ATTRIBUTE_ID, data);
	}
}
