package org.uiop.easyplacefix.Mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.RodBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.uiop.easyplacefix.IBlock;

@Mixin(RodBlock.class)
public class MixinRodBlock implements IBlock {
    @Override
    public Pair<BlockHitResult, Integer> getHitResult(BlockState blockState, BlockPos blockPos) {
        Direction direction = blockState.get(Properties.FACING);
        return new Pair<>(
                new BlockHitResult(
                        new Vec3d(0.5, 0.5, 0.5),
                        direction,
                        blockPos,
                        false
                ), 1
        );
    }
}
