package io.github.qMartinz.paranormal.entity.renderer;

import io.github.qMartinz.paranormal.entity.RitualProjectile;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RitualProjectileRenderer extends EntityRenderer<RitualProjectile> {

	public RitualProjectileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public void render(RitualProjectile entity, float yaw, float tickDelta, MatrixStack matrices,
					   VertexConsumerProvider vertexConsumers, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(RitualProjectile entity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}
}
