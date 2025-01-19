package net.pokepandamon.strife3.items.custom;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class MossBucket extends BlockItem {

    public MossBucket(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (context.getWorld().isInBuildLimit(context.getBlockPos()) && (context.getWorld().isAir(context.getBlockPos()) || context.getWorld().isWater(context.getBlockPos()))) {
            if (!context.getWorld().isClient) {
                context.getWorld().setBlockState(context.getBlockPos(), this.getBlock().getDefaultState(), 3);
            }
            context.getPlayer().setStackInHand(context.getHand(), Items.BUCKET.getDefaultStack());
            return ActionResult.success(context.getWorld().isClient());
        }
        return ActionResult.FAIL;
    }
}
