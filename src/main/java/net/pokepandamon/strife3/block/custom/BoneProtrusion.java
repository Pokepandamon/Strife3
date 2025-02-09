package net.pokepandamon.strife3.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class BoneProtrusion extends DeadCoralFanBlock implements Waterloggable {
    public static final MapCodec<BoneProtrusion> CODEC = createCodec(BoneProtrusion::new);
    public static final BooleanProperty FORM;
    public static final BooleanProperty WATERLOGGED;
    //private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625); // Center pillar shape

    static{
        FORM = BooleanProperty.of("form");
        WATERLOGGED = Properties.WATERLOGGED;
    }

    @Override
    public MapCodec<? extends BoneProtrusion> getCodec() {
        return CODEC;
    }

    public BoneProtrusion(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FORM, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FORM); // Register the WATERLOGGED property
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(FORM, ctx.getWorld().getRandom().nextBoolean());
    }
}
