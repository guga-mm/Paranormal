package io.github.qMartinz.paranormal.entity.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.qMartinz.paranormal.entity.CorpseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;


public class VillagerCorpseModel extends EntityModel<CorpseEntity> {
	private final ModelPart body;

	// Criar modelo
	public VillagerCorpseModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	// Definir partes do modelo
	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData body = modelPartData.addChild("body",
				ModelPartBuilder.create().uv(0, 20).cuboid(-11.0F, -15.5F, 3.25F, 23.0F, 26.0F, 0.0F,
						new Dilation(0.0F)),
				ModelTransform.of(-1.0F, 3.3F, 5.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head",
				ModelPartBuilder.create().uv(0, 0)
						.cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)).uv(0, 48)
						.cuboid(-3.6F, -9.9F, -3.6F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, -9.5F, 0.0F, 0.0F, 0.0F, 0.5236F));

		return TexturedModelData.of(modelData, 46, 64);
	}

	@Override
	public void setAngles(CorpseEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void method_2828(MatrixStack matrices, VertexConsumer vertexConsumer, int i, int j, int k) {
		body.method_22699(matrices, vertexConsumer, i, j, k);
	}
}
