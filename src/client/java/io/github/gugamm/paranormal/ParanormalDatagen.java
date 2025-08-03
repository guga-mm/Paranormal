package io.github.gugamm.paranormal;

import io.github.gugamm.paranormal.api.ParanormalAttribute;
import io.github.gugamm.paranormal.item.ModItemTags;
import io.github.gugamm.paranormal.registry.ModBlockRegistry;
import io.github.gugamm.paranormal.registry.ModItemGroupRegistry;
import io.github.gugamm.paranormal.registry.ModItemRegistry;
import io.github.gugamm.paranormal.registry.ModRitualRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static io.github.gugamm.paranormal.Paranormal.MODID;

public class ParanormalDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelProvider::new);
		pack.addProvider(BlockTagsProvider::new);
		pack.addProvider(EnUsLangProvider::new);
		pack.addProvider(PtBrLangProvider::new);
		pack.addProvider(ItemTagProvider::new);
	}

	public static class BlockTagsProvider extends FabricTagProvider.BlockTagProvider {
		public BlockTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> wrapperFuture) {
			super(output, wrapperFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlockRegistry.TRANSCENDANCE_ALTAR);
			getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlockRegistry.TRANSCENDANCE_ALTAR);
			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlockRegistry.BLOOD_TABLE);
			getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlockRegistry.BLOOD_TABLE);
			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlockRegistry.DEATH_TABLE);
			getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlockRegistry.DEATH_TABLE);
			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlockRegistry.ENERGY_TABLE);
			getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlockRegistry.ENERGY_TABLE);
			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlockRegistry.WISDOM_TABLE);
			getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlockRegistry.WISDOM_TABLE);
		}
	}

	public static class ModelProvider extends FabricModelProvider {
		public ModelProvider(FabricDataOutput output) {
			super(output);
		}

		private Model blockItem(Block block){
			return new Model(Optional.of(Identifier.of(MODID, ModelIds.getBlockModelId(block).getPath())), Optional.empty());
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			itemModelGenerator.register(ModItemRegistry.ASHES, Models.GENERATED);
			itemModelGenerator.register(ModItemRegistry.ORGAN, Models.GENERATED);
			itemModelGenerator.register(ModItemRegistry.TRANSCENDANCE_ALTAR, blockItem(ModBlockRegistry.TRANSCENDANCE_ALTAR));
			itemModelGenerator.register(ModItemRegistry.BLOOD_TABLE, blockItem(ModBlockRegistry.BLOOD_TABLE));
			itemModelGenerator.register(ModItemRegistry.DEATH_TABLE, blockItem(ModBlockRegistry.DEATH_TABLE));
			itemModelGenerator.register(ModItemRegistry.WISDOM_TABLE, blockItem(ModBlockRegistry.WISDOM_TABLE));
			itemModelGenerator.register(ModItemRegistry.ENERGY_TABLE, blockItem(ModBlockRegistry.ENERGY_TABLE));
		}
	}

	public static class EnUsLangProvider extends FabricLanguageProvider {

		protected EnUsLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, "en_us", registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup provider, TranslationBuilder translationBuilder) {
			translationBuilder.add(ModItemRegistry.ASHES, "Ashes");
			translationBuilder.add(ModItemRegistry.ORGAN, "Organ");
			translationBuilder.add(ModBlockRegistry.TRANSCENDANCE_ALTAR, "Transcendance Altar");
			translationBuilder.add(ModItemGroupRegistry.PARANORMAL.toString(), "Paranormal");
			translationBuilder.add("paranormal.screen.attributes_screen", "Attributes");
			translationBuilder.add("paranormal.screen.attributes_screen.attPoints", "Attribute Points");
			translationBuilder.add(ParanormalAttribute.STRENGTH.name, "Strength");
			translationBuilder.add(ParanormalAttribute.VIGOR.name, "Vigor");
			translationBuilder.add(ParanormalAttribute.PRESENCE.name, "Presence");

			// Rituals
			translationBuilder.add(ModRitualRegistry.HEALING.getTranslationKey(), "Healing");
			translationBuilder.add(ModRitualRegistry.SKINNING.getTranslationKey(), "Skinning");
			translationBuilder.add(ModRitualRegistry.LIGHT.getTranslationKey(), "Light");
		}
	}

	public static class PtBrLangProvider extends FabricLanguageProvider {

		protected PtBrLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, "pt_br", registryLookup);
		}

		@Override
		public void generateTranslations(RegistryWrapper.WrapperLookup provider, TranslationBuilder translationBuilder) {
			translationBuilder.add(ModItemRegistry.ASHES, "Cinzas");
			translationBuilder.add(ModItemRegistry.ORGAN, "Órgao");
			translationBuilder.add(ModBlockRegistry.TRANSCENDANCE_ALTAR, "Altar de Transcendência");
			translationBuilder.add(ModItemGroupRegistry.PARANORMAL.toString(), "Paranormal");
			translationBuilder.add("paranormal.screen.attributes_screen", "Atributos");
			translationBuilder.add(ParanormalAttribute.STRENGTH.name, "Força");
			translationBuilder.add(ParanormalAttribute.VIGOR.name, "Vigor");
			translationBuilder.add(ParanormalAttribute.PRESENCE.name, "Presença");

			// Rituals
			translationBuilder.add(ModRitualRegistry.HEALING.getTranslationKey(), "Cicatrização");
			translationBuilder.add(ModRitualRegistry.SKINNING.getTranslationKey(), 	"Descarnar");
			translationBuilder.add(ModRitualRegistry.LIGHT.getTranslationKey(), "Luz");
		}
	}

	public static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
		public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			getOrCreateTagBuilder(ModItemTags.BLOOD_FUEL).add(Items.BEEF, Items.PORKCHOP, Items.MUTTON, Items.RABBIT, ModItemRegistry.ORGAN);
			getOrCreateTagBuilder(ModItemTags.ENERGY_FUEL).add(Items.LAPIS_LAZULI, Items.AMETHYST_SHARD, Items.PRISMARINE_SHARD, Items.EMERALD, Items.QUARTZ);
			getOrCreateTagBuilder(ModItemTags.DEATH_FUEL).add(Items.BONE, Items.SAND, Items.RED_SAND, Items.GRAVEL, ModItemRegistry.ASHES);
			getOrCreateTagBuilder(ModItemTags.WISDOM_FUEL).add(Items.GLASS, Items.GOLD_INGOT, Items.GLOWSTONE_DUST, Items.PAPER, Items.CANDLE);
		}
	}
}
