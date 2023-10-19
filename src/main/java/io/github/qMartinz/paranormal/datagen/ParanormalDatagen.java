package io.github.qMartinz.paranormal.datagen;

import io.github.qMartinz.paranormal.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.Models;

public class ParanormalDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelProvider::new);
		pack.addProvider(EnUsLangProvider::new);
		pack.addProvider(PtBrLangProvider::new);
	}

	public static class ModelProvider extends FabricModelProvider {
		public ModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			itemModelGenerator.register(ItemRegistry.ASHES, Models.SINGLE_LAYER_ITEM);
			itemModelGenerator.register(ItemRegistry.ORGAN, Models.SINGLE_LAYER_ITEM);
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
		}
	}
}
