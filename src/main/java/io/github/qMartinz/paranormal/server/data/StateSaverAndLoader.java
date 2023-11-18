package io.github.qMartinz.paranormal.server.data;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.powers.PowerRegistry;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.RitualRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
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

			NbtCompound rituals = new NbtCompound();
			for (int i = 0; i < playerData.rituals.size(); i++) {
				rituals.putString("ritual_" + i, playerData.rituals.stream().toList().get(i).getId().toString());
			}
			NbtCompound powers = new NbtCompound();
			for (int i = 0; i < playerData.powers.size(); i++) {
				powers.putString("power_" + i, playerData.powers.stream().toList().get(i).getId().toString());
			}

			playerNbt.put("rituals", rituals);
			playerNbt.put("powers", powers);

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
			NbtCompound nbt = playersNbt.getCompound(key);

			playerData.pex = nbt.getInt("pex");
			playerData.xp = nbt.getInt("xp");
			playerData.attPoints = nbt.getInt("attPoints");
			playerData.attributes = nbt.getIntArray("attributes");
			playerData.ritualSlots = nbt.getInt("ritualSlots");
			playerData.powerPoints = nbt.getInt("powerPoints");

			playerData.rituals.clear();
			NbtCompound rituals = nbt.getCompound("rituals");
			for (int i = 0; i < rituals.getKeys().size(); i++){
				playerData.rituals.add(RitualRegistry.getRitual(new Identifier(rituals.getString("ritual_" + i))).orElse(null));
			}

			playerData.powers.clear();
			NbtCompound powers = nbt.getCompound("powers");
			for (int i = 0; i < powers.getKeys().size(); i++){
				playerData.powers.add(PowerRegistry.getPower(new Identifier(powers.getString("power_" + i))).orElse(null));
			}

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
