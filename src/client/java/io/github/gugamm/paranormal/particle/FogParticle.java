package io.github.gugamm.paranormal.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

import java.util.Random;

public class FogParticle extends SpriteBillboardParticle {
	private double xRand;
	private double zRand;

	protected FogParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider sprites, double xd, double yd, double zd) {
		super(clientWorld, x, y, z, xd, yd, zd);
		this.velocityX = xd;
		this.velocityY = yd;
		this.velocityZ = zd;
		this.scale = 3.0F;
		this.maxAge = 100;
		this.alpha = 0.0F;
		this.setSprite(sprites);

		this.red = 1f;
		this.green = 1f;
		this.blue = 1f;
		Random random = new Random();
		double max = 0.05D;
		double min = -0.05D;
		xRand = random.nextDouble(max - min) + min;
		zRand = random.nextDouble(max - min) + min;
	}

	@Override
	public void tick() {
		super.tick();

		if (Math.abs(xRand) > Math.abs(zRand)) {
			zRand = 0;
		} else {
			xRand = 0;
		}
		this.move(xRand, 0.0D, zRand);

		if (this.age - 30 <= 0 && this.alpha < 1.0F) {
			this.alpha += 0.015F;
		} else if (this.age++ < this.maxAge && !(this.alpha <= 0.0F)) {
			if (this.age >= this.maxAge - 60 && this.alpha > 0.001F) {
				this.alpha -= 0.015F;
			}
		} else {
			this.markDead();
		}
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	public static class DefaultFactory implements ParticleFactory<SimpleParticleType> {
		private final SpriteProvider sprites;

		public DefaultFactory(SpriteProvider spriteProvider) {
			this.sprites = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType particleType, ClientWorld clientWorld,
									   double x, double y, double z, double xd, double yd, double zd) {
			return new FogParticle(clientWorld, x, y, z, this.sprites, xd, yd, zd);
		}
	}
}
