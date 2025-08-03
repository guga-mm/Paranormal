package io.github.gugamm.paranormal.api.curses;

import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import io.github.gugamm.paranormal.registry.ModComponentsRegistry;
import io.github.gugamm.paranormal.util.MathUtils;
import io.github.gugamm.paranormal.util.NBTUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CurseHelper {
	private static final String PREFIX = "curse_";

	public static Identifier getCurseId(NbtCompound nbt) {
		return Identifier.tryParse(nbt.getString("id"));
	}

	public static int getCurseUses(NbtCompound nbt) {
		return nbt.getInt("uses");
	}

	public static Set<CurseInstance> getCurses(ItemStack pStack) {
		NbtCompound nbt = new NbtCompound();
		for (int i = 0; i < pStack.getOrDefault(ModComponentsRegistry.ITEM_CURSES, new ArrayList<ModComponentsRegistry.CurseComponent>()).size(); i++) {
			ModComponentsRegistry.CurseComponent curse = pStack.getOrDefault(ModComponentsRegistry.ITEM_CURSES, new ArrayList<ModComponentsRegistry.CurseComponent>()).get(i);
			NbtCompound curseNbt = new NbtCompound();
			curseNbt.putString("id", curse.id());
			curseNbt.putInt("uses", curse.uses());
			nbt.put(PREFIX + i, curseNbt);
		}

		return deserializeCurses(nbt);
	}

	public static CurseInstance getCurse(ItemStack stack, AbstractCurse curse){
		NbtCompound nbt = new NbtCompound();
		for (int i = 0; i < stack.getOrDefault(ModComponentsRegistry.ITEM_CURSES, new ArrayList<ModComponentsRegistry.CurseComponent>()).size(); i++) {
			ModComponentsRegistry.CurseComponent curseComponent = stack.getOrDefault(ModComponentsRegistry.ITEM_CURSES, new ArrayList<ModComponentsRegistry.CurseComponent>()).get(i);
			NbtCompound curseNbt = new NbtCompound();
			curseNbt.putString("id", curseComponent.id());
			curseNbt.putInt("uses", curseComponent.uses());
			nbt.put(PREFIX + i, curseNbt);
		}

		for (int i = 0; i < nbt.getSize(); i++){
			NbtCompound curseTag = nbt.getCompound(PREFIX + i);
			if (getCurseId(curseTag).equals(curse.getId())) {
				return new CurseInstance(CurseRegistry.getCurse(getCurseId(curseTag)).orElse(null), getCurseUses(curseTag));
			}
		}
		return null;
	}

	public static Set<CurseInstance> deserializeCurses(NbtCompound pSerialized) {
		Set<CurseInstance> set = new HashSet<>();

		for(NbtCompound c : NBTUtil.readCurses(pSerialized)){
			set.add(new CurseInstance(CurseRegistry.getCurse(Identifier.tryParse(c.getString("id"))).orElse(null), c.getInt("uses")));
		}

		return set;
	}

	public static void setCurses(Collection<CurseInstance> pCurses, ItemStack pStack) {
		NbtCompound nbt = new NbtCompound();

		for (int i = 0; i < pCurses.size(); i++) nbt.put(PREFIX + i, pCurses.stream().toList().get(i).serializeNBT());

		if (nbt.isEmpty()) {
			pStack.set(ModComponentsRegistry.ITEM_CURSES, new ArrayList<>());
		} else {
			List<ModComponentsRegistry.CurseComponent> itemCurses = new ArrayList<>();
			for (int i = 0; i < nbt.getSize(); i++){
				NbtCompound curseTag = nbt.getCompound(PREFIX + i);
				itemCurses.add(new ModComponentsRegistry.CurseComponent(curseTag.getString("id"), curseTag.getInt("uses")));
			}

			pStack.set(ModComponentsRegistry.ITEM_CURSES, itemCurses);
		}
	}

	public static ItemStack addCurse(ItemStack pStack, CurseInstance pCurse){
		Set<CurseInstance> curses = CurseHelper.getCurses(pStack);
		curses.removeIf(c -> c.getCurse().getId().equals(pCurse.getCurse().getId()));
		if (curses.stream().filter(inst -> !inst.getCurse().isTemporary()).toList().size() < 4) {
			curses.add(pCurse);
		}
		CurseHelper.setCurses(curses, pStack);
		return pStack;
	}

	public static ItemStack addCurse(ItemStack pStack, AbstractCurse pCurse){
		Set<CurseInstance> curses = CurseHelper.getCurses(pStack);
		curses.removeIf(c -> c.getCurse().getId().equals(pCurse.getId()));
		if (curses.stream().filter(inst -> !inst.getCurse().isTemporary()).toList().size() < 4) {
			curses.add(new CurseInstance(pCurse));
		}
		CurseHelper.setCurses(curses, pStack);
		return pStack;
	}

	public static ItemStack removeCurse(ItemStack pStack, AbstractCurse pCurse){
		Set<CurseInstance> curses = CurseHelper.getCurses(pStack);
		curses.removeIf(c -> c.getCurse().getId().equals(pCurse.getId()));
		CurseHelper.setCurses(curses, pStack);
		return pStack;
	}

	public static float doPostAttackEffects(LivingEntity pAttacker, LivingEntity pTarget, float pAmount, DamageSource source){
		float f = pAmount;
		if (pAttacker != null) {
			for (CurseInstance curse : getCurses(pAttacker.getMainHandStack())) {
				if (curse != null) {
					f = curse.getCurse().doPostAttack(pAttacker.getMainHandStack(), pAttacker, pTarget, f, source);

					f += MathUtils.calcParanormalDmg(curse.getCurse().getDamageBonus(), pTarget, curse.getCurse().getElement());
				}
			}
		}
		return f;
	}

	public static float doPostHurtEffects(LivingEntity pTarget, @Nullable Entity pAttacker, float pAmount, DamageSource source){
		float f = pAmount;
		if (pTarget != null) {
			for (ItemStack item : pTarget.getEquippedItems()) {
				for (CurseInstance curseInstance : getCurses(item)) {
					if (curseInstance != null) {
						f = curseInstance.getCurse().doPostHurt(item, pTarget, pAttacker, f, source);

						f -= curseInstance.getCurse().getDamageProtection(source);
					}
				}
			}
		}
		return f;
	}

	public static void doTickEffects(LivingEntity pUser){
		if (pUser != null) {
			for (ItemStack item : pUser.getEquippedItems()) {
				for (CurseInstance curse : getCurses(item)) {
					if (curse != null) curse.getCurse().doTick(item, pUser);
				}
			}
		}
	}

	public static boolean doBlockBreakEffects(PlayerEntity pUser, BlockPos pPos, BlockState pState){
		if (pUser != null) {
			for (ItemStack item : pUser.getEquippedItems()) {
				boolean allowBreak = true;
				for (CurseInstance curse : getCurses(item)) {
					if (curse != null) allowBreak = allowBreak && curse.getCurse().doBlockBreak(pUser, pUser.getWorld(), pPos, pState);
				}
				return allowBreak;
			}
		}
		return true;
	}

	public static boolean isRitualTargetEffects(LivingEntity target, AbstractRitual ritual, LivingEntity caster){
		if (target != null) {
			for (ItemStack item : target.getEquippedItems()) {
				boolean allowCasting = true;
				for (CurseInstance curse : getCurses(item)) {
					if (curse != null) allowCasting = allowCasting && curse.getCurse().isRitualTarget(target, target.getWorld(), ritual, caster);
				}
				return allowCasting;
			}
		}
		return true;
	}

	public static boolean isRitualCasterEffects(LivingEntity caster, AbstractRitual ritual){
		if (caster != null) {
			for (ItemStack item : caster.getEquippedItems()) {
				boolean allowCasting = true;
				for (CurseInstance curse : getCurses(item)) {
					if (curse != null) allowCasting = allowCasting && curse.getCurse().isRitualCaster(caster, caster.getWorld(), ritual);
				}
				return allowCasting;
			}
		}
		return true;
	}
}
