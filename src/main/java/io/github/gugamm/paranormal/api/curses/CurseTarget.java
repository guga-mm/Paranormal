package io.github.gugamm.paranormal.api.curses;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public enum CurseTarget {
	ARMOR {
		public boolean isAcceptableItem(Item item) {
			return item instanceof ArmorItem;
		}
	},
	ARMOR_FEET {
		public boolean isAcceptableItem(Item item) {
			boolean var10000;
			if (item instanceof ArmorItem armorItem) {
				if (armorItem.getSlotType() == EquipmentSlot.FEET) {
					var10000 = true;
					return var10000;
				}
			}

			var10000 = false;
			return var10000;
		}
	},
	ARMOR_LEGS {
		public boolean isAcceptableItem(Item item) {
			boolean var10000;
			if (item instanceof ArmorItem armorItem) {
				if (armorItem.getSlotType() == EquipmentSlot.LEGS) {
					var10000 = true;
					return var10000;
				}
			}

			var10000 = false;
			return var10000;
		}
	},
	ARMOR_CHEST {
		public boolean isAcceptableItem(Item item) {
			boolean var10000;
			if (item instanceof ArmorItem armorItem) {
				if (armorItem.getSlotType() == EquipmentSlot.CHEST) {
					var10000 = true;
					return var10000;
				}
			}

			var10000 = false;
			return var10000;
		}
	},
	ARMOR_HEAD {
		public boolean isAcceptableItem(Item item) {
			boolean var10000;
			if (item instanceof ArmorItem armorItem) {
				if (armorItem.getSlotType() == EquipmentSlot.HEAD) {
					var10000 = true;
					return var10000;
				}
			}

			var10000 = false;
			return var10000;
		}
	},
	WEAPON {
		public boolean isAcceptableItem(Item item) {
			return item instanceof SwordItem;
		}
	},
	DIGGER {
		public boolean isAcceptableItem(Item item) {
			return item instanceof MiningToolItem;
		}
	},
	FISHING_ROD {
		public boolean isAcceptableItem(Item item) {
			return item instanceof FishingRodItem;
		}
	},
	TRIDENT {
		public boolean isAcceptableItem(Item item) {
			return item instanceof TridentItem;
		}
	},
	BREAKABLE {
		public boolean isAcceptableItem(Item item) {
			return new ItemStack(item).isDamageable();
		}
	},
	BOW {
		public boolean isAcceptableItem(Item item) {
			return item instanceof BowItem;
		}
	},
	WEARABLE {
		public boolean isAcceptableItem(Item item) {
			return item instanceof Equipment || Block.getBlockFromItem(item) instanceof Equipment;
		}
	},
	CROSSBOW {
		public boolean isAcceptableItem(Item item) {
			return item instanceof CrossbowItem;
		}
	};

	CurseTarget() {
	}

	public abstract boolean isAcceptableItem(Item item);
}
