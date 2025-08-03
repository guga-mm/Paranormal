package io.github.gugamm.paranormal.api.curses;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class CurseInstance {
	private AbstractCurse curse;
	private int uses;

	public CurseInstance(AbstractCurse curse) {
		this.curse = curse;
		this.uses = 0;
	}

	public CurseInstance(AbstractCurse curse, int uses){
		this.curse = curse;
		this.uses = uses;
	}

	public AbstractCurse getCurse() {
		return curse;
	}

	public void setCurse(AbstractCurse curse) {
		this.curse = curse;
	}

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public void useOrRemove(ItemStack pStack){
		if (curse.isTemporary()) {
			int newUses = uses + 1;
			if (newUses == curse.getMaxUses()) {
				CurseHelper.removeCurse(pStack, this.curse);
			} else {
				this.uses = newUses;
				CurseHelper.addCurse(pStack, this);
			}
		}
	}

	public NbtCompound serializeNBT() {
		NbtCompound nbt = new NbtCompound();
		nbt.putString("id", curse.id.toString());
		nbt.putInt("uses", uses);

		return nbt;
	}

	public void deserializeNBT(NbtCompound nbt) {
		curse = CurseRegistry.getCurse(Identifier.tryParse(nbt.getString("id"))).orElse(null);
		uses = nbt.getInt("uses");
	}
}
