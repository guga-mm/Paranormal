package io.github.gugamm.paranormal.server.data;

import io.github.gugamm.paranormal.Paranormal;
import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.api.powers.ParanormalPower;
import io.github.gugamm.paranormal.api.powers.PowerRegistry;
import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import io.github.gugamm.paranormal.api.rituals.RitualRegistry;
import io.github.gugamm.paranormal.networking.Payloads;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {
	public HashMap<UUID, PlayerData> players = new HashMap<>();
	private static final Type<StateSaverAndLoader> type = new Type<>(
		StateSaverAndLoader::new, // If there's no 'StateSaverAndLoader' yet create one
		StateSaverAndLoader::createFromNbt, // If there is a 'StateSaverAndLoader' NBT, parse it with 'createFromNbt'
		null // Supposed to be an 'DataFixTypes' enum, but we can just pass null
	);

	@Override
	public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
		NbtCompound playersNbt = new NbtCompound();
		players.forEach((uuid, playerData) -> {
			NbtCompound playerNbt = new NbtCompound();

			playerNbt.putInt("pex", playerData.pex);
			playerNbt.putInt("xp", playerData.xp);
			playerNbt.putInt("attPoints", playerData.attPoints);
			playerNbt.putIntArray("attributes", playerData.attributes);
			playerNbt.putInt("ritualSlots", playerData.ritualSlots);
			playerNbt.putInt("powerPoints", playerData.powerPoints);
			playerNbt.putDouble("maxOccultPoints", playerData.maxOccultPoints);
			playerNbt.putDouble("occultPoints", playerData.occultPoints);

			NbtCompound rituals = new NbtCompound();
			for (int i = 0; i < playerData.rituals.size(); i++) {
				AbstractRitual ritual = playerData.rituals.stream().toList().get(i);
				if (ritual != null) rituals.putString(ritual.getId().getNamespace() + "_ritual_" + i,
					playerData.rituals.stream().toList().get(i).getId().getPath());
			}

			NbtCompound powers = new NbtCompound();
			for (int i = 0; i < playerData.powers.size(); i++) {
				ParanormalPower power = playerData.powers.stream().toList().get(i);
				if (power != null) powers.putString(power.getId().getNamespace() + "_power_" + i,
					playerData.powers.stream().toList().get(i).getId().getPath());
			}

			NbtCompound affinities = new NbtCompound();
			for (int i = 0; i < playerData.affinities.size(); i++) {
				ParanormalPower power = playerData.powers.stream().toList().get(i);
				if (power != null) affinities.putString(power.getId().getNamespace() + "_affinity_" + i,
					playerData.affinities.stream().toList().get(i).getId().getPath());
			}

			playerNbt.put("rituals", rituals);
			playerNbt.put("powers", powers);
			playerNbt.put("affinities", affinities);

			playersNbt.put(uuid.toString(), playerNbt);
		});
		nbt.put("players", playersNbt);

		return nbt;
	}

	public static StateSaverAndLoader createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
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
			playerData.maxOccultPoints = nbt.getDouble("maxOccultPoints");
			playerData.occultPoints = nbt.getDouble("occultPoints");

			playerData.rituals.clear();
			NbtCompound rituals = nbt.getCompound("rituals");
			for (int i = 0; i < rituals.getKeys().size(); i++) {
				String ritualKey = rituals.getKeys().stream().toList().get(i);
				String[] splitKey = ritualKey.split("_ritual_");
				String namespace = Objects.equals(splitKey[0], "") ? Paranormal.MODID : splitKey[0];

				playerData.rituals.add(RitualRegistry.getRitual(Identifier.of(namespace,
					rituals.getString(ritualKey))).orElse(null));
			}

			playerData.powers.clear();
			NbtCompound powers = nbt.getCompound("powers");
			for (int i = 0; i < powers.getKeys().size(); i++) {
				String powerKey = powers.getKeys().stream().toList().get(i);
				String[] splitKey = powerKey.split("_power_");
				String namespace = Objects.equals(splitKey[0], "") ? Paranormal.MODID : splitKey[0];

				playerData.powers.add(PowerRegistry.getPower(Identifier.of(namespace,
					powers.getString(powerKey))).orElse(null));
			}

			playerData.affinities.clear();
			NbtCompound affinities = nbt.getCompound("affinities");
			for (int i = 0; i < affinities.getKeys().size(); i++) {
				String affinityKey = affinities.getKeys().stream().toList().get(i);
				String[] splitKey = affinityKey.split("_affinity_");
				String namespace = Objects.equals(splitKey[0], "") ? Paranormal.MODID : splitKey[0];

				playerData.affinities.add(PowerRegistry.getPower(Identifier.of(namespace,
					affinities.getString(affinityKey))).orElse(null));
			}

			UUID uuid = UUID.fromString(key);
			state.players.put(uuid, playerData);
		});

		return state;
	}

	public static StateSaverAndLoader getServerState(MinecraftServer server) {
		PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

		StateSaverAndLoader state = persistentStateManager.getOrCreate(type, Paranormal.MODID);

		state.markDirty();

		return state;
	}

	public static PlayerData getPlayerState(LivingEntity player) {
		StateSaverAndLoader serverState = getServerState(player.getServer());

		// Either get the player by the uuid, or we don't have data for them yet, make a new player state
		PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

		return playerState;
	}

	public static void syncAllToClient(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		ServerPlayNetworking.send(player, new Payloads.PexPayload(playerState.pex, playerState.xp));
		ServerPlayNetworking.send(player, new Payloads.AttributesPayload(playerState.attPoints, playerState.attributes[0], playerState.attributes[1], playerState.attributes[2]));
		ServerPlayNetworking.send(player, new Payloads.PowersPayload(playerState.powerPoints, playerState.serializePowers(), playerState.serializeAffinities()));
		ServerPlayNetworking.send(player, new Payloads.RitualsPayload(playerState.ritualSlots, playerState.serializeRituals()));
		ServerPlayNetworking.send(player, new Payloads.OccultPointsPayload(playerState.maxOccultPoints, playerState.occultPoints));
	}

	public static void syncPexToClient(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		ServerPlayNetworking.send(player, new Payloads.PexPayload(playerState.pex, playerState.xp));
	}

	public static void syncAttributesToClient(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		ServerPlayNetworking.send(player, new Payloads.AttributesPayload(playerState.attPoints, playerState.attributes[0], playerState.attributes[1], playerState.attributes[2]));
	}

	public static void syncPowersToClient(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		ServerPlayNetworking.send(player, new Payloads.PowersPayload(playerState.powerPoints, playerState.serializePowers(), playerState.serializeAffinities()));
	}

	public static void synRitualsToClient(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		ServerPlayNetworking.send(player, new Payloads.RitualsPayload(playerState.ritualSlots, playerState.serializeRituals()));
	}

	public static void syncOccultPointsToClient(ServerPlayerEntity player){
		PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
		ServerPlayNetworking.send(player, new Payloads.OccultPointsPayload(playerState.maxOccultPoints, playerState.occultPoints));
	}
}
