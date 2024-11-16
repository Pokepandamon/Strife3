package net.pokepandamon.strife3.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class RadiationStation extends HorizontalFacingBlock {
    /*public RadiationStation(Settings settings) {
        super(settings);
    }*/

    // the codec is required since 1.20.5 however not actually used in Minecraft yet.
    public static final MapCodec<RadiationStation> CODEC = Block.createCodec(RadiationStation::new);

    public RadiationStation(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
    }

    @Override
    protected MapCodec<? extends RadiationStation> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    public static VoxelShape rotateShape(VoxelShape shape, Direction rotation) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};
        int rotations = rotation.getHorizontal();

        for (int i = 0; i < rotations; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(
                        1 - maxZ, minY, minX, 1 - minZ, maxY, maxX
                ));
            });
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        VoxelShape northFacing = VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(
                                                                                                        VoxelShapes.cuboid(0f,0f,0f,1f,1.19f,1f),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.66f,1f,1.9f,1f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.635f,1f,1.84f,0.66f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.61f,1f,1.78f,0.635f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.585f,1f,1.72f,0.61f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.56f,1f,1.66f,0.585f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.535f,1f,1.60f,0.56f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.51f,1f,1.54f,0.535f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.495f,1f,1.48f,0.51f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.47f,1f,1.42f,0.495f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.445f,1f,1.36f,0.47f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.42f,1f,1.3f,0.445f)),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.395f,1f,1.24f,0.42f));
        Direction dir = state.get(FACING);
        return switch (dir) {
            case NORTH -> northFacing;
            case SOUTH -> rotateShape(northFacing, Direction.NORTH);
            case EAST -> rotateShape(northFacing, Direction.WEST);
            case WEST -> rotateShape(northFacing, Direction.EAST);
            default -> VoxelShapes.fullCube();
        };
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        VoxelShape northFacing = VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(
                                                                                                        VoxelShapes.cuboid(0f,0f,0f,1f,1.19f,1f),
                                                                                                        VoxelShapes.cuboid(0f,1.19f,0.66f,1f,1.9f,1f)),
                                                                                                VoxelShapes.cuboid(0f,1.19f,0.635f,1f,1.84f,0.66f)),
                                                                                        VoxelShapes.cuboid(0f,1.19f,0.61f,1f,1.78f,0.635f)),
                                                                                VoxelShapes.cuboid(0f,1.19f,0.585f,1f,1.72f,0.61f)),
                                                                        VoxelShapes.cuboid(0f,1.19f,0.56f,1f,1.66f,0.585f)),
                                                                VoxelShapes.cuboid(0f,1.19f,0.535f,1f,1.60f,0.56f)),
                                                        VoxelShapes.cuboid(0f,1.19f,0.51f,1f,1.54f,0.535f)),
                                                VoxelShapes.cuboid(0f,1.19f,0.495f,1f,1.48f,0.51f)),
                                        VoxelShapes.cuboid(0f,1.19f,0.47f,1f,1.42f,0.495f)),
                                VoxelShapes.cuboid(0f,1.19f,0.445f,1f,1.36f,0.47f)),
                        VoxelShapes.cuboid(0f,1.19f,0.42f,1f,1.3f,0.445f)),
                VoxelShapes.cuboid(0f,1.19f,0.395f,1f,1.24f,0.42f));
        Direction dir = state.get(FACING);
        return northFacing;
        /*return switch (dir) {
            case NORTH -> northFacing;
            case SOUTH -> rotateShape(northFacing, Direction.NORTH);
            case EAST -> rotateShape(northFacing, Direction.WEST);
            case WEST -> rotateShape(northFacing, Direction.EAST);
            default -> VoxelShapes.fullCube();
        };*/
    }

    /*@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        //return VoxelShapes.combine(VoxelShapes.cuboid(0f,0f,0f,1f,1f,1f), VoxelShapes.cuboid(0f,1f,0f,1f,2f,1f), BooleanBiFunction.AND);
        return VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(VoxelShapes.union(
                VoxelShapes.cuboid(0f,0f,0f,1f,1.19f,1f),
                VoxelShapes.cuboid(0f,1.19f,0.66f,1f,1.9f,1f)),
                VoxelShapes.cuboid(0f,1.19f,0.635f,1f,1.84f,0.66f)),
                VoxelShapes.cuboid(0f,1.19f,0.61f,1f,1.78f,0.635f)),
                VoxelShapes.cuboid(0f,1.19f,0.585f,1f,1.72f,0.61f)),
                VoxelShapes.cuboid(0f,1.19f,0.56f,1f,1.66f,0.585f)),
                VoxelShapes.cuboid(0f,1.19f,0.535f,1f,1.60f,0.56f)),
                VoxelShapes.cuboid(0f,1.19f,0.51f,1f,1.54f,0.535f)),
                VoxelShapes.cuboid(0f,1.19f,0.495f,1f,1.48f,0.51f)),
                VoxelShapes.cuboid(0f,1.19f,0.47f,1f,1.42f,0.495f)),
                VoxelShapes.cuboid(0f,1.19f,0.445f,1f,1.36f,0.47f)),
                VoxelShapes.cuboid(0f,1.19f,0.42f,1f,1.3f,0.445f)),
                VoxelShapes.cuboid(0f,1.19f,0.395f,1f,1.24f,0.42f));
    }*/
}
