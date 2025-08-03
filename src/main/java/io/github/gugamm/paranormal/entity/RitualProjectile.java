package io.github.gugamm.paranormal.entity;

import io.github.gugamm.paranormal.api.PlayerData;
import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import io.github.gugamm.paranormal.api.rituals.types.ProjectileRitual;
import io.github.gugamm.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class RitualProjectile extends ProjectileEntity {
	private static final TrackedData<Integer> DATA_ELEMENT;

	public AbstractRitual ritual;

	private LivingEntity target;

	private final double iX;
	private final double iY;
	private final double iZ;

	private int particleIncrement;

	public RitualProjectile(EntityType<? extends RitualProjectile> type, World world) {
		super(type, world);
		this.iX = this.getX();
		this.iY = this.getY();
		this.iZ = this.getZ();
	}

	public RitualProjectile(EntityType<? extends RitualProjectile> entityType, World world, LivingEntity owner, AbstractRitual ritual) {
		super(entityType, world);
		this.setOwner(owner); // Define o proprietário do projétil. Isso é crucial para evitar que ele colida consigo mesmo.
		this.iX = this.getX();
		this.iY = this.getY();
		this.iZ = this.getZ();
		// A rotação e o lançamento do projétil serão feitos no ritual.onShoot, não aqui no construtor.
		this.ritual = ritual;
		this.setElement(ritual.getElement().index);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(DATA_ELEMENT, 0);
	}

	static{
		DATA_ELEMENT = DataTracker.registerData(RitualProjectile.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Override
	public void tick() {
		super.tick();

		// Lógica de rastreamento de alvo para mobs
		if (getOwner() instanceof MobEntity mob) {
			if (target == null || !target.isAlive() || target.isDead()) {
				// Tenta encontrar um novo alvo dentro de um raio de 1.5 blocos
				target = getWorld().getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(1.5d),
						livingEntity -> livingEntity != getOwner() && livingEntity.isAlive())
					.stream().min((a, b) -> (int) (a.distanceTo(this) - b.distanceTo(this))).orElse(null);
			}
			// Se houver um alvo e ele não for o alvo do dono (para evitar que o projétil siga um alvo que o mob não está perseguindo)
			if (target != null && target != mob.getTarget()) {
				target = null;
			}
			if (target != null && target.isAlive()) {
				// Define a velocidade do projétil para seguir o alvo
				Vec3d targetPos = target.getPos().add(0, target.getEyeHeight(target.getPose()), 0);
				Vec3d direction = targetPos.subtract(getPos()).normalize();
				this.setVelocity(direction.multiply(0.5D)); // Ajuste a velocidade conforme necessário
			}
		}

		// Verifica se o projétil viajou além do seu alcance
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
			this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Emitter.of(this, null));
			if (getOwner() instanceof LivingEntity owner) {
				if (ritual instanceof ProjectileRitual ritual1){
					ritual1.onHit(owner, hitResult);
				}
			}

			this.discard();
		} else if (type == HitResult.Type.BLOCK &&
			!getWorld().getBlockState(((BlockHitResult) hitResult).getBlockPos()).isAir()) {
			BlockHitResult blockHitResult = (BlockHitResult)hitResult;
			this.onBlockHit(blockHitResult);
			BlockPos blockPos = blockHitResult.getBlockPos();
			this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.getWorld().getBlockState(blockPos)));
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
		if (getOwner() instanceof LivingEntity owner && entityHitResult.getEntity() != getOwner()) {
			if (ritual instanceof ProjectileRitual ritual1){
				ritual1.onEntityHit(owner, entityHitResult);
			}
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		if (getOwner() instanceof LivingEntity owner && !getWorld().getBlockState(blockHitResult.getBlockPos()).isAir()) {
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

	private double getRange(){
		if (getOwner() instanceof LivingEntity owner){
			PlayerData playerData = StateSaverAndLoader.getPlayerState(owner);
			double length = ritual.getRange();
			//if (playerData.hasPower(ModPowerRegistry.AMPLIAR_RITUAL) && length > 0d) length += 3.5d;
			return length;
		} else return ritual.getRange();
	}

	@Override
	protected boolean canHit(Entity entity) {
		if (entity == this.getOwner()) return false;
		return super.canHit(entity);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("element", this.getElement());
		nbt.putDouble("initial_x", this.iX);
		nbt.putDouble("initial_y", this.iY);
		nbt.putDouble("initial_z", this.iZ);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setElement(nbt.getInt("element"));
		// Não precisamos ler as coordenadas iniciais, pois elas são definidas no construtor
	}
}
