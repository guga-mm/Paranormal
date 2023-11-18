package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.entity.model.VillagerCorpseModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static io.github.qMartinz.paranormal.Paranormal.MODID;

public class ModModelLayerRegistry {
	public static final EntityModelLayer VILLAGER_CORPSE = register("villager_corpse");

	private static EntityModelLayer register(String id){
		return new EntityModelLayer(new Identifier(Paranormal.MODID, id), "main");
	}

	public static void registerLayers(){
		EntityModelLayerRegistry.registerModelLayer(VILLAGER_CORPSE, VillagerCorpseModel::getTexturedModelData);
	}

	public static void init(){
		Paranormal.LOGGER.info("Registering model layers for " + MODID);
		registerLayers();
	}
}
