package io.github.qMartinz.paranormal.particle;

import io.github.qMartinz.paranormal.networking.ModMessages;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import team.lodestar.lodestone.systems.rendering.particle.world.GenericParticle;
import team.lodestar.lodestone.systems.rendering.particle.world.WorldParticleEffect;

import java.awt.*;
import java.util.Collection;

public class GlowingParticle extends GenericParticle {
	public GlowingParticle(ClientWorld world, WorldParticleEffect data, FabricSpriteProviderImpl spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(world, data, spriteProvider, x, y, z, velocityX, velocityY, velocityZ);
	}
}
