package io.github.qMartinz.paranormal.entity;

import io.github.qMartinz.paranormal.registry.EntityRegistry;
import io.github.qMartinz.paranormal.registry.ParticleRegistry;
import io.github.qMartinz.paranormal.util.IEntityDataSaver;
import io.github.qMartinz.paranormal.util.NexData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.List;

public class FogEntity extends Entity {
	private static final TrackedData<Integer> FOG_INTENSITY;
	private static final TrackedData<Integer> FOG_LIFE;
	private static final TrackedData<Integer> FOG_RADIUS;

	public FogEntity(EntityType<?> variant, World world) {
		super(variant, world);

	}

	@Override
	protected void initDataTracker() {
		this.dataTracker.startTracking(FOG_INTENSITY, 1);
		this.dataTracker.startTracking(FOG_LIFE, getMaxLife());
		this.dataTracker.startTracking(FOG_RADIUS, 15);
	}

	public void setRadius(int size) {
		this.dataTracker.set(FOG_RADIUS, Math.max(25, Math.min(75, size)));
	}

	public void setIntensity(int intensity) {
		this.dataTracker.set(FOG_INTENSITY, Math.max(1, Math.min(3, intensity)));
	}

	public void setLife(int life) {
		this.dataTracker.set(FOG_LIFE, Math.min(life, getMaxLife()));
	}

	public int getRadius() {
		return this.dataTracker.get(FOG_RADIUS);
	}

	public int getIntensity() {
		return this.dataTracker.get(FOG_INTENSITY);
	}

	public int getLife() {
		return this.dataTracker.get(FOG_LIFE);
	}
	public int getMaxLife(){
		if (this.getType() == EntityRegistry.RUINED_FOG) return 1200;
		return 600;
	}

	static{
		FOG_RADIUS = DataTracker.registerData(FogEntity.class, TrackedDataHandlerRegistry.INTEGER);
		FOG_LIFE = DataTracker.registerData(FogEntity.class, TrackedDataHandlerRegistry.INTEGER);
		FOG_INTENSITY = DataTracker.registerData(FogEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	public void fogParticle(){
		DefaultParticleType particle = ParticleRegistry.FOG_1;
		if (this.getType() == EntityRegistry.RUINED_FOG) particle = ParticleRegistry.FOG_2;
		double radius = this.getRadius();
		for (int i = 1; i <= Math.pow(radius, 1.8) * getIntensity(); i++) {
			Vec3d randomPos = this.getPos().add(
					new Vec3d(random.range((int) -radius, (int) radius),
							random.range((int) -radius, (int) radius),
							random.range((int) -radius, (int) radius)));

			BlockPos block = new BlockPos(new Vec3i((int) randomPos.x, (int) randomPos.y, (int) randomPos.z));
			if (!this.getWorld().getBlockState(block.down()).isAir() && this.getWorld().getBlockState(block).isAir()
			&& randomPos.distanceTo(this.getPos()) <= radius) {
				this.getWorld().addParticle(particle,
						randomPos.x, randomPos.y + 1.5d, randomPos.z, 0D, 0D, 0D);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (random.nextFloat() <= 0.2f) {
			fogParticle();

			if (entitiesWithin().stream().anyMatch(e -> e instanceof HostileEntity || e instanceof CorpseEntity)) {
				setLife(getMaxLife());
			} else {
				setLife(getLife() - 1);
				if (getLife() <= 0) {
					if (this.getIntensity() > 1 || this.getRadius() > 25) {
						setIntensity(getIntensity() - 1);
						setRadius(getRadius() - 25);
						setLife(getMaxLife());
					} else if (getIntensity() == 1 && getRadius() == 25) this.remove(RemovalReason.DISCARDED);
				}
			}
		}

		for (Entity entity : entitiesWithin()){
			if (entity instanceof PlayerEntity player && NexData.getNex((IEntityDataSaver) player) < 1){
				NexData.addNex((IEntityDataSaver) player, 1);
			}
		}
	}

	public List<Entity> entitiesWithin(){
		return this.getWorld().getEntitiesByClass(Entity.class, Box.of(this.getPos(), getRadius()*2, getRadius()*2, getRadius()*2),
				e -> e.distanceTo(this) <= getRadius());
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		this.setRadius(nbt.getInt("Radius"));
		this.setIntensity(nbt.getInt("Intensity"));
		this.setLife(nbt.getInt("Life"));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putDouble("Radius", getRadius());
		nbt.putInt("Intensity", getIntensity());
		nbt.putInt("Life", getLife());
	}
}
