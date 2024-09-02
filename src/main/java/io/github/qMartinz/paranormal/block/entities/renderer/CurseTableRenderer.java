package io.github.qMartinz.paranormal.block.entities.renderer;

import io.github.qMartinz.paranormal.Paranormal;
import io.github.qMartinz.paranormal.block.entities.CurseTableEntity;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class CurseTableRenderer implements BlockEntityRenderer<CurseTableEntity> {
	public CurseTableRenderer(BlockEntityRendererFactory.Context ctx) {
	}

	@Override
	public void render(CurseTableEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		float yOffset = 1.3f;
		float xOffset = 0.5f;
		float zOffset = 0.5f;
		ItemStack item = entity.getItem();
		ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

		if (!item.isEmpty()) {
			matrices.push();
			matrices.translate(xOffset, yOffset, zOffset);
			matrices.scale(0.5f, 0.5f, 0.5f);
			matrices.multiply(Axis.Y_POSITIVE.rotationDegrees((System.currentTimeMillis() / 10f) % 360));
			Paranormal.LOGGER.info("rendering: " + item);
			// TODO fix rendering
			itemRenderer.renderItem(item,
					ModelTransformationMode.FIXED,
					getLightLevel(entity.getWorld(), entity.getPos()),
					OverlayTexture.DEFAULT_UV,
					matrices,
					vertexConsumers,
					entity.getWorld(),
					1);
			matrices.pop();
		}
	}

	private int getLightLevel(World world, BlockPos pos) {
		int bLight = world.getLightLevel(LightType.BLOCK, pos);
		int sLight = world.getLightLevel(LightType.SKY, pos);
		return LightmapTextureManager.pack(bLight, sLight);
	}
}
