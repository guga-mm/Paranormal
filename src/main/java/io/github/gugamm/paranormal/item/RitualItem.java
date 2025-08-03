package io.github.gugamm.paranormal.item;

import io.github.gugamm.paranormal.api.ParanormalAttribute;
import io.github.gugamm.paranormal.api.rituals.AbstractRitual;
import io.github.gugamm.paranormal.registry.ModComponentsRegistry;
import io.github.gugamm.paranormal.util.CommonText;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RitualItem extends Item {
	public final AbstractRitual ritual;

	public RitualItem(AbstractRitual ritual) {
		super(new Settings().maxCount(1).rarity(ritual.getRarity()));
		this.ritual = ritual;
	}

	public AbstractRitual getRitual() {
		return ritual;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		if (stack.getOrDefault(ModComponentsRegistry.RITUAL_LEARNED, false)){
			tooltip.add(CommonText.RITUAL_LEARNED.copy().formatted(Formatting.GRAY));
		} else {
			tooltip.add(Text.translatable(CommonText.RITUAL_UNKNOWN + ritual.getElement().name).copy().formatted(Formatting.GRAY));
			if (ritual.getPresenceRequired() > 0)
				tooltip.add(CommonText.RITUAL_REQUIRES.copy()
					.append(ParanormalAttribute.PRESENCE.getDisplayName().getString() + " " + ritual.getPresenceRequired()).formatted(Formatting.GRAY));
		}
	}

	@Override
	public Text getName(ItemStack stack) {
		if (stack.getOrDefault(ModComponentsRegistry.RITUAL_LEARNED, false)){
			Formatting formatting = Formatting.WHITE;
			switch (ritual.getElement()){
				case BLOOD -> formatting = Formatting.DARK_RED;
				case WISDOM -> formatting = Formatting.GOLD;
				case ENERGY -> formatting = Formatting.DARK_PURPLE;
				case DEATH -> formatting = Formatting.DARK_GRAY;
			}
			return this.getRitual().getDisplayName().copyContentOnly().formatted(formatting);
		} else return super.getName(stack);
	}
}
