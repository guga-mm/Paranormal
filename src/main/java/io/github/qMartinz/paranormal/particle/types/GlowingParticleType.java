package io.github.qMartinz.paranormal.particle.types;

import com.mojang.serialization.Codec;
import io.github.qMartinz.paranormal.particle.GlowingParticle;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleType;
import team.lodestar.lodestone.systems.rendering.particle.world.WorldParticleEffect;

public class GlowingParticleType extends ParticleType<WorldParticleEffect> {
	public GlowingParticleType() {
		super(false, WorldParticleEffect.DESERIALIZER);
	}

	@Override
	public boolean shouldAlwaysSpawn() {
		return true;
	}

	@Override
	public Codec<WorldParticleEffect> getCodec() {
		return WorldParticleEffect.codecFor(this);
	}

	public record Factory(SpriteProvider sprite) implements ParticleFactory<WorldParticleEffect> {
		@Override
		public Particle createParticle(WorldParticleEffect data, ClientWorld world, double x, double y, double z, double mx, double my, double mz) {
			return new GlowingParticle(world, data, (FabricSpriteProviderImpl) sprite, x, y, z, mx, my, mz);
		}
	}
}
