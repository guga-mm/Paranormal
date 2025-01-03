package io.github.qMartinz.paranormal.entity;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.registry.ModEntityRegistry;
import io.github.qMartinz.paranormal.registry.ModParticleRegistry;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import io.github.qMartinz.paranormal.util.FearData;
import io.github.qMartinz.paranormal.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
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
	private int increaseIntensityTimer = 1200;
	private int turnRuinedTimer = 1200;

	public FogEntity(EntityType<?> variant, World world) {
		super(variant, world);

	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {

		this.dataTracker.set(FOG_INTENSITY, 1);
		this.dataTracker.set(FOG_LIFE, getMaxLife());
		this.dataTracker.set(FOG_RADIUS, 15);
	}

	public void setRadius(int size) {
		this.dataTracker.set(FOG_RADIUS, Math.max(15, Math.min(45, size)));
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
		return 1200;
	}

	public int getMaxRadius(){
		if (this.getType() == ModEntityRegistry.RUINED_FOG) return 90;
		return 45;
	}

	static{
		FOG_RADIUS = DataTracker.registerData(FogEntity.class, TrackedDataHandlerRegistry.INTEGER);
		FOG_LIFE = DataTracker.registerData(FogEntity.class, TrackedDataHandlerRegistry.INTEGER);
		FOG_INTENSITY = DataTracker.registerData(FogEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	public void fogParticle(){
		DefaultParticleType particle = ModParticleRegistry.FOG_1;
		if (this.getType() == ModEntityRegistry.RUINED_FOG) particle = ModParticleRegistry.FOG_2;
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
						randomPos.x, randomPos.y + 1.4d, randomPos.z, 0D, 0D, 0D);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (random.nextFloat() <= 0.2f) fogParticle();

		if (shouldLoseIntensity()) {
			setLife(getLife() - 1);
			if (getLife() <= 0) {
				if (this.getType() == ModEntityRegistry.RUINED_FOG){
					if (this.getIntensity() > 1) {
						loseIntensity();
					} else if (getIntensity() == 1) turnNormal();
				} else {
					if (this.getIntensity() > 1) {
						loseIntensity();
					} else if (getIntensity() == 1) {
						this.discard();
					}
				}
			}
		} else {
			setLife(getMaxLife());

			if (this.getType() == ModEntityRegistry.RUINED_FOG){
				List<VillagerEntity> v1 = this.getWorld().getEntitiesByClass(VillagerEntity.class, Box.of(this.getPos(), getRadius()*2, getRadius()*2, getRadius()*2),
						e -> e.distanceTo(this) <= getRadius() && FearData.getFear(((IEntityDataSaver) e)) >= 200);

				if (!v1.isEmpty()){
					increaseIntensityTimer -= v1.size();

					if (increaseIntensityTimer <= 0){
						increaseIntensity();
					}
				} else increaseIntensityTimer = 1200;
			} else {
				List<VillagerEntity> v1 = this.getWorld().getEntitiesByClass(VillagerEntity.class, Box.of(this.getPos(), getRadius()*2, getRadius()*2, getRadius()*2),
						e -> e.distanceTo(this) <= getRadius() && FearData.getFear(((IEntityDataSaver) e)) >= 100);

				if (!v1.isEmpty()){
					increaseIntensityTimer -= v1.size();

					if (increaseIntensityTimer <= 0){
						if (getIntensity() >= 3) {
							List<VillagerEntity> v2 = this.getWorld().getEntitiesByClass(VillagerEntity.class, Box.of(this.getPos(), getRadius()*2, getRadius()*2, getRadius()*2),
									e -> e.distanceTo(this) <= getRadius() && FearData.getFear(((IEntityDataSaver) e)) >= 150);

							if (!v2.isEmpty()){
								turnRuinedTimer -= v2.size();

								if (turnRuinedTimer <= 0) turnRuined();
							} else turnRuinedTimer = 1200;
						} else increaseIntensity();
					}
				} else increaseIntensityTimer = 1200;
			}
		}

		for (Entity entity : entitiesWithin()){
			if (entity instanceof PlayerEntity player && !player.getWorld().isClient()){
				PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
				// TODO limit xp gain by pex after testing is done
				if (random.nextFloat() <= 0.05f && !getWorld().isClient()) {
					playerData.addXp(getIntensity() + (getType() == ModEntityRegistry.RUINED_FOG ? 3 : 0));
					playerData.syncAllToClient((ServerPlayerEntity) player);
				}
			}
		}
	}

	public List<Entity> entitiesWithin(){
		return this.getWorld().getEntitiesByClass(Entity.class, Box.of(this.getPos(), getRadius()*2, getRadius()*2, getRadius()*2),
				e -> e.distanceTo(this) <= getRadius());
	}

	public int getRequiredFear(){
		if (this.getType() == ModEntityRegistry.RUINED_FOG){
			return getIntensity() > 1 ? 200 : 150;
		} else {
			return getIntensity() > 1 ? 100 : 50;
		}
	}

	public boolean shouldLoseIntensity(){
		boolean hasFearfulVillager = entitiesWithin().stream().anyMatch(e ->
				e instanceof VillagerEntity villager && FearData.getFear(((IEntityDataSaver) villager)) >= getRequiredFear());

		boolean hasParanormalOrigin = entitiesWithin().stream().anyMatch(e ->
				e instanceof HostileEntity || e instanceof CorpseEntity);

		return !hasParanormalOrigin && !hasFearfulVillager;
	}

	public void loseIntensity(){
		setIntensity(getIntensity() - 1);
		setRadius(getRadius() - 15);
		setLife(getMaxLife());
		Paranormal.LOGGER.info("Losing intensity!");
	}

	public void increaseIntensity(){
		this.setIntensity(this.getIntensity() + 1);
		this.setRadius(this.getRadius() + 15);
		increaseIntensityTimer = 1200;
		Paranormal.LOGGER.info("Increasing intensity!");
	}

	public void turnNormal(){
		FogEntity normalFog = new FogEntity(ModEntityRegistry.FOG, this.getWorld());
		normalFog.setPosition(this.getPos());
		normalFog.setRadius(this.getRadius());
		normalFog.setIntensity(3);
		this.getWorld().spawnEntity(normalFog);
		this.discard();
		Paranormal.LOGGER.info("Turning normal!");
	}

	public void turnRuined(){
		FogEntity ruinedFog = new FogEntity(ModEntityRegistry.RUINED_FOG, this.getWorld());
		ruinedFog.setPosition(this.getPos());
		ruinedFog.setRadius(this.getRadius());
		this.getWorld().spawnEntity(ruinedFog);
		this.discard();
		Paranormal.LOGGER.info("Turning ruined!");
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
