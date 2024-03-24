package io.github.qMartinz.paranormal.networking;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.ParanormalClient;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.Collection;

public class ModMessages {
	public static final Identifier PLAYER_DATA_SYNC_ID = new Identifier(Paranormal.MODID, "player_data_sync");
	public static final Identifier CAST_RITUAL_ID = new Identifier(Paranormal.MODID, "cast_ritual");
	public static final Identifier SPAWN_PARTICLE_ID = new Identifier(Paranormal.MODID, "spawn_particle");

	public static void registerC2SPackets() {
		ServerPlayNetworking.registerGlobalReceiver(PLAYER_DATA_SYNC_ID, (server, player, handler, buf, responseSender) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(handler.player);
			playerData.pex = buf.readInt();
			playerData.xp = buf.readInt();
			playerData.attPoints = buf.readInt();
			playerData.attributes = buf.readIntArray();
			playerData.ritualSlots = buf.readInt();
			playerData.powerPoints = buf.readInt();
			playerData.maxOccultPoints = buf.readDouble();
			playerData.occultPoints = buf.readDouble();

			playerData.deserializeRituals(buf.readNbt());
			playerData.deserializePowers(buf.readNbt());
		});

		ServerPlayNetworking.registerGlobalReceiver(CAST_RITUAL_ID, (server, player, handler, buf, responseSender) -> {
			PlayerData playerData = StateSaverAndLoader.getPlayerState(handler.player);
			playerData.getRitual(buf.readInt()).onCast(player);
			Paranormal.LOGGER.info("Cast ritual within message!");
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
			ParanormalClient.playerData.maxOccultPoints = buf.readDouble();
			ParanormalClient.playerData.occultPoints = buf.readDouble();

			ParanormalClient.playerData.deserializeRituals(buf.readNbt());
			ParanormalClient.playerData.deserializePowers(buf.readNbt());
		});

		ClientPlayNetworking.registerGlobalReceiver(SPAWN_PARTICLE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				DefaultParticleType particle = (DefaultParticleType) Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				client.world.addParticle(particle, x, y, z, xd, yd, zd);
			}
		});

		ParticleMessages.registerPackets();
	}

	public static void spawnParticlePacket(Collection<ServerPlayerEntity> players, ParticleType<?> particle, double x, double y, double z, double xd, double yd, double zd){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		ServerPlayNetworking.send(players, SPAWN_PARTICLE_ID, data);
	}
}
