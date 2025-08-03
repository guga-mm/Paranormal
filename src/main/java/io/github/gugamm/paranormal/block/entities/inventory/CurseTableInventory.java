package io.github.gugamm.paranormal.block.entities.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface CurseTableInventory extends Inventory {
	@Override
	default int size() {
		return 1;
	}

	@Override
	default boolean isEmpty() {
		return this.getItem().isEmpty();
	}

	@Override
	default ItemStack getStack(int slot) {
		return getItem();
	};

	@Override
	default void clear() {
		this.removeItem();
	}

	default ItemStack getItem() {
		return this.getStack(0);
	}

	default ItemStack removeItem() {
		return this.removeStack(0);
	}

	default void setItem(ItemStack stack) {
		this.setStack(0, stack);
	}

	@Override
	default ItemStack removeStack(int slot) {
		return this.removeStack(slot, this.getMaxCountPerStack());
	}

	@Override
	default void setStack(int slot, ItemStack stack) {
		setItem(stack);
	}

	@Override
	default boolean canPlayerUse(PlayerEntity player){
		return true;
	}
}
