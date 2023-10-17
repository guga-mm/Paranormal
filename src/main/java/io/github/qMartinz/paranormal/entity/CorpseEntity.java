package io.github.qMartinz.paranormal.entity;

import io.github.qMartinz.paranormal.registry.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CorpseEntity extends Entity {
	private static final TrackedData<Integer> EXPOSURE = DataTracker.registerData(CorpseEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public CorpseEntity(EntityType<?> variant, World world) {
		super(variant, world);
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
		super.tick();
	}



	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (player.getStackInHand(hand).getItem() instanceof SwordItem) {
			this.dropStack(new ItemStack(ItemRegistry.ORGAN));
			this.remove(RemovalReason.KILLED);
			return ActionResult.CONSUME;
		}

		if (player.getStackInHand(hand).isOf(Items.FLINT_AND_STEEL)) {
			this.dropStack(new ItemStack(ItemRegistry.ASHES));
			this.remove(RemovalReason.KILLED);
			return ActionResult.CONSUME;
		}

		return super.interact(player, hand);
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
