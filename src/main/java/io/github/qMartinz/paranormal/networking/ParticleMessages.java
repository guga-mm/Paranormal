package io.github.qMartinz.paranormal.networking;

import io.github.qMartinz.paranormal.Paranormal;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.LodestoneWorldParticleTextureSheet;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;

import java.awt.*;

public class ParticleMessages {
	public static final Identifier SPAWN_ADDITIVE_PARTICLE_ID = new Identifier(Paranormal.MODID, "spawn_additive_particle");
	public static final Identifier SPAWN_LUMITRANSPARENT_PARTICLE_ID = new Identifier(Paranormal.MODID, "spawn_lumitransparent_particle");
	public static final Identifier SPAWN_TRANSPARENT_PARTICLE_ID = new Identifier(Paranormal.MODID, "spawn_transparent_particle");
	public static final Identifier SPAWN_ADDITIVE_CIRCLE_ID = new Identifier(Paranormal.MODID, "spawn_additive_circle");
	public static final Identifier SPAWN_LUMITRANSPARENT_CIRCLE_ID = new Identifier(Paranormal.MODID, "spawn_lumitransparent_circle");
	public static final Identifier SPAWN_TRANSPARENT_CIRCLE_ID = new Identifier(Paranormal.MODID, "spawn_transparent_circle");
	public static final Identifier SPAWN_ADDITIVE_OUTLINE_ID = new Identifier(Paranormal.MODID, "spawn_additive_outline");
	public static final Identifier SPAWN_LUMITRANSPARENT_OUTLINE_ID = new Identifier(Paranormal.MODID, "spawn_lumitransparent_outline");
	public static final Identifier SPAWN_TRANSPARENT_OUTLINE_ID = new Identifier(Paranormal.MODID, "spawn_transparent_outline");

	public static void registerPackets(){
		registerSpawnParticlePackets();
		registerSpawnCirclePackets();
		registerSpawnOutlinePackets();
	}

	private static void registerSpawnParticlePackets(){
		ClientPlayNetworking.registerGlobalReceiver(SPAWN_ADDITIVE_PARTICLE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.ADDITIVE)
						.setMotion(xd, yd, zd)
						.spawn(client.world, x, y, z);
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(SPAWN_LUMITRANSPARENT_PARTICLE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.LUMITRANSPARENT)
						.setMotion(xd, yd, zd)
						.spawn(client.world, x, y, z);
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(SPAWN_TRANSPARENT_PARTICLE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.TRANSPARENT)
						.setMotion(xd, yd, zd)
						.spawn(client.world, x, y, z);
			}
		});
	}

	private static void registerSpawnCirclePackets(){
		ClientPlayNetworking.registerGlobalReceiver(SPAWN_ADDITIVE_CIRCLE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				double distance = buf.readDouble();
				int times = buf.readInt();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.ADDITIVE)
						.setRandomMotion(xd, yd, zd)
						.repeatCircle(client.world, x, y, z, distance, times);
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(SPAWN_LUMITRANSPARENT_CIRCLE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				double distance = buf.readDouble();
				int times = buf.readInt();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.LUMITRANSPARENT)
						.setRandomMotion(xd, yd, zd)
						.repeatCircle(client.world, x, y, z, distance, times);
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(SPAWN_TRANSPARENT_CIRCLE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				double distance = buf.readDouble();
				int times = buf.readInt();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.TRANSPARENT)
						.setRandomMotion(xd, yd, zd)
						.repeatCircle(client.world, x, y, z, distance, times);
			}
		});
	}

	private static void registerSpawnOutlinePackets(){
		ClientPlayNetworking.registerGlobalReceiver(SPAWN_ADDITIVE_OUTLINE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				BlockPos pos = buf.readBlockPos();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.ADDITIVE)
						.setMotion(xd, yd, zd)
						.createBlockOutline(client.world, pos, client.world.getBlockState(pos));
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(SPAWN_LUMITRANSPARENT_OUTLINE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				BlockPos pos = buf.readBlockPos();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.LUMITRANSPARENT)
						.setMotion(xd, yd, zd)
						.createBlockOutline(client.world, pos, client.world.getBlockState(pos));
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(SPAWN_TRANSPARENT_OUTLINE_ID,  (client, handler, buf, responseSender) -> {
			if (client.world != null) {
				Identifier id = buf.readIdentifier();
				ParticleType<?> particle = Registries.PARTICLE_TYPE.get(id);
				double x = buf.readDouble();
				double y = buf.readDouble();
				double z = buf.readDouble();
				BlockPos pos = buf.readBlockPos();
				double xd = buf.readDouble();
				double yd = buf.readDouble();
				double zd = buf.readDouble();
				int colorS = buf.readInt();
				int colorE = buf.readInt();
				float transparencyS = buf.readFloat();
				float transparencyM = buf.readFloat();
				float transparencyE = buf.readFloat();
				float transparencyCoeff = buf.readFloat();
				String transparencyEasing = buf.readString();
				String transparencyEasingMtE = buf.readString();
				float spinS = buf.readFloat();
				float spinM = buf.readFloat();
				float spinE = buf.readFloat();
				float spinCoeff = buf.readFloat();
				String spinEasing = buf.readString();
				String spinEasingMtE = buf.readString();
				float spinOffset = buf.readFloat();
				float scaleS = buf.readFloat();
				float scaleM = buf.readFloat();
				float scaleE = buf.readFloat();
				float scaleCoeff = buf.readFloat();
				String scaleEasing = buf.readString();
				String scaleEasingMtE = buf.readString();
				int lifetime = buf.readInt();
				float gravity = buf.readFloat();

				WorldParticleBuilder.create(particle)
						.setTransparencyData(GenericParticleData.create(transparencyS, transparencyM, transparencyE)
								.setCoefficient(transparencyCoeff)
								.setEasing(Easing.valueOf(transparencyEasing), Easing.valueOf(transparencyEasingMtE)).build())
						.setSpinData(SpinParticleData.create(spinS, spinM, spinE)
								.setCoefficient(spinCoeff)
								.setEasing(Easing.valueOf(spinEasing), Easing.valueOf(spinEasingMtE))
								.setSpinOffset(spinOffset).build())
						.setScaleData(GenericParticleData.create(scaleS, scaleM, scaleE)
								.setCoefficient(scaleCoeff)
								.setEasing(Easing.valueOf(scaleEasing), Easing.valueOf(scaleEasingMtE)).build())
						.setLifetime(lifetime).setGravity(gravity)
						.setColorData(ColorParticleData.create(new Color(colorS), new Color(colorE)).build())
						.setRenderType(LodestoneWorldParticleTextureSheet.TRANSPARENT)
						.setMotion(xd, yd, zd)
						.createBlockOutline(client.world, pos, client.world.getBlockState(pos));
			}
		});
	}

	// Single Particles

	public static void spawnAdditiveParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											 double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											 float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											 Easing transparencyEasingMtE, float spinS, float spinM, float spinE, float spinCoeff,
											 Easing spinEasing, Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM,
											 float scaleE, float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE,
											 int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_PARTICLE_ID, data);
	}

	public static void spawnAdditiveParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											 double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											 float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											 Easing transparencyEasingMtE, float scaleS, float scaleM, float scaleE, float scaleCoeff,
											 Easing scaleEasing, Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_PARTICLE_ID, data);
	}

	public static void spawnAdditiveParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											 double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											 float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											 Easing transparencyEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_PARTICLE_ID, data);
	}

	public static void spawnAdditiveParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											 double xd, double yd, double zd, Color colorS, Color colorE, int lifetime,
											 float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_PARTICLE_ID, data);
	}

	public static void spawnAdditiveParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											 double xd, double yd, double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_PARTICLE_ID, data);
	}

	public static void spawnLumitransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
													double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
													float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
													Easing transparencyEasingMtE, float spinS, float spinM, float spinE,
													float spinCoeff, Easing spinEasing, Easing spinEasingMtE, float spinOffset,
													float scaleS, float scaleM, float scaleE, float scaleCoeff, Easing scaleEasing,
													Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnLumitransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
													double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
													float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
													Easing transparencyEasingMtE, float scaleS, float scaleM, float scaleE,
													float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime,
													float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnLumitransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
													double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
													float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
													Easing transparencyEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnLumitransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
													double xd, double yd, double zd, Color colorS, Color colorE, int lifetime,
													float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnLumitransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
													double xd, double yd, double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnTransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
												float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
												Easing transparencyEasingMtE, float spinS, float spinM, float spinE, float spinCoeff,
												Easing spinEasing, Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM,
												float scaleE, float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE,
												int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnTransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
												float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
												Easing transparencyEasingMtE, float scaleS, float scaleM, float scaleE,
												float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime,
												float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnTransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
												float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
												Easing transparencyEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnTransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												double xd, double yd, double zd, Color colorS, Color colorE, int lifetime,
												float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_PARTICLE_ID, data);
	}

	public static void spawnTransparentParticle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												double xd, double yd, double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_PARTICLE_ID, data);
	}

	// Particle Circles

	public static void spawnAdditiveCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
										   double distance, int times, double xd, double yd,
										   double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
										   float transparencyE, float transparencyCoeff, Easing transparencyEasing, Easing transparencyEasingMtE,
										   float spinS, float spinM, float spinE, float spinCoeff, Easing spinEasing,
										   Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM, float scaleE,
										   float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_CIRCLE_ID, data);
	}

	public static void spawnAdditiveCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
										   double distance, int times, double xd, double yd,
										   double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
										   float transparencyE, float transparencyCoeff, Easing transparencyEasing, Easing transparencyEasingMtE,
										   float scaleS, float scaleM, float scaleE, float scaleCoeff, Easing scaleEasing,
										   Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_CIRCLE_ID, data);
	}

	public static void spawnAdditiveCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
										   double distance, int times, double xd, double yd,
										   double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
										   float transparencyE, float transparencyCoeff, Easing transparencyEasing, Easing transparencyEasingMtE,
										   int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_CIRCLE_ID, data);
	}

	public static void spawnAdditiveCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
										   double distance, int times, double xd, double yd,
										   double zd, Color colorS, Color colorE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_CIRCLE_ID, data);
	}

	public static void spawnAdditiveCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
										   double distance, int times, double xd, double yd,
										   double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_CIRCLE_ID, data);
	}

	public static void spawnLumitransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												  double distance, int times, double xd, double yd,
												  double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
												  float transparencyE, float transparencyCoeff, Easing transparencyEasing,
												  Easing transparencyEasingMtE, float spinS, float spinM, float spinE, float spinCoeff,
												  Easing spinEasing, Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM,
												  float scaleE, float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE,
												  int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnLumitransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												  double distance, int times, double xd, double yd,
												  double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
												  float transparencyE, float transparencyCoeff, Easing transparencyEasing,
												  Easing transparencyEasingMtE, float scaleS, float scaleM, float scaleE,
												  float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime,
												  float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnLumitransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												  double distance, int times, double xd, double yd,
												  double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
												  float transparencyE, float transparencyCoeff, Easing transparencyEasing,
												  Easing transparencyEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnLumitransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												  double distance, int times, double xd, double yd,
												  double zd, Color colorS, Color colorE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnLumitransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
												  double distance, int times, double xd, double yd,
												  double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnTransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											  double distance, int times, double xd, double yd,
											  double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
											  float transparencyE, float transparencyCoeff, Easing transparencyEasing, Easing transparencyEasingMtE,
											  float spinS, float spinM, float spinE, float spinCoeff, Easing spinEasing,
											  Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM, float scaleE,
											  float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnTransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											  double distance, int times, double xd, double yd,
											  double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
											  float transparencyE, float transparencyCoeff, Easing transparencyEasing, Easing transparencyEasingMtE,
											  float scaleS, float scaleM, float scaleE, float scaleCoeff, Easing scaleEasing,
											  Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnTransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											  double distance, int times, double xd, double yd,
											  double zd, Color colorS, Color colorE, float transparencyS, float transparencyM,
											  float transparencyE, float transparencyCoeff, Easing transparencyEasing, Easing transparencyEasingMtE,
											  int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnTransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											  double distance, int times, double xd, double yd,
											  double zd, Color colorS, Color colorE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_CIRCLE_ID, data);
	}

	public static void spawnTransparentCircle(ServerWorld world, ParticleType<?> particle, double x, double y, double z,
											  double distance, int times, double xd, double yd,
											  double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeDouble(distance);
		data.writeInt(times);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_CIRCLE_ID, data);
	}

	// Block Outline Particles

	public static void spawnAdditiveOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, float spinS, float spinM, float spinE, float spinCoeff,
											Easing spinEasing, Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM,
											float scaleE, float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime,
											float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_OUTLINE_ID, data);
	}

	public static void spawnAdditiveOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, float scaleS, float scaleM, float scaleE, float scaleCoeff,
											Easing scaleEasing, Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_OUTLINE_ID, data);
	}

	public static void spawnAdditiveOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_OUTLINE_ID, data);
	}

	public static void spawnAdditiveOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_OUTLINE_ID, data);
	}

	public static void spawnAdditiveOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_ADDITIVE_OUTLINE_ID, data);
	}

	public static void spawnLumitransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, float spinS, float spinM, float spinE, float spinCoeff,
											Easing spinEasing, Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM,
											float scaleE, float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime,
											float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnLumitransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, float scaleS, float scaleM, float scaleE, float scaleCoeff,
											Easing scaleEasing, Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnLumitransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnLumitransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnLumitransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_LUMITRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnTransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, float spinS, float spinM, float spinE, float spinCoeff,
											Easing spinEasing, Easing spinEasingMtE, float spinOffset, float scaleS, float scaleM,
											float scaleE, float scaleCoeff, Easing scaleEasing, Easing scaleEasingMtE, int lifetime,
											float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(spinS);
		data.writeFloat(spinM);
		data.writeFloat(spinE);
		data.writeFloat(spinCoeff);
		data.writeString(spinEasing.name);
		data.writeString(spinEasingMtE.name);
		data.writeFloat(spinOffset);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnTransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, float scaleS, float scaleM, float scaleE, float scaleCoeff,
											Easing scaleEasing, Easing scaleEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(scaleS);
		data.writeFloat(scaleM);
		data.writeFloat(scaleE);
		data.writeFloat(scaleCoeff);
		data.writeString(scaleEasing.name);
		data.writeString(scaleEasingMtE.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnTransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, float transparencyS,
											float transparencyM, float transparencyE, float transparencyCoeff, Easing transparencyEasing,
											Easing transparencyEasingMtE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(transparencyS);
		data.writeFloat(transparencyM);
		data.writeFloat(transparencyE);
		data.writeFloat(transparencyCoeff);
		data.writeString(transparencyEasing.name);
		data.writeString(transparencyEasingMtE.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnTransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, Color colorS, Color colorE, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(colorS.getRGB());
		data.writeInt(colorE.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_OUTLINE_ID, data);
	}

	public static void spawnTransparentOutline(ServerWorld world, ParticleType<?> particle, double x, double y, double z, BlockPos pos,
											double xd, double yd, double zd, int lifetime, float gravity){
		PacketByteBuf data = PacketByteBufs.create();
		data.writeIdentifier(Registries.PARTICLE_TYPE.getId(particle));
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBlockPos(pos);
		data.writeDouble(xd);
		data.writeDouble(yd);
		data.writeDouble(zd);
		data.writeInt(Color.WHITE.getRGB());
		data.writeInt(Color.BLACK.getRGB());
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeFloat(0);
		data.writeFloat(1);
		data.writeFloat(0);
		data.writeFloat(-1);
		data.writeFloat(1);
		data.writeString(Easing.LINEAR.name);
		data.writeString(Easing.LINEAR.name);
		data.writeInt(lifetime);
		data.writeFloat(gravity);

		ServerPlayNetworking.send(world.getPlayers(), SPAWN_TRANSPARENT_OUTLINE_ID, data);
	}
}
