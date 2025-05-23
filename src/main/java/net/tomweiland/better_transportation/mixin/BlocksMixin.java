package net.tomweiland.better_transportation.mixin;

import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.tomweiland.better_transportation.block.PackedIceBlock;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    @Redirect(
        method = "<clinit>",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 0),
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=packed_ice"))
    )
    private static Block registerPackedIce(String id, AbstractBlock.Settings settings) {
        // Register custom class as the packed ice block
        return register(id, PackedIceBlock::new, settings.ticksRandomly());
    }

    @Shadow
    private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) { return null; }
}
