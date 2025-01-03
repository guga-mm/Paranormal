package io.github.qMartinz.paranormal.networking;

import io.github.qMartinz.paranormal.Paranormal;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.impl.networking.PayloadTypeRegistryImpl;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.util.Identifier;

public class Payloads {
	public static void registerPayloads(){
		Paranormal.LOGGER.info("Registering payloads for " + Paranormal.MODID);

		PayloadTypeRegistry.playC2S().register(PexPayload.ID, PexPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(AttributesPayload.ID, AttributesPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PowersPayload.ID, PowersPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(RitualsPayload.ID, RitualsPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(OccultPointsPayload.ID, OccultPointsPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(CastPayload.ID, CastPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(AddedPowerTriggerPayload.ID, AddedPowerTriggerPayload.CODEC);

		PayloadTypeRegistry.playS2C().register(PexPayload.ID, PexPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(AttributesPayload.ID, AttributesPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PowersPayload.ID, PowersPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(RitualsPayload.ID, RitualsPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(OccultPointsPayload.ID, OccultPointsPayload.CODEC);
	}

	public record PexPayload(int pex, int xp) implements CustomPayload {
		public static final CustomPayload.Id<PexPayload> ID = new Id<>(ModMessages.PEX_SYNC_ID);
		public static final PacketCodec<RegistryByteBuf, PexPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PexPayload::pex,
			PacketCodecs.VAR_INT, PexPayload::xp,
			PexPayload::new
		);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}

	public record AttributesPayload(int attPoints, int str, int pre, int vig) implements CustomPayload {
		public static final CustomPayload.Id<AttributesPayload> ID = new Id<>(ModMessages.ATTRIBUTES_SYNC_ID);
		public static final PacketCodec<RegistryByteBuf, AttributesPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, AttributesPayload::attPoints,
			PacketCodecs.VAR_INT, AttributesPayload::str,
			PacketCodecs.VAR_INT, AttributesPayload::pre,
			PacketCodecs.VAR_INT, AttributesPayload::vig,
			AttributesPayload::new
		);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}

	public record PowersPayload(int powerPoints, NbtCompound powers, NbtCompound affinities) implements CustomPayload {
		public static final CustomPayload.Id<PowersPayload> ID = new Id<>(ModMessages.POWERS_SYNC_ID);
		public static final PacketCodec<RegistryByteBuf, PowersPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PowersPayload::powerPoints,
			PacketCodecs.NBT_COMPOUND, PowersPayload::powers,
			PacketCodecs.NBT_COMPOUND, PowersPayload::affinities,
			PowersPayload::new
		);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}

	public record RitualsPayload(int ritualSlots, NbtCompound rituals) implements CustomPayload {
		public static final CustomPayload.Id<RitualsPayload> ID = new Id<>(ModMessages.RITUALS_SYNC_ID);
		public static final PacketCodec<RegistryByteBuf, RitualsPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, RitualsPayload::ritualSlots,
			PacketCodecs.NBT_COMPOUND, RitualsPayload::rituals,
			RitualsPayload::new
		);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}

	public record OccultPointsPayload(double maxOccultPoints, double occultPoints) implements CustomPayload {
		public static final CustomPayload.Id<OccultPointsPayload> ID = new Id<>(ModMessages.OCCULT_POINTS_SYNC_ID);
		public static final PacketCodec<RegistryByteBuf, OccultPointsPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.DOUBLE, OccultPointsPayload::maxOccultPoints,
			PacketCodecs.DOUBLE, OccultPointsPayload::occultPoints,
			OccultPointsPayload::new
		);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}

	public record CastPayload(int index) implements CustomPayload {
		public static final CustomPayload.Id<CastPayload> ID = new Id<>(ModMessages.CAST_ID);
		public static final PacketCodec<RegistryByteBuf, CastPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.INT, CastPayload::index,
			CastPayload::new
		);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}

	public record AddedPowerTriggerPayload(Identifier powerId) implements CustomPayload {
		public static final CustomPayload.Id<AddedPowerTriggerPayload> ID = new Id<>(ModMessages.ADDED_POWER_TRIGGER_ID);
		public static final PacketCodec<RegistryByteBuf, AddedPowerTriggerPayload> CODEC = PacketCodec.tuple(
			Identifier.PACKET_CODEC, AddedPowerTriggerPayload::powerId,
			AddedPowerTriggerPayload::new
		);

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}
}
