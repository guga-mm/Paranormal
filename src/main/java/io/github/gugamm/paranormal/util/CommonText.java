package io.github.gugamm.paranormal.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public class CommonText {
	public static final Text NEW_LINE = Text.literal("\n");
	public static final Text PEX_TITLE = Text.translatable("ordemparanormal.pex.title");
	public static final Text PEX_ABBREVIATION = Text.translatable("ordemparanormal.pex.abbreviation");
	public static final Text HEALTH_POINTS = Text.translatable("ordemparanormal.health_points");
	public static final Text occult_POINTS = Text.translatable("ordemparanormal.occult_points");
	public static final Text occult_POINTS_FULL_NAME = Text.translatable("ordemparanormal.occult_points.full_name");
	public static final Text POWER_POINTS = Text.translatable("ordemparanormal.pex.power_points");
	public static final Text ACTIVE_POWER = Text.translatable("ordemparanormal.power.active");
	public static final Text PASSIVE_POWER = Text.translatable("ordemparanormal.power.passive");
	public static final Text OWNED_POWER = Text.translatable("ordemparanormal.power.owned");
	public static final Text REQUISITES = Text.translatable("ordemparanormal.power.requisites");
	public static final Text ATTRIBUTE_POINTS = Text.translatable("ordemparanormal.pex.attribute_points");
	public static final Text RITUALS = Text.translatable("ordemparanormal.rituals");
	public static final Text INGREDIENT = Text.translatable("ordemparanormal.ritual.ingredient");
	public static final Text RITUAL_LEARNED = Text.translatable("ordemparanormal.ritual_item.ritual_learned");
	public static final Text RITUAL_REQUIRES = Text.translatable("ordemparanormal.ritual_item.requires");
	public static final Text CONSUMES = Text.translatable("ordemparanormal.ritual.consumes");
	public static final String RITUAL_UNKNOWN = "ordemparanormal.ritual_item.ritual_unknown.";

	public static Text joinLines(Text... pLines) {
		return joinLines(Arrays.asList(pLines));
	}

	public static Text joinLines(Collection<? extends Text> lines) {
		return joinLines(lines, NEW_LINE, Function.identity());
	}

	public static <T> MutableText joinLines(Collection<? extends T> lines, Text separator, Function<T, Text> textExtractor) {
		if (lines.isEmpty()) {
			return Text.empty();
		} else if (lines.size() == 1) {
			return textExtractor.apply(lines.iterator().next()).copy();
		} else {
			MutableText mutablecomponent = Text.empty();
			boolean flag = true;

			for(T t : lines) {
				if (!flag) {
					mutablecomponent.append(separator);
				}

				mutablecomponent.append(textExtractor.apply(t));
				flag = false;
			}

			return mutablecomponent;
		}
	}
}
