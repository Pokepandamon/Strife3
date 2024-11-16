package net.pokepandamon.strife3.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.pokepandamon.strife3.Strife3;
import net.pokepandamon.strife3.entity.custom.GreaterVerluerEntity;

public class GreaterVerluerRenderer extends MobEntityRenderer<GreaterVerluerEntity, GreaterVerluerModel<GreaterVerluerEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Strife3.MOD_ID, "textures/entity/greater_verluer.png");

    public GreaterVerluerRenderer(EntityRendererFactory.Context context) {
        super(context, new GreaterVerluerModel<>(context.getPart(Strife3ModelLayers.GREATER_VERLUER)), 0.6f);
    }

    @Override
    public Identifier getTexture(GreaterVerluerEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GreaterVerluerEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(2F,2F,2F);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
