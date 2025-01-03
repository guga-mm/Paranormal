package io.github.qMartinz.paranormal.api.events;

import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;

public class ParanormalEvents {
	/**
	 * Evento engatilhado quando um poder é adicionado aos poderes que um jogador possui.
	 */
	public static final Event<PowerAdded> POWER_ADDED = EventFactory.createArrayBacked(PowerAdded.class,
			(listeners) -> (power, player) -> {
				for (PowerAdded listener : listeners) {
					listener.powerAdded(power, player);
				}
			});

	/**
	 * Evento engatilhado quando um ritual é adicionado aos rituais que um jogador conhece.
	 */
	public static final Event<RitualAdded> RITUAL_ADDED = EventFactory.createArrayBacked(RitualAdded.class,
			(listeners) -> (ritual, player) -> {
				for (RitualAdded listener : listeners) {
					listener.ritualAdded(ritual, player);
				}
			});

	/**
	 * Evento engatilhado quando um ritual é utilizado por uma entidade. Outros mods podem cancelar esse evento para impedir que o ritual seja utilizado.
	 */
	public static final Event<RitualCast> RITUAL_CAST = EventFactory.createArrayBacked(RitualCast.class,
			(listeners) -> (ritual, caster) -> {
				boolean allowCasting = true;
				for (RitualCast listener : listeners) {
					allowCasting = allowCasting && listener.ritualCast(ritual, caster);
				}

				return allowCasting;
			});

	public static final Event<RitualTarget> RITUAL_TARGET = EventFactory.createArrayBacked(RitualTarget.class,
			(listeners) -> (ritual, caster, target) -> {
				boolean allowCasting = true;
				for (RitualTarget listener : listeners) {
					allowCasting = allowCasting && listener.ritualTarget(ritual, caster, target);
				}

				return allowCasting;
			});

	public static final Event<TakenShieldHit> TAKEN_SHIELD_HIT = EventFactory.createArrayBacked(TakenShieldHit.class,
			(listeners) -> (attacker, target) -> {
				for (TakenShieldHit listener : listeners) {
					listener.takenShieldHit(attacker, target);
				}
			});

	public static final Event<UseItem> USE_ITEM = EventFactory.createArrayBacked(UseItem.class,
			(listeners) -> (world, player, hand, result) -> {
				for (UseItem listener : listeners) {
					return listener.useItem(world, player, hand, result);
				}

				return result;
			});

	public static final Event<FinishUsingItem> FINISH_USING_ITEM = EventFactory.createArrayBacked(FinishUsingItem.class,
			(listeners) -> (stack, world, user) -> {
				for (FinishUsingItem listener : listeners) {
					return listener.finishUsingItem(stack, world, user);
				}

				return stack;
			});

	public static final Event<TickUseItem> TICK_USE_ITEM = EventFactory.createArrayBacked(TickUseItem.class,
			(listeners) -> (world, user, stack, remainingUseTicks) -> {
				for (TickUseItem listener : listeners) {
					listener.tickUseItem(world, user, stack, remainingUseTicks);
				}
			});

	public static final Event<AddExperience> ADD_EXPERIENCE = EventFactory.createArrayBacked(AddExperience.class,
			(listeners) -> (player, experience) -> {
				for (AddExperience listener : listeners) {
					experience = listener.addExperience(player, experience);
				}

				return experience;
			});

	public static final Event<AddExperienceLevels> ADD_EXPERIENCE_LEVELS = EventFactory.createArrayBacked(AddExperienceLevels.class,
			(listeners) -> (player, levels) -> {
				for (AddExperienceLevels listener : listeners) {
					levels = listener.addExperienceLevels(player, levels);
				}

				return levels;
			});

	public static final Event<EntityTick> ENTITY_TICK = EventFactory.createArrayBacked(EntityTick.class,
		(listeners) -> (entity) -> {
			for (EntityTick listener : listeners) {
				listener.entityTick(entity);
			}
		});

	public interface PowerAdded {
		void powerAdded(ParanormalPower power, PlayerEntity player);
	}

	public interface RitualAdded {
		void ritualAdded(AbstractRitual ritual, PlayerEntity player);
	}

	public interface RitualCast {
		boolean ritualCast(AbstractRitual ritual, LivingEntity caster);
	}

	public interface RitualTarget {
		boolean ritualTarget(AbstractRitual ritual, LivingEntity caster, LivingEntity target);
	}

	public interface TakenShieldHit {
		void takenShieldHit(LivingEntity attacker, LivingEntity target);
	}

	public interface UseItem {
		TypedActionResult<ItemStack> useItem(World world, PlayerEntity player, Hand hand, TypedActionResult<ItemStack> result);
	}

	public interface FinishUsingItem {
		ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity user);
	}

	public interface TickUseItem {
		void tickUseItem(World world, LivingEntity user, ItemStack stack, int remainingUseTicks);
	}

	public interface AddExperience {
		int addExperience(PlayerEntity player, int experience);
	}

	public interface AddExperienceLevels {
		int addExperienceLevels(PlayerEntity player, int levels);
	}

	public interface EntityTick {
		void entityTick(Entity entity);
	}
}
