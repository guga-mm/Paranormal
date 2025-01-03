package io.github.qMartinz.paranormal.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.qMartinz.paranormal.Paranormal;
import net.minecraft.component.DataComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModComponentsRegistry {
	public static final DataComponentType<Boolean> RITUAL_LEARNED = Registry.register(
		Registries.DATA_COMPONENT_TYPE,
		Identifier.of(Paranormal.MODID, "ritual_learned"),
		DataComponentType.<Boolean>builder().codec(Codec.BOOL).build()
	);

	public static final DataComponentType<CurseComponent> CURSE = Registry.register(
		Registries.DATA_COMPONENT_TYPE,
		Identifier.of(Paranormal.MODID, "curse"),
		DataComponentType.<CurseComponent>builder().codec(CurseComponent.CODEC).build()
	);

	public static final DataComponentType<List<CurseComponent>> ITEM_CURSES = Registry.register(
		Registries.DATA_COMPONENT_TYPE,
		Identifier.of(Paranormal.MODID, "item_curses"),
		DataComponentType.<List<CurseComponent>>builder().codec(Codec.list(CurseComponent.CODEC)).build()
	);

	public record CurseComponent(String id, int uses) {
		public static final Codec<CurseComponent> CODEC = RecordCodecBuilder.create(builder -> {
			return builder.group(
				Codec.STRING.fieldOf("id").forGetter(CurseComponent::id),
				Codec.INT.fieldOf("uses").forGetter(CurseComponent::uses)
			).apply(builder, CurseComponent::new);
		});
	}

	public static void initialize() {
		Paranormal.LOGGER.info("Registering components for " + Paranormal.MODID);
	}
}
