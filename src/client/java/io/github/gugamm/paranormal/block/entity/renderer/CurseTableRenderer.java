package io.github.gugamm.paranormal.block.entity.renderer;

import io.github.gugamm.paranormal.block.entities.CurseTableEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class CurseTableRenderer implements BlockEntityRenderer<CurseTableEntity> {
	public CurseTableRenderer(BlockEntityRendererFactory.Context ctx) {
	}

	@Override
	public void render(CurseTableEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		float yOffset = 1.3f;
		float xOffset = 0.5f;
		float zOffset = 0.5f;
		ItemStack item = entity.getItem();

		if (!item.isEmpty()) {
			matrices.push();
			matrices.translate(xOffset, yOffset, zOffset);
			matrices.scale(0.5f, 0.5f, 0.5f);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 4));
			// TODO fix rendering
			int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
			MinecraftClient.getInstance().getItemRenderer().renderItem(item, ModelTransformationMode.FIXED, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
			matrices.pop();
		}
	}

	private int getLightLevel(World world, BlockPos pos) {
		int bLight = world.getLightLevel(LightType.BLOCK, pos);
		int sLight = world.getLightLevel(LightType.SKY, pos);
		return LightmapTextureManager.pack(bLight, sLight);
	}
}
