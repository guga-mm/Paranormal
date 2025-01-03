package io.github.qMartinz.paranormal.entity.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.entity.CorpseEntity;
import io.github.qMartinz.paranormal.entity.model.VillagerCorpseModel;
import io.github.qMartinz.paranormal.registry.ModModelLayerRegistry;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class VillagerCorpseRenderer extends EntityRenderer<CorpseEntity> {
	public static final Identifier TEXTURE = Identifier.of(Paranormal.MODID, "textures/entity/villager_corpse.png");
	protected VillagerCorpseModel model;

	public VillagerCorpseRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		model = new VillagerCorpseModel(ctx.getPart(ModModelLayerRegistry.VILLAGER_CORPSE));
	}

	@Override
	public void render(CorpseEntity entity, float yaw, float tickDelta, MatrixStack matrices,
					   VertexConsumerProvider vertexConsumers, int light) {
		VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(TEXTURE), false, false);
		this.model.method_2828(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV, 1);
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(CorpseEntity entity) {
		return TEXTURE;
	}
}
