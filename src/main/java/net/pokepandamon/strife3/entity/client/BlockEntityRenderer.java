package net.pokepandamon.strife3.entity.client;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.pokepandamon.strife3.entity.custom.BlockEntity;

public class BlockEntityRenderer extends EntityRenderer<BlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public BlockEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
    }

    @Override
    public void render(BlockEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        BlockState blockState;
        if(entity.getBlockState() != null) {
            blockState = entity.getBlockState(); // Use the entity's current block state
        }else{
            blockState = Blocks.STONE.getDefaultState();
        }
        blockRenderManager.renderBlockAsEntity(
                blockState, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV
        );

        if(entity.interpolating()){
            double interpolatedX = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
            double interpolatedY = MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
            double interpolatedZ = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());

            // Use interpolated positions for rendering
            matrices.push();
            matrices.translate(interpolatedX, interpolatedY, interpolatedZ);
        }else{
            matrices.push();

            // Adjust positioning, scale, or rotation here if necessary
            matrices.translate(0, 0.0, 0); // Adjust for the block's position
        }

        // Render your entity here
        //matrices.pop();

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BlockEntity entity) {
        // This isn't used since we're rendering a block directly
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}