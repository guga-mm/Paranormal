package io.github.qMartinz.paranormal.entity;

import io.github.qMartinz.paranormal.registry.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CorpseEntity extends Entity {
	private static final TrackedData<Integer> EXPOSURE = DataTracker.registerData(CorpseEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public CorpseEntity(EntityType<?> variant, World world) {
		super(variant, world);
		this.inanimate = true;
	}

	public boolean collides() {
		return !this.isRemoved();
	}

	@Override
	protected void initDataTracker() {
		this.dataTracker.startTracking(EXPOSURE, 0);
	}

	public void setExposure(int exposure){
		this.dataTracker.set(EXPOSURE, exposure);
	}

	public int getExposure(){
		return this.dataTracker.get(EXPOSURE);
	}

	@Override
	public void tick() {
		if (!this.hasNoGravity()) {
			this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
		}

		this.move(MovementType.SELF, this.getVelocity());
		this.setVelocity(this.getVelocity().multiply(0.98));
		if (this.isOnGround()) {
			this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
		}

		super.tick();
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (player.getStackInHand(hand).isOf(Items.FLINT_AND_STEEL)) {
			this.dropStack(new ItemStack(ItemRegistry.ASHES, random.range(1, 3)));
			this.remove(RemovalReason.KILLED);
			return ActionResult.CONSUME;
		} else {
			this.dropStack(new ItemStack(ItemRegistry.ORGAN, random.range(1, 3)));
			this.remove(RemovalReason.KILLED);
			return ActionResult.CONSUME;
		}
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		this.setExposure(nbt.getInt("Exposure"));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putInt("Exposure", this.getExposure());
	}
}
