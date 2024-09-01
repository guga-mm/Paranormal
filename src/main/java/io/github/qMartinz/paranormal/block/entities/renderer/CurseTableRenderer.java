package io.github.qMartinz.paranormal.block.entities.renderer;

import io.github.qMartinz.paranormal.block.entities.CurseTableEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;

public class CurseTableRenderer implements BlockEntityRenderer<CurseTableEntity> {

	private final ItemRenderer itemRenderer;

	public CurseTableRenderer(BlockEntityRendererFactory.Context ctx) {
		itemRenderer = ctx.getItemRenderer();
	}

	@Override
	public void render(CurseTableEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		float yOffset = 1.3f;
		float xOffset = 0.5f;
		float zOffset = 0.5f;

		matrices.push();
		matrices.translate(xOffset, yOffset, zOffset);
		matrices.scale(0.5f, 0.5f, 0.5f);
		matrices.multiply(Axis.Y_POSITIVE.rotationDegrees((System.currentTimeMillis() / 10f) % 360));
		itemRenderer.renderItem(new ItemStack(Blocks.AIR),
				ModelTransformationMode.FIXED,
				light,
				overlay,
				matrices,
				vertexConsumers,
				entity.getWorld(),
				(int) entity.getPos().asLong());
		matrices.pop();
	}
}
