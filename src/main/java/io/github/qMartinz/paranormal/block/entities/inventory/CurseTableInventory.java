package io.github.qMartinz.paranormal.block.entities.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface CurseTableInventory extends Inventory {
	default int size() {
		return 1;
	}

	default boolean isEmpty() {
		return this.getItem().isEmpty();
	}

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

	default ItemStack removeStack(int slot) {
		return this.removeStack(slot, this.getMaxCountPerStack());
	}
}
