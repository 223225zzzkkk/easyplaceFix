package org.uiop.easyplacefix.Mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.uiop.easyplacefix.IBlock;

import static net.minecraft.util.math.Direction.Axis;

@Mixin(PillarBlock.class)
public class MixinPillarBlock implements IBlock {

    @Override
    public Pair<BlockHitResult, Integer> getHitResult(BlockState blockState, BlockPos blockPos) {
        Axis axis = blockState.get(Properties.AXIS);

        return new Pair<>(new BlockHitResult(
                new Vec3d(0.5, 0.5, 0.5),
                switch (axis) {
                    case X -> Direction.EAST;//x
                    case Y -> Direction.DOWN;//y
                    case Z -> Direction.NORTH;//z
                },
                blockPos, false
        ), 1);
    }
}
