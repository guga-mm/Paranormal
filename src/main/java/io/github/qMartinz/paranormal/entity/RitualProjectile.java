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
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class RitualProjectile extends PersistentProjectileEntity {
    private static final TrackedData<Integer> DATA_ELEMENT;

    public AbstractRitual ritual;

    private LivingEntity target;

    private final double iX;
    private final double iY;
    private final double iZ;

	private int particleIncrement;

	public RitualProjectile(EntityType<? extends PersistentProjectileEntity> type, World world) {
		super(type, world);
		this.iX = this.getX();
		this.iY = this.getY();
		this.iZ = this.getZ();
	}

	public RitualProjectile(EntityType<? extends RitualProjectile> entityType, World world, LivingEntity owner, AbstractRitual ritual) {
		super(entityType, owner, world, ItemStack.EMPTY, null);
		this.pickupType = PickupPermission.DISALLOWED;
		this.iX = this.getX();
		this.iY = this.getY();
		this.iZ = this.getZ();
		this.setRotation(owner.getYaw(), owner.getPitch());
		this.ritual = ritual;
		this.setElement(ritual.getElement().index);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(DATA_ELEMENT, 0);
	}

	static{
		DATA_ELEMENT = DataTracker.registerData(RitualProjectile.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Override
    public void tick() {
        super.tick();

        if (target == null || !target.isAlive()){
            target = getWorld().getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,
                    null, getX(), getY(), getZ(), getVisibilityBoundingBox().expand(1.5d));
            if (getOwner() instanceof MobEntity mob && target != mob.getTarget()) {
                target = null;
            }
        }

        if (target != null && target != getOwner() && target.isAlive()) {
            this.setVelocity(target.getPos().add(0d, target.getEyeHeight(target.getPose()), 0d).subtract(getPos()).multiply(0.0005d).normalize());
        } else {
            target = null;
        }

        if (ritual != null){
            if (Math.abs(iX - getX()) > this.getRange() ||
                    Math.abs(iY - getY()) > this.getRange() ||
                    Math.abs(iZ - getZ()) > this.getRange()) {
                if (getOwner() instanceof LivingEntity owner && getWorld().getBlockState(getBlockPos()).isAir()) {
					if (ritual instanceof ProjectileRitual ritual1){
						ritual1.onBlockHit(owner, BlockHitResult.createMissed(getPos(), Direction.random(random), getBlockPos()));
					}
                }
				this.discard();
            }
        }
    }

	@Override
	public void baseTick() {
		super.baseTick();
		if (ritual != null && this.getWorld() instanceof ServerWorld world) {
			// TODO ritual projectile VFX
		}

		particleIncrement++;
	}

	@Override
	protected void spawnSprintingParticles() {
		super.spawnSprintingParticles();
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		HitResult.Type type = hitResult.getType();
		if (type == HitResult.Type.ENTITY && ((EntityHitResult) hitResult).getEntity() != getOwner()) {
			this.onEntityHit((EntityHitResult)hitResult);
			this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Context.create(this, null));
			if (getOwner() instanceof LivingEntity owner) {
				if (ritual instanceof ProjectileRitual ritual1){
					ritual1.onHit(owner, hitResult);
				}
			}

			this.discard();
		} else if (type == HitResult.Type.BLOCK) {
			BlockHitResult blockHitResult = (BlockHitResult)hitResult;
			this.onBlockHit(blockHitResult);
			BlockPos blockPos = blockHitResult.getBlockPos();
			this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Context.create(this, this.getWorld().getBlockState(blockPos)));
			if (getOwner() instanceof LivingEntity owner) {
				if (ritual instanceof ProjectileRitual ritual1){
					ritual1.onHit(owner, hitResult);
				}
			}

			this.discard();
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (getOwner() instanceof LivingEntity owner) {
			if (ritual instanceof ProjectileRitual ritual1){
				ritual1.onEntityHit(owner, entityHitResult);
			}
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
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
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("element", this.getElement());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setElement(nbt.getInt("element"));
	}

	@Override
	protected ItemStack asItemStack() {
		return null;
	}

	@Override
	protected ItemStack getDefaultItemStack() {
		return ItemStack.EMPTY;
	}

	private double getRange(){
		if (getOwner() instanceof LivingEntity owner){
			PlayerData playerData = StateSaverAndLoader.getPlayerState(owner);
			double length = ritual.getRange();
			//if (playerData.hasPower(ModPowerRegistry.AMPLIAR_RITUAL) && length > 0d) length += 3.5d;
			return length;
		} else return ritual.getRange();
    }
}
