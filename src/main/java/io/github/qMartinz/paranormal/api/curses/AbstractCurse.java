package io.github.qMartinz.paranormal.api.curses;

import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.rituals.AbstractRitual;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCurse {
	protected final Identifier id;
	private final EquipmentSlot[] slots;
	public final ParanormalElement element;
	public final CurseTarget target;


	public AbstractCurse(Identifier id, ParanormalElement element, CurseTarget target, EquipmentSlot... slots) {
		this.id = id;
		this.slots = slots;
		this.element = element;
		this.target = target;
	}

	public Identifier getId() {
		return id;
	}

	public EquipmentSlot[] getSlots() {
		return slots;
	}

	public ParanormalElement getElement() {
		return element;
	}

	public CurseTarget getTarget() {
		return target;
	}

	public final boolean isCompatibleWith(AbstractCurse pOther) {
		return this.checkCompatibility(pOther) && pOther.checkCompatibility(this);
	}

	protected boolean checkCompatibility(AbstractCurse pOther) {
		return this != pOther && this.getElement().isCompatible(pOther.getElement());
	}

	public boolean canCurse(ItemStack stack){
		boolean flag1 = CurseHelper.getCurses(stack).stream().allMatch(inst -> inst.getCurse().isCompatibleWith(this));
		boolean flag2 = this.target.isAcceptableItem(stack.getItem());
		return flag1 && flag2;
	}

	public String getTranslationKey(){
		return this.getId().getNamespace() + ".curse." + this.getId().getPath();
	}

	public Text getDisplayName() {
		MutableText name = Text.translatable(getTranslationKey());
		switch (element){
			case BLOOD -> name.formatted(Formatting.DARK_RED);
			case WISDOM -> name.formatted(Formatting.GOLD);
			case ENERGY -> name.formatted(Formatting.DARK_PURPLE);
			case DEATH -> name.formatted(Formatting.DARK_GRAY);
			case FEAR -> name.formatted(Formatting.WHITE);
		}

		return name;
	}

	/**
	 * Realiza seu efeito quando o usuário ataca.
	 * @return a quantidade de dano após o efeito da maldição.
	 */
	public float doPostAttack(ItemStack pStack, LivingEntity pAttacker, LivingEntity pTarget, float amount, DamageSource source) {
		CurseHelper.getCurse(pStack, this).useOrRemove(pStack);
		return amount;
	}

	/**
	 * Realiza seu efeito quando o usuário é atacado.
	 * @return a quantidade de dano após o efeito da maldição.
	 */
	public float doPostHurt(ItemStack pStack, LivingEntity pTarget, @Nullable Entity pAttacker, float amount, DamageSource source) {
		CurseHelper.getCurse(pStack, this).useOrRemove(pStack);
		return amount;
	}

	/**
	 * Realiza seu efeito a cada tick.
	 */
	public void doTick(ItemStack pStack, LivingEntity pUser) {}

	/**
	 * Chamado quando o usuário quebra um bloco
	 *
	 * @param entity quem quebrou
	 * @param level o level onde o bloco foi quebrado
	 * @param pos a posição do bloco
	 * @param state o bloco quebrado
	 * @return o exp ganho após a maldição ser ativa
	 */
	public boolean doBlockBreak(PlayerEntity entity, World level, BlockPos pos, BlockState state){
		return true;
	}

	/**
	 * Chamado quando o usuário é alvo de um ritual
	 *
	 * @param entity o usuário
	 * @param world o world onde o ritual foi conjurado
	 * @param ritual o ritual conjurado
	 * @param caster o usuário do ritual
	 * @return true se o ritual foi utilizado
	 */
	public boolean isRitualTarget(LivingEntity entity, World world, AbstractRitual ritual, LivingEntity caster){
		return true;
	}

	/**
	 * Chamado quando o usuário conjura um ritual
	 *
	 * @param entity o usuário
	 * @param world o world onde o ritual foi conjurado
	 * @param ritual o ritual conjurado
	 * @return true se o ritual foi utilizado
	 */
	public boolean isRitualCaster(LivingEntity entity, World world, AbstractRitual ritual){
		return true;
	}

	/**
	 * Mantenha os usos máximos como 0 para uma maldição permanente.
	 * @return quantos usos essa maldição pode ter.
	 */
	public int getMaxUses() {
		return 0;
	}

	public boolean isTemporary(){
		return getMaxUses() > 0;
	}

	public int getDamageProtection(DamageSource pSource) {
		return 0;
	}

	public int getDamageBonus() {
		return 0;
	}

	public float getBreakSpeedBonus() {
		return 0;
	}

	public Map<EntityAttributeModifier, EntityAttribute> getAttributeModifiers(){
		return new HashMap<>();
	}
}
