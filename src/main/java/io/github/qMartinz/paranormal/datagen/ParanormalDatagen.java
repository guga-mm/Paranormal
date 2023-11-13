package io.github.qMartinz.paranormal.datagen;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.registry.BlockRegistry;
import io.github.qMartinz.paranormal.registry.ItemGroupRegistry;
import io.github.qMartinz.paranormal.registry.ItemRegistry;
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
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ParanormalDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelProvider::new);
		pack.addProvider(BlockTagsProvider::new);
		pack.addProvider(EnUsLangProvider::new);
		pack.addProvider(PtBrLangProvider::new);
	}

	public static class BlockTagsProvider extends FabricTagProvider.BlockTagProvider {
		public BlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(HolderLookup.Provider arg) {
			getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(BlockRegistry.TRANSCENDANCE_ALTAR);
			getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(BlockRegistry.TRANSCENDANCE_ALTAR);
		}
	}

	public static class ModelProvider extends FabricModelProvider {
		public ModelProvider(FabricDataOutput output) {
			super(output);
		}

		private Model blockItem(Block block){
			return new Model(Optional.of(new Identifier(Paranormal.MODID, ModelIds.getBlockModelId(block).getPath())), Optional.empty());
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			itemModelGenerator.register(ItemRegistry.ASHES, Models.SINGLE_LAYER_ITEM);
			itemModelGenerator.register(ItemRegistry.ORGAN, Models.SINGLE_LAYER_ITEM);
			itemModelGenerator.register(ItemRegistry.TRANSCENDANCE_ALTAR, blockItem(BlockRegistry.TRANSCENDANCE_ALTAR));
		}
	}

	public static class EnUsLangProvider extends FabricLanguageProvider {

		protected EnUsLangProvider(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generateTranslations(TranslationBuilder translationBuilder) {
			translationBuilder.add(ItemRegistry.ASHES, "Ashes");
			translationBuilder.add(ItemRegistry.ORGAN, "Organ");
			translationBuilder.add(BlockRegistry.TRANSCENDANCE_ALTAR, "Transcendance Altar");
			translationBuilder.add(ItemGroupRegistry.PARANORMAL, "Paranormal");
			translationBuilder.add("paranormal.screen.attributes_screen", "Attributes");
			translationBuilder.add("paranormal.screen.attributes_screen.attPoints", "Attribute Points");
			translationBuilder.add("paranormal.screen.attributes_screen.increase_attribute_0", "Increase Strength");
			translationBuilder.add("paranormal.screen.attributes_screen.increase_attribute_1", "Increase Vigor");
			translationBuilder.add("paranormal.screen.attributes_screen.increase_attribute_2", "Increase Presence");
			translationBuilder.add("paranormal.screen.attributes_screen.attribute_0", "Strength");
			translationBuilder.add("paranormal.screen.attributes_screen.attribute_1", "Vigor");
			translationBuilder.add("paranormal.screen.attributes_screen.attribute_2", "Presence");
		}
	}

	public static class PtBrLangProvider extends FabricLanguageProvider {

		protected PtBrLangProvider(FabricDataOutput dataOutput) {
			super(dataOutput, "pt_br");
		}

		@Override
		public void generateTranslations(TranslationBuilder translationBuilder) {
			translationBuilder.add(ItemRegistry.ASHES, "Cinzas");
			translationBuilder.add(ItemRegistry.ORGAN, "Órgao");
			translationBuilder.add(BlockRegistry.TRANSCENDANCE_ALTAR, "Altar de Transcendência");
			translationBuilder.add(ItemGroupRegistry.PARANORMAL, "Paranormal");
			translationBuilder.add("paranormal.screen.attributes_screen", "Atributos");
			translationBuilder.add("paranormal.screen.attributes_screen.attPoints", "Pontos de Atributo");
			translationBuilder.add("paranormal.screen.attributes_screen.increase_attribute_0", "Aumentar Força");
			translationBuilder.add("paranormal.screen.attributes_screen.increase_attribute_1", "Aumentar Vigor");
			translationBuilder.add("paranormal.screen.attributes_screen.increase_attribute_2", "Aumentar Presença");
			translationBuilder.add("paranormal.screen.attributes_screen.attribute_0", "Força");
			translationBuilder.add("paranormal.screen.attributes_screen.attribute_1", "Vigor");
			translationBuilder.add("paranormal.screen.attributes_screen.attribute_2", "Presença");
		}
	}
}
