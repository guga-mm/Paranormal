package io.github.qMartinz.paranormal.api.powers;

import io.github.qMartinz.paranormal.api.ParanormalAttribute;
import io.github.qMartinz.paranormal.api.ParanormalElement;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParanormalPower {
	private final ParanormalElement element;
	private final int pexRequired;
	private final int[] attributesRequired = new int[]{0, 0, 0};
	private final Set<ParanormalPower> powerRequirements;

	public ParanormalPower(ParanormalElement element, int pexRequired, int strengthRequired, int vigorRequired, int presenceRequired, ParanormalPower... powerRequirements) {
		this.element = element;
		this.pexRequired = pexRequired;
		this.attributesRequired[ParanormalAttribute.STRENGTH.index] = strengthRequired;
		this.attributesRequired[ParanormalAttribute.VIGOR.index] = vigorRequired;
		this.attributesRequired[ParanormalAttribute.PRESENCE.index] = presenceRequired;
		this.powerRequirements = new HashSet<>(List.of(powerRequirements));
	}

	public Identifier getId() {
		return PowerRegistry.getId(this);
	}

	public ParanormalElement getElement() {
		return element;
	}

	public int getPexRequired() {
		return pexRequired;
	}

	/**
	 * <p>0 = str
	 * <p>1 = vig
	 * <p>2 = pre
	 */
	public int[] getAttributesRequired() {
		return attributesRequired;
	}

	/**
	 * <p>0 = str
	 * <p>1 = vig
	 * <p>2 = pre
	 */
	public int getAttributeRequired(ParanormalAttribute attribute) {
		return attributesRequired[attribute.index];
	}

	public Collection<ParanormalPower> getPowerRequirements() {
		return powerRequirements;
	}

	public String getTranslationKey(){
		return getId().getNamespace() + ".power." + getId().getPath();
	}

	public Text getDisplayName(){ return Text.translatable(getTranslationKey()); }

	public MutableText getDescription(){
		return Text.translatable(this.getTranslationKey() + ".description").formatted(Formatting.WHITE);
	}

	/**
	 * Chamado quando o poder é adicionado ao jogador por um {@link io.github.qMartinz.paranormal.client.screen.elements.button.PowerSlotButton}
	 *
	 * @param player o jogador que adquiriu o poder
	 */
	public void onAdded(PlayerEntity player){}

	/**
	 * Chamado a cada tick
	 *
	 * @param player o jogador que possui o poder
	 */
	public void onTick(PlayerEntity player){}

	/**
	 * Chamado quando o usuário ataca
	 *
	 * @param player quem atacou
	 * @param amount quanto dano foi causado
	 * @param target o alvo do ataque
	 * @param source o tipo de dano causado
	 * @return verdadeiro caso o dano seja causado
	 */
	public boolean onAttack(PlayerEntity player, float amount, Entity target, DamageSource source){
		return true;
	}

	/**
	 * Chamado quando o usuário sofre dano
	 *
	 * @param player quem sofreu dano
	 * @param amount quanto dano foi sofrido
	 * @param attacker quem causou o dano
	 * @param source o tipo de dano causado
	 * @return verdadeiro caso o dano seja tomado
	 */
	public boolean onHurt(PlayerEntity player, float amount, @Nullable Entity attacker, DamageSource source){
		return true;
	}

	/**
	 * Chamado quando o usuário usa um item
	 *
	 * @param world o mundo em que o item foi utilizado
	 * @param player quem utilizou
	 * @param hand a mão em que o item está
	 * @param result o resultado original da ação
	 * @return o resultado da ação
	 */
	public TypedActionResult<ItemStack> onUseItem(World world, PlayerEntity player, Hand hand, TypedActionResult<ItemStack> result){
		return result;
	}

	/**
	 * Chamado quando o usuário termina de usar algum item
	 *
	 * @param stack o item utilizado
	 * @param world o mundo em que o item foi utilizado
	 * @param user quem utilizou o item
	 * @return o item resultante do uso
	 */
	public ItemStack onFinishUseItem(ItemStack stack, World world, LivingEntity user){
		return stack;
	}

	/**
	 * Chamado a cada tick quando o usuário está usando um item
	 *
	 * @param world o mundo em que o item está sendo utilizado
	 * @param user quem utilizou o item
	 * @param stack o item sendo utilizado
	 * @param remainingUseTicks a duração restante da utilização
	 */
	public void onTickUseItem(World world, LivingEntity user, ItemStack stack, int remainingUseTicks){}

	/**
	 * Chamado quando o usuário bloqueia um ataque com um escudo
	 *
	 * @param player quem bloqueou
	 * @param attacker quem atacou
	 */
	public void onShieldBlock(PlayerEntity player, @Nullable Entity attacker){}

	/**
	 * Chamado quando o usuário ataca, mas seu ataque é bloqueado por um escudo
	 *
	 * @param player quem atacou
	 * @param target quem bloqueou
	 */
	public void onAttackBlocked(PlayerEntity player, LivingEntity target){}

	/**
	 * Chamado quando o usuário quebra um bloco
	 *
	 * @param player quem quebrou
	 * @param world o world onde o bloco foi quebrado
	 * @param pos a posição do bloco
	 * @param state o bloco quebrado
	 * @param blockEntity a entidade do bloco
	 * @return o exp ganho após o poder ser ativo
	 */
	public boolean onBlockBreak(PlayerEntity player, World world, BlockPos pos, BlockState state, BlockEntity blockEntity){
		return true;
	}

	/**
	 * Chamado quando o usuário recebe exp
	 *
	 * @param player quem recebeu o exp
	 * @param exp o exp ganho
	 * @return o exp ganho após o poder ser ativo
	 */
	public int onXPChange(PlayerEntity player, int exp){
		return exp;
	}

	/**
	 * Chamado quando o usuário recebe levels
	 *
	 * @param player quem recebeu os levels
	 * @param levels os levels ganho
	 * @return os levels ganho após o poder ser ativo
	 */
	public int onXPLevelChange(PlayerEntity player, int levels){
		return levels;
	}
}
