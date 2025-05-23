package net.tomweiland.better_transportation.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class PackedIceBlock extends Block {

    public PackedIceBlock(Settings settings) {
        super(settings);
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Make packed ice melt in the nether
        if (world.getDimension().ultrawarm()) {
            world.removeBlock(pos, false);
        }
    }
}
