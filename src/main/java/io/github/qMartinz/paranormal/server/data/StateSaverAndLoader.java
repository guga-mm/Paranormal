package io.github.qMartinz.paranormal.server.data;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {
	public HashMap<UUID, PlayerData> players = new HashMap<>();

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		NbtCompound playersNbt = new NbtCompound();
		players.forEach((uuid, playerData) -> {
			NbtCompound playerNbt = new NbtCompound();

			playerNbt.putInt("pex", playerData.pex);
			playerNbt.putInt("xp", playerData.xp);
			playerNbt.putInt("attPoints", playerData.attPoints);
			playerNbt.putIntArray("attributes", playerData.attributes);
			playerNbt.putInt("ritualSlots", playerData.ritualSlots);
			playerNbt.putInt("powerPoints", playerData.powerPoints);

			playersNbt.put(uuid.toString(), playerNbt);
		});
		nbt.put("players", playersNbt);

		return nbt;
	}

	public static StateSaverAndLoader createFromNbt(NbtCompound tag) {
		StateSaverAndLoader state = new StateSaverAndLoader();

		NbtCompound playersNbt = tag.getCompound("players");
		playersNbt.getKeys().forEach(key -> {
			PlayerData playerData = new PlayerData();

			playerData.pex = playersNbt.getCompound(key).getInt("pex");
			playerData.xp = playersNbt.getCompound(key).getInt("xp");
			playerData.attPoints = playersNbt.getCompound(key).getInt("attPoints");
			playerData.attributes = playersNbt.getCompound(key).getIntArray("attributes");
			playerData.ritualSlots = playersNbt.getCompound(key).getInt("ritualSlots");
			playerData.powerPoints = playersNbt.getCompound(key).getInt("powerPoints");

			UUID uuid = UUID.fromString(key);
			state.players.put(uuid, playerData);
		});

		return state;
	}

	public static StateSaverAndLoader getServerState(MinecraftServer server) {
		PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

		StateSaverAndLoader state = persistentStateManager.getOrCreate(StateSaverAndLoader::createFromNbt, StateSaverAndLoader::new, Paranormal.MODID);

		state.markDirty();

		return state;
	}

	public static PlayerData getPlayerState(LivingEntity player) {
		StateSaverAndLoader serverState = getServerState(player.getServer());

		// Either get the player by the uuid, or we don't have data for him yet, make a new player state
		PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

		return playerState;
	}
}
