package io.github.qMartinz.paranormal.datagen;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.api.ParanormalAttribute;
import io.github.qMartinz.paranormal.registry.ModBlockRegistry;
import io.github.qMartinz.paranormal.registry.ModItemGroupRegistry;
import io.github.qMartinz.paranormal.registry.ModItemRegistry;
import io.github.qMartinz.paranormal.registry.ModRitualRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.Model;
import net.minecraft.data.client.model.ModelIds;
import net.minecraft.data.client.model.Models;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

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
		public BlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(HolderLookup.Provider arg) {
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
			itemModelGenerator.register(ModItemRegistry.ASHES, Models.SINGLE_LAYER_ITEM);
			itemModelGenerator.register(ModItemRegistry.ORGAN, Models.SINGLE_LAYER_ITEM);
			itemModelGenerator.register(ModItemRegistry.TRANSCENDANCE_ALTAR, blockItem(ModBlockRegistry.TRANSCENDANCE_ALTAR));
			itemModelGenerator.register(ModItemRegistry.BLOOD_TABLE, blockItem(ModBlockRegistry.BLOOD_TABLE));
			itemModelGenerator.register(ModItemRegistry.DEATH_TABLE, blockItem(ModBlockRegistry.DEATH_TABLE));
			itemModelGenerator.register(ModItemRegistry.WISDOM_TABLE, blockItem(ModBlockRegistry.WISDOM_TABLE));
			itemModelGenerator.register(ModItemRegistry.ENERGY_TABLE, blockItem(ModBlockRegistry.ENERGY_TABLE));
		}
	}

	public static class EnUsLangProvider extends FabricLanguageProvider {

		protected EnUsLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(dataOutput, "en_us", registryLookup);
		}

		@Override
		public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
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

		protected PtBrLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(dataOutput, "pt_br", registryLookup);
		}

		@Override
		public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
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
		public static final TagKey<Item> BLOOD_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "blood_fuel"));
		public static final TagKey<Item> ENERGY_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "energy_fuel"));
		public static final TagKey<Item> DEATH_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "death_fuel"));
		public static final TagKey<Item> WISDOM_FUEL = TagKey.of(RegistryKeys.ITEM, Identifier.of(MODID, "wisdom_fuel"));

		public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(HolderLookup.Provider arg) {
			getOrCreateTagBuilder(BLOOD_FUEL).add(Items.BEEF, Items.PORKCHOP, Items.MUTTON, Items.RABBIT, ModItemRegistry.ORGAN);
			getOrCreateTagBuilder(ENERGY_FUEL).add(Items.LAPIS_LAZULI, Items.AMETHYST_SHARD, Items.PRISMARINE_SHARD, Items.EMERALD, Items.QUARTZ);
			getOrCreateTagBuilder(DEATH_FUEL).add(Items.BONE, Items.SAND, Items.RED_SAND, Items.GRAVEL, ModItemRegistry.ASHES);
			getOrCreateTagBuilder(WISDOM_FUEL).add(Items.GLASS, Items.GOLD_INGOT, Items.GLOWSTONE_DUST, Items.PAPER, Items.CANDLE);
		}
	}
}
