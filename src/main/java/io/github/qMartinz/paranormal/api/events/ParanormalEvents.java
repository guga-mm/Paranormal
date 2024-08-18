package io.github.qMartinz.paranormal.api.events;

import io.github.qMartinz.paranormal.api.powers.ParanormalPower;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import io.github.qMartinz.paranormal.client.screen.PowersScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.quiltmc.qsl.base.api.event.Event;

public class ParanormalEvents {
	/**
	 * Evento engatilhado quando um poder é adicionado aos poderes que um jogador possui.
	 */
	public static final Event<PowerAdded> POWER_ADDED = Event.create(PowerAdded.class,
			(listeners) -> (power, player) -> {
				for (PowerAdded listener : listeners) {
					listener.powerAdded(power, player);
				}
			});

	/**
	 * Evento engatilhado quando um ritual é adicionado aos rituais que um jogador conhece.
	 */
	public static final Event<RitualAdded> RITUAL_ADDED = Event.create(RitualAdded.class,
			(listeners) -> (ritual, player) -> {
				for (RitualAdded listener : listeners) {
					listener.ritualAdded(ritual, player);
				}
			});

	/**
	 * Evento engatilhado quando um ritual é utilizado por uma entidade. Outros mods podem cancelar esse evento para impedir que o ritual seja utilizado.
	 */
	public static final Event<RitualCast> RITUAL_CAST = Event.create(RitualCast.class,
			(listeners) -> (ritual, caster) -> {
				for (RitualCast listener : listeners) {
					return listener.ritualCast(ritual, caster);
				}

				return true;
			});

	public static final Event<TakenShieldHit> TAKEN_SHIELD_HIT = Event.create(TakenShieldHit.class,
			(listeners) -> (attacker, target) -> {
				for (TakenShieldHit listener : listeners){
					listener.takenShieldHit(attacker, target);
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

	public interface TakenShieldHit {
		void takenShieldHit(LivingEntity attacker, LivingEntity target);
	}
}
