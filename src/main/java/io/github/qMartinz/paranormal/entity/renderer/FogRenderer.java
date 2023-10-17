package io.github.qMartinz.paranormal.entity.renderer;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.entity.FogEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FogRenderer extends EntityRenderer<FogEntity> {
	public static final Identifier TEXTURE = new Identifier(Paranormal.MODID, "textures/entity/fog.png");

	public FogRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public void render(FogEntity entity, float yaw, float tickDelta, MatrixStack matrices,
					   VertexConsumerProvider vertexConsumers, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(FogEntity entity) {
		return TEXTURE;
	}
}
