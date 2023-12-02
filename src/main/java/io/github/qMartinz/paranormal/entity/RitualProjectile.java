package io.github.qMartinz.paranormal.entity;

import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.api.rituals.types.ProjectileRitual;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RitualProjectile extends ProjectileEntity {
    private static final TrackedData<Integer> DATA_ELEMENT;

    public AbstractRitual ritual;

    private LivingEntity target;

    private final double iX;
    private final double iY;
    private final double iZ;

	public RitualProjectile(EntityType<? extends RitualProjectile> entityType, World world) {
		super(entityType, world);
		this.iX = this.getX();
		this.iY = this.getY();
		this.iZ = this.getZ();
	}

	public RitualProjectile(EntityType<? extends RitualProjectile> entityType, World world, AbstractRitual ritual) {
		super(entityType, world);
		this.iX = this.getX();
		this.iY = this.getY();
		this.iZ = this.getZ();
		this.ritual = ritual;
	}

	@Override
	protected void initDataTracker() {

	}

	@Override
    public void tick() {
        super.tick();

		/*
        if (this.world instanceof ServerWorld level1){
            level1.spawnParticles(AbilitiesParticleOptions.createData(ritual.getElement().getParticleColor(), ritual.getElement() != ParanormalElement.MORTE),
                    this.getX(), this.getY(), this.getZ(), 3, 0.05d, 0.05d, 0.05d, 0d);
        }
        */

        if (target == null || !target.isAlive()){
            target = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,
                    null, getX(), getY(), getZ(), getBoundingBox().expand(1.5d));
            if (getOwner() instanceof MobEntity mob && target != mob.getTarget()) {
                target = null;
            }
        }

        if (target != null && target != getOwner() && target.isAlive()) {
            this.setVelocity(target.getPos().add(0d, target.getEyeHeight(target.getPose()), 0d).subtract(getPos()).multiply(0.005d).normalize());
        } else {
            target = null;
        }

        if (ritual != null){
            if (Math.abs(iX - getX()) > this.getRange() ||
                    Math.abs(iY - getY()) > this.getRange() ||
                    Math.abs(iZ - getZ()) > this.getRange()) {
                if (getOwner() instanceof LivingEntity owner && world.getBlockState(getBlockPos()).isAir()) {
					if (ritual instanceof ProjectileRitual ritual1){
						ritual1.onBlockHit(owner, BlockHitResult.createMissed(getPos(), Direction.random(random), getBlockPos()));
					}
                }
				this.discard();
            }
        }
    }

	@Override
	protected void onCollision(HitResult hitResult) {
		if (getOwner() instanceof LivingEntity owner) {
			if (ritual instanceof ProjectileRitual ritual1){
				ritual1.onHit(owner, hitResult);
			}
		}
		super.onCollision(hitResult);
		this.discard();
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (getOwner() instanceof LivingEntity owner) {
			if (ritual instanceof ProjectileRitual ritual1){
				ritual1.onEntityHit(owner, entityHitResult);
			}
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (getOwner() instanceof LivingEntity owner) {
			if (ritual instanceof ProjectileRitual ritual1){
				ritual1.onBlockHit(owner, blockHitResult);
			}
		}
	}

    public int getElement() {
        return this.dataTracker.get(DATA_ELEMENT);
    }

    public void setElement(int elementIndex) {
        if (elementIndex < 0 || elementIndex >= 5) {
            elementIndex = this.random.nextInt(4);
        }

        this.dataTracker.set(DATA_ELEMENT, elementIndex);
    }

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("element", this.getElement());
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setElement(nbt.getInt("element"));
	}

    private double getRange(){
		if (getOwner() instanceof LivingEntity owner){
			PlayerData playerData = StateSaverAndLoader.getPlayerState(owner);
			double length = ritual.getRange();
			//if (playerData.hasPower(ModPowerRegistry.AMPLIAR_RITUAL) && length > 0d) length += 3.5d;
			return length;
		} else return ritual.getRange();
    }

	static{
		DATA_ELEMENT = DataTracker.registerData(RitualProjectile.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
