package net.pokepandamon.strife3.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.pokepandamon.strife3.items.ModItems;

public class MossBlock extends Block {
    public static final MapCodec<MossBlock> CODEC = createCodec(MossBlock::new);
    public static final IntProperty DENSITY;
    //private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625); // Center pillar shape

    static{
        DENSITY = IntProperty.of("density", 1, 4);
    }

    @Override
    public MapCodec<? extends MossBlock> getCodec() {
        return CODEC;
    }

    public MossBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DENSITY, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DENSITY);
    }

    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //if(stack.getItem().equals(ModItems.MOSS_BUCKET) || stack.getItem().equals(Items.BUCKET)){
        if (stack.getItem().equals(Items.BUCKET)) {
            if (state.get(DENSITY).intValue() == 1) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }else if (state.get(DENSITY).intValue() == 2) {
                world.setBlockState(pos, state.with(DENSITY, 1));
            } else if (state.get(DENSITY).intValue() == 3) {
                world.setBlockState(pos, state.with(DENSITY, 2));
            } else {
                world.setBlockState(pos, state.with(DENSITY, 3));
            }
            //player.getStackInHand() = ModItems.MOSS_BUCKET;
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, ModItems.MOSS_BUCKET.getDefaultStack()));
            return ItemActionResult.success(world.isClient());
        }else if(stack.getItem().equals(ModItems.MOSS_BUCKET)){
        //}else if(stack.getItem().equals(ModItems.ALLOY_AXE)){
            if (state.get(DENSITY).intValue() == 1) {
                world.setBlockState(pos, state.with(DENSITY, 2));
            } else if(state.get(DENSITY).intValue() == 2){
                world.setBlockState(pos, state.with(DENSITY, 3));
            }else{
                world.setBlockState(pos, state.with(DENSITY, 4));
            }
            //player.getStackInHand() = ModItems.MOSS_BUCKET;
            if(state.get(DENSITY).intValue() != 4) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, Items.BUCKET.getDefaultStack()));
            }
            return ItemActionResult.success(world.isClient());
        } else{
            return ItemActionResult.FAIL;
        }
    }

    /*@Override
    public boolean canPlaceAt(BlockState state, WorldAccess world, BlockPos pos) {
        return !state.get(WATERLOGGED) || world.getFluidState(pos).isEmpty();
    }*/

    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    /*protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED});
    }*/

    /*@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE; // Shape used for visual outline
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE; // Shape used for collision
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return SHAPE; // Shape used for raycasting
    }*/

    /*public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState();
    }*/
}
